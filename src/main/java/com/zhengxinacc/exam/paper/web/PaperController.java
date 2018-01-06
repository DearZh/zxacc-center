/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.config.BaseController;
import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.exam.grade.repository.GradeRepository;
import com.zhengxinacc.exam.paper.domain.Paper;
import com.zhengxinacc.exam.paper.repository.PaperRepository;
import com.zhengxinacc.exam.paper.service.PaperService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午2:10:25
 * @version 1.0
 */
@RestController
@RequestMapping("/exam/paper")
public class PaperController extends BaseController {
	
	@Resource
	private PaperRepository paperRepository;
	@Resource
	private GradeRepository gradeRepository;
	@Resource
	private PaperService paperService;

	/**
	 * 加载数据
	 * @author eko.zhan at 2017年12月23日 下午7:01:13
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/loadList")
	public JSONObject loadList(Integer page, Integer limit){
		Order order = new Order(Direction.DESC, "createDate");
		Pageable pageable = new PageRequest(page-1, limit, new Sort(order));
		
		Page<Paper> pager = paperRepository.findAll(pageable);
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", pager.getSize());
		
		JSONArray dataArr = new JSONArray();
		List<Paper> list = pager.getContent();
		for (Paper paper : list){
			JSONObject tmp = (JSONObject)JSONObject.toJSON(paper);
			tmp.put("createDate", DateFormatUtils.format(paper.getCreateDate(), "yyyy-MM-dd"));
			String grades = "";
			for (Grade grade : paper.getGrades()){
				grades += grade.getName() + " ";
			}
			tmp.put("gradeName", grades);
			dataArr.add(tmp);
		}
		
		result.put("data", dataArr);
		
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
	public Paper save(HttpServletRequest request){
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String[] gradeIds = request.getParameterValues("gradeIds[]"); //班级id数组
		String questions = request.getParameter("questions"); //json数组字符串
		System.out.println(questions);
		
		JSONObject param = new JSONObject();
		param.put("id", id);
		param.put("name", name);
		param.put("total", request.getParameter("total"));
		param.put("limit", request.getParameter("limit"));
		param.put("gradeIds", JSON.toJSONString(gradeIds));
		param.put("username", getUsername(request));
		param.put("questions", questions);
		
		return paperService.save(param);
	}
	
	@RequestMapping("/delete")
	public JSONObject delete(String id){
		paperRepository.delete(id);
		return writeSuccess();
	}
}
