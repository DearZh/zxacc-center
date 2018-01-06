/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.task.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhengxinacc.exam.paper.domain.Paper;
import com.zhengxinacc.exam.paper.repository.PaperRepository;
import com.zhengxinacc.exam.paper.service.PaperService;
import com.zhengxinacc.exam.question.domain.Answer;
import com.zhengxinacc.exam.question.domain.Question;
import com.zhengxinacc.exam.question.repository.QuestionRepository;
import com.zhengxinacc.exam.question.service.QuestionService;
import com.zhengxinacc.exam.task.domain.Task;
import com.zhengxinacc.exam.task.domain.TaskQuestion;
import com.zhengxinacc.exam.task.repository.TaskRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月6日 上午11:31:30
 * @version 1.0
 */
@Service
public class TaskServiceImpl implements TaskService {

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
	
	@Override
	public Task saveQuestion(String paperId, String quesId, String ans, Integer limit, String username) {
		//获取试卷
		Paper paper = paperRepository.findOne(paperId);
		//获取指定用户的答卷
		Task task = taskRepository.findByPaperAndCreateUser(paper, username);
		
		//如果已经答卷完毕，则无法修改
		if (task.getStatus()==1) return task;
		
		task.setModifyDate(new Date());
		task.setLimit(limit);
		//答卷中的题目
		Map<String, TaskQuestion> taskQuestions = task.getQuestions();
		
		//答卷中的试题
		TaskQuestion taskQuestion = taskQuestions.get(quesId);
		List<Answer> answers = taskQuestion.getAnswers();
		//判断是否答对
		if (taskQuestion.getType()==0){
			//单选题
			for (Answer answer : answers){
				if (answer.getId().equals(ans)){
					answer.setMark(Boolean.TRUE);
					break;
				}
			}
		}else if (taskQuestion.getType()==1){
			//多选题
			//ans 西文逗号分隔的字符串
			List<String> asList = Arrays.asList(ans.split(","));
			for (String as : asList){
				for (Answer answer : answers){
					if (answer.getId().equals(as)){
						answer.setMark(Boolean.TRUE);
					}
				}
			}
			//TODO 还存在一种类型是不定向选择题，有可能多选，有可能单选，不定向选择题有可能得0.5分
		}else if (taskQuestion.getType()==2){
			//判断题
			taskQuestion.setKeyMark(Boolean.valueOf(ans));
		}
		taskQuestion.setAnswers(answers);
		taskQuestion.setIsReply(true);
		taskQuestions.put(quesId, taskQuestion);
		task.setQuestions(taskQuestions);
		return taskRepository.save(task);
	}

	@Override
	public Task init(String paperId, String username) {
		Paper paper = paperRepository.findOne(paperId);
		Task task = taskRepository.findByPaperAndCreateUser(paper, username);
		if (task==null){
			task = new Task();
			task.setCreateUser(username);
			task.setModifyUser(username);
			task.setPaper(paper);
			task.setLimit(paper.getLimit() * 60);
			Map<String, TaskQuestion> taskQuestions = new HashMap<String, TaskQuestion>(); 
			paper.getQuestions().forEach((id, paperQuestion) -> {
			    Question question = questionRepository.findOne(id);
			    TaskQuestion taskQuestion
			    	= new TaskQuestion(question.getId(), question.getName(), question.getType(),
			    			paperQuestion.getOrder(), paperQuestion.getScore(), question.getKey(),
			    			Boolean.FALSE, Boolean.FALSE, question.getAnswers());
			    taskQuestions.put(question.getId(), taskQuestion);
			});
			task.setQuestions(taskQuestions);
			taskRepository.save(task);
		}
		return task;
	}

	@Override
	public Task submit(String paperId, String username) {
		Paper paper = paperRepository.findOne(paperId);
		Task task = taskRepository.findByPaperAndCreateUser(paper, username);
		
		//如果已经答卷完毕，则无法修改
		if (task.getStatus()==1) return task;
		
		task.setStatus(1); //答卷完毕
		task.setModifyDate(new Date());
		//计算分数
		List<Double> scoreList = new ArrayList<Double>();
		Map<String, TaskQuestion> questions = task.getQuestions();
		questions.forEach((id, taskQuestion) -> {
			if (taskQuestion.getType()==0){
				//单选题
				taskQuestion.getAnswers().forEach(answer -> {
					if (answer.getKey()==answer.getMark()){
						scoreList.add(taskQuestion.getScore());
						return;
					}
				});
			}else if (taskQuestion.getType()==1){
				//多选题
				Boolean b = true;
				Iterator<Answer> iterator = taskQuestion.getAnswers().iterator();
				while (iterator.hasNext()){
					Answer answer = iterator.next();
					if (answer.getKey()!=answer.getMark()){
						b = false;
					}
				}
				if (b){
					scoreList.add(taskQuestion.getScore());
				}
			}else if (taskQuestion.getType()==2){
				//判断题
				if (taskQuestion.getKey()==taskQuestion.getKeyMark()){
					scoreList.add(taskQuestion.getScore());
				}
			}
		});
		
		Double total = 0.0;
		for (Double d : scoreList){
			total += d;
		}
		task.setScore(total);
		
		return taskRepository.save(task);
	}

}
