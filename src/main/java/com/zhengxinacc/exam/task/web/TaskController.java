/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.task.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.config.BaseController;
import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.exam.paper.domain.Paper;
import com.zhengxinacc.exam.paper.service.PaperService;
import com.zhengxinacc.exam.task.domain.Task;
import com.zhengxinacc.exam.task.repository.TaskRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月2日 下午7:44:44
 * @version 1.0
 */
@RestController
@RequestMapping("/exam/task")
public class TaskController extends BaseController {
	
	@Resource
	private PaperService paperService;
	@Resource
	private TaskRepository taskRepository;
	
	@RequestMapping("/loadList")
	public JSONObject list(HttpServletRequest request){
		List<Paper> paperList = paperService.findByUser(getCurrentUser(request));
		if (paperList==null) paperList = new ArrayList<Paper>();
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", paperList.size());
		
		JSONArray dataArr = new JSONArray();
		for (Paper paper : paperList){
			JSONObject tmp = (JSONObject)JSONObject.toJSON(paper);
			tmp.put("createDate", DateFormatUtils.format(paper.getCreateDate(), "yyyy-MM-dd"));
			String grades = "";
			for (Grade grade : paper.getGrades()){
				grades += grade.getName() + " ";
			}
			tmp.put("gradeName", grades);
			Task task = taskRepository.findByPaperAndCreateUser(paper, getUsername(request));
			if (task!=null){
				tmp.put("task", task);
			}
			dataArr.add(tmp);
		}
		
		result.put("data", dataArr);
		
		return result;
	}
	
	/**
	 * 查阅试卷，只读
	 * @author eko.zhan at 2018年1月6日 下午2:36:30
	 * @param taskId
	 */
	@RequestMapping("loadTask")
	public Task loadTask(String taskId){
		Task task = taskRepository.findOne(taskId);
		return task;
	}
}