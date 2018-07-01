/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.common.config.BaseController;
import com.zhengxinacc.exam.domain.Paper;
import com.zhengxinacc.exam.domain.Task;
import com.zhengxinacc.exam.remote.UserClient;
import com.zhengxinacc.exam.repository.TaskRepository;
import com.zhengxinacc.exam.service.PaperService;
import com.zhengxinacc.exam.service.TaskService;
import com.zhengxinacc.system.domain.User;

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
	@Resource
	private UserClient userClient;
	
	/**
	 * 我的考试任务，求速度
	 * @author eko.zhan
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadList")
	public JSONObject loadList(String userid){
		User user = userClient.findByUserId(userid);
		List<Paper> paperList = paperService.findByUser(user);
		if (paperList==null) paperList = new ArrayList<Paper>();
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", paperList.size());
		
		JSONArray dataArr = new JSONArray();
		for (Paper paper : paperList){
			paper.setGrades(null);
			paper.setQuestionList(null);
			paper.setQuestions(null);
			JSONObject tmp = (JSONObject)JSONObject.toJSON(paper);
			Task task = taskRepository.findByPaperAndCreateUser(paper, user.getUsername());
			if (task!=null){ //判断用户是否考试完毕，只用到了 status 字段
				task.setPaper(null);
				task.setQuestionList(null);
				task.setQuestions(null);
				tmp.put("task", task);
			}
			dataArr.add(tmp);
		}
		
		result.put("data", dataArr);
		
		return result;
	}
	
	@RequestMapping("/loadMyList")
	public JSONObject loadMyList(String username){
		List<Task> list = taskRepository.findByCreateUser(username);
		
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
	public Task loadTask(@RequestBody JSONObject param){
		Task task = taskRepository.findOne(param.getString("taskId"));
		return taskService.setQuestionList(task);
	}
}
