/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.user.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.system.permission.repository.PermissionRepository;
import com.zhengxinacc.system.permission.service.PermissionService;
import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.system.user.domain.UserInfo;
import com.zhengxinacc.system.user.repository.UserRepository;
import com.zhengxinacc.util.EncryptUtils;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年10月23日 下午3:47:24
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
	/* 默认密码 */
	private final static String DEFAULT_PASSWORD = "888888";

	private final static Log logger = LogFactory.getLog(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        user.setAuthorities(permissionService.getAuthorities(user));
        logger.debug("username:" + user.getUsername()+";password:" + user.getPassword());
        return user;
	}

	@Override
	public User save(User tmpUser) {
		//如果有账号相同的用户，不提醒，直接覆盖
		User user = userRepository.findByUsername(tmpUser.getUsername());
		if (user!=null){
			BeanUtils.copyProperties(tmpUser, user, new String[]{"id"});
		}
		//用户密码为空，初始化
		if (StringUtils.isBlank(user.getPassword())){
			String salt = String.valueOf(Calendar.getInstance().getTimeInMillis());
			user.setSalt(Base64.encodeBase64String(salt.getBytes())); //加密储存秘钥
			user.setPassword(EncryptUtils.encode(DEFAULT_PASSWORD, salt));
		}
		return userRepository.save(user);
	}

	@Override
	public User findOne(String id) {
		return userRepository.findOne(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Page<User> findAll(Integer page, Integer size, JSONObject data, Direction desc) {
		String property = data.getString("property");
		String keyword = data.getString("keyword");
		
		Order order = new Order(desc, property);
		Pageable pageable = new PageRequest(page-1, size, new Sort(order));
		
		if (StringUtils.isNotBlank(keyword)){
			//return userRepository.findByUsernameLike(keyword, pageable);
			Criteria criteria = new Criteria();
			criteria.orOperator(Criteria.where("username").regex(keyword),
					Criteria.where("userInfo.username").regex(keyword),
					Criteria.where("userInfo.phone").regex(keyword));
			Query query = new Query(criteria);
			long total = mongoTemplate.count(query, User.class);
			query.limit(size);
			query.skip((page-1)*size);
			List<User> list = mongoTemplate.find(query, User.class);
			Page<User> pager = new PageImpl<User>(list, pageable, total);
			return pager;
		}
		return userRepository.findAll(pageable);
	}

	@Override
	public void delete(String id) {
		userRepository.delete(id);
	}

	@Override
	public void importUsers(MultipartFile file, String username) {
		// excel 从0开始
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			for (int i=2; i<=sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell1 = row.getCell(1);	//用户名
				XSSFCell cell2 = row.getCell(2);	//手机号
				cell2.setCellType(CellType.STRING);
				String usernameCN = cell1.getStringCellValue();
				String phone = cell2.getStringCellValue();
				
//				System.out.println(phone);
//				System.out.println(usernameCN);
				
				if (StringUtils.isNotBlank(phone)){
					User user = userRepository.findByUsername(phone);
					if (user==null){
						user = new User();
						user.setCreateUser(username);
						user.setUsername(phone);
						user.setModifyUser(username);
						
						UserInfo userInfo = new UserInfo();
						userInfo.setUsername(usernameCN);
						userInfo.setPhone(phone);
						userInfo.setSex(0);
						userInfo.setEmail("");
						user.setUserInfo(userInfo);
						
						save(user);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if (workbook!=null){
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
