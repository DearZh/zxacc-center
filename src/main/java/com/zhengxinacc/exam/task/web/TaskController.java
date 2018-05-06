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
import com.zhengxinacc.exam.task.service.TaskService;

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
	@Resource
	private TaskService taskService;
	
	/**
	 * 我的考试任务
	 * @author eko.zhan
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadList")
	public JSONObject loadList(HttpServletRequest request){
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
	
	@RequestMapping("/loadMyList")
	public JSONObject loadMyList(HttpServletRequest request){
		List<Task> list = taskRepository.findByCreateUser(getUsername(request));
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", list.size());
		
		JSONArray dataArr = new JSONArray();
		for (Task task : list){
			JSONObject tmp = (JSONObject)JSONObject.toJSON(task);
			tmp.put("createDate", DateFormatUtils.format(task.getCreateDate(), "yyyy-MM-dd HH:mm"));
			tmp.put("modifyDate", DateFormatUtils.format(task.getModifyDate(), "yyyy-MM-dd HH:mm"));
			if (task.getStatus()==1){
				tmp.put("statusDesc", "已完成");
			}else{
				tmp.put("statusDesc", "考试中");
			}
			tmp.put("paperName", task.getPaper().getName());
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
		return taskService.setQuestionList(task);
	}
}
