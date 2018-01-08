/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.task.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.config.BaseController;
import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.exam.paper.domain.Paper;
import com.zhengxinacc.exam.paper.repository.PaperRepository;
import com.zhengxinacc.exam.paper.service.PaperService;
import com.zhengxinacc.exam.question.repository.QuestionRepository;
import com.zhengxinacc.exam.question.service.QuestionService;
import com.zhengxinacc.exam.task.domain.Task;
import com.zhengxinacc.exam.task.domain.TaskQuestion;
import com.zhengxinacc.exam.task.repository.TaskRepository;
import com.zhengxinacc.exam.task.service.TaskService;

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
	
	@RequestMapping("/loadPaper")
	public Task loadPaper(String id, HttpServletRequest request){
		Task task = taskService.init(id, getUsername(request));
		Map<String, TaskQuestion> questions = task.getQuestions();
		List<Map.Entry<String, TaskQuestion>> list = new ArrayList<Map.Entry<String, TaskQuestion>>(questions.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, TaskQuestion>>() {
			@Override
			public int compare(Map.Entry<String, TaskQuestion> o1, Map.Entry<String, TaskQuestion> o2) {
				TaskQuestion q1 = o1.getValue();
				TaskQuestion q2 = o2.getValue();
				if (q1.getOrder()!=null && q2.getOrder()!=null){
					return q1.getOrder().compareTo(q2.getOrder());
				}
				return 0;
			}
		});
		task.setQuestionList(list);
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
	public TaskQuestion loadQues(String paperId, String id, HttpServletRequest request){
		Task task = taskService.init(paperId, getUsername(request));
		return task.getQuestions().get(id);
	}
	/**
	 * 我的可以参加考试的列表
	 * @author eko.zhan at 2018年1月6日 下午2:00:19
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
	public JSONObject save(String paperId, String quesId, String ans, Integer limit, HttpServletRequest request){
		taskService.saveQuestion(paperId, quesId, ans, limit, getUsername(request));
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
	public Task submit(String id, HttpServletRequest request){
		return taskService.submit(id, getUsername(request));
	}
}
