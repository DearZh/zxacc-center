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
import com.zhengxinacc.exam.domain.Grade;
import com.zhengxinacc.exam.domain.Paper;
import com.zhengxinacc.exam.domain.Task;
import com.zhengxinacc.exam.domain.TaskQuestion;
import com.zhengxinacc.exam.remote.UserClient;
import com.zhengxinacc.exam.repository.PaperRepository;
import com.zhengxinacc.exam.repository.QuestionRepository;
import com.zhengxinacc.exam.repository.TaskRepository;
import com.zhengxinacc.exam.service.PaperService;
import com.zhengxinacc.exam.service.QuestionService;
import com.zhengxinacc.exam.service.TaskService;
import com.zhengxinacc.system.domain.User;

/**
 * 考试页面
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月2日 下午8:11:25
 * @version 1.0
 */
@RestController
@RequestMapping("/exam/exec")
public class ExecController extends BaseController {

	@Resource
	private PaperService paperService;
	@Resource
	private PaperRepository paperRepository;
	@Resource
	private QuestionService questionService;
	@Resource
	private QuestionRepository questionRepository;
	@Resource
	private TaskRepository taskRepository;
	@Resource
	private TaskService taskService;
	@Resource
	private UserClient userClient;
	
	@RequestMapping("/loadPaper")
	public Task loadPaper(String id, String username){
		Task task = taskService.init(id, username);
		task = taskService.setQuestionList(task);
		return task;
	}
	
	/**
	 * 如果已经存在答题，则返回答题，否则返回原始题目
	 * @author eko.zhan at 2018年1月6日 上午10:29:17
	 * @param paperId
	 * @param id
	 * @return
	 */
	@RequestMapping("/loadQues")
	public TaskQuestion loadQues(String paperId, String id, String username){
		Task task = taskService.init(paperId, username);
		//需要处理答案，标准答案不应该抛出到答题界面
		TaskQuestion taskQuestion = task.getQuestions().get(id);
		taskQuestion.setKey(null);
		if (taskQuestion.getAnswers()!=null){
			taskQuestion.getAnswers().forEach(answer -> {
				answer.setKey(null);
			});
		}
		return taskQuestion;
	}
	/**
	 * 我的可以参加考试的列表
	 * @author eko.zhan at 2018年1月6日 下午2:00:19
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadList")
	public JSONObject loadList(String userId){
		User user = userClient.findByUserId(userId);
		List<Paper> paperList = paperService.findByUser(user);
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
			dataArr.add(tmp);
		}
		
		result.put("data", dataArr);
		
		return result;
	}
	
	/**
	 * 单个答题保存，不计算分数，仅仅作为数据保存
	 * @author eko.zhan at 2018年1月6日 上午10:28:52
	 * @param paperId
	 * @param quesId
	 * @param ans
	 * @param request
	 * @return
	 */
	@RequestMapping("save")
//	public JSONObject save(String paperId, String quesId, String ans, Integer limit, HttpServletRequest request){
	public JSONObject save(@RequestBody JSONObject param){
		taskService.saveQuestion(param.getString("paperId"), param.getString("quesId"), param.getString("ans"), param.getInteger("limit"), param.getString("username"));
		return writeSuccess();
	}
	/**
	 * 提交试卷，计算得分
	 * @author eko.zhan at 2018年1月6日 下午2:34:00
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("submit")
	public Task submit(@RequestBody JSONObject param){
		return taskService.submit(param.getString("id"), param.getString("username"));
	}
}
