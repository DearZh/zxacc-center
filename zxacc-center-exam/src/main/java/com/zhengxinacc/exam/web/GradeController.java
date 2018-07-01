/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.common.config.BaseController;
import com.zhengxinacc.exam.domain.Grade;
import com.zhengxinacc.exam.repository.GradeRepository;
import com.zhengxinacc.exam.service.GradeService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午2:10:25
 * @version 1.0
 */
@RestController
@RequestMapping("/exam/grade")
public class GradeController extends BaseController {
	
	@Resource
	private GradeRepository gradeRepository;
	@Resource
	private GradeService gradeService;

	/**
	 * 加载数据
	 * @author eko.zhan at 2017年12月23日 下午7:01:13
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/loadList")
	public JSONObject loadList(Integer page, Integer limit, String keyword){
		JSONObject param = new JSONObject();
		param.put("property", "createDate");
		param.put("keyword", keyword);
		Page<Grade> pager = gradeService.findAll(page, limit, param, Direction.DESC);
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", pager.getTotalElements());
		
//		JSONArray dataArr = new JSONArray();
//		List<Grade> list = pager.getContent();
//		for (Grade grade : list){
//			JSONObject tmp = (JSONObject)JSONObject.toJSON(grade);
//			tmp.put("createDate", DateFormatUtils.format(grade.getCreateDate(), "yyyy-MM-dd"));
//			if (grade.getUsers()!=null){
//				tmp.put("count", grade.getUsers().size());
//			}else{
//				tmp.put("count", 0);
//			}
//			
//			dataArr.add(tmp);
//		}
		
		result.put("data", pager.getContent());
		
		return result;
	}
	
	/**
	 * 保存班级信息
	 * @author eko.zhan at 2017年12月23日 下午7:01:05
	 * @param principal
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	public JSONObject save(Grade grade){
		
		JSONObject param = new JSONObject();
		param.put("id", grade.getId());
		param.put("name", grade.getName());
		param.put("users", JSON.toJSONString(grade.getUsers()));
		param.put("username", grade.getCreateUser());
		gradeService.save(param);
		
		return writeSuccess();
		
	}
	
	@RequestMapping("/delete")
	public JSONObject delete(String id){
		gradeService.delete(id);
		return writeSuccess();
	}
}
