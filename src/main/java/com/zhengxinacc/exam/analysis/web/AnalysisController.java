/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.analysis.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.log4j.Log4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.config.BaseController;
import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.exam.paper.domain.Paper;
import com.zhengxinacc.exam.paper.repository.PaperRepository;
import com.zhengxinacc.exam.task.domain.Task;
import com.zhengxinacc.exam.task.repository.TaskRepository;


/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年5月11日 下午8:42:58
 * @version 1.0
 */
@Log4j
@RestController
@RequestMapping("/exam/analysis")
public class AnalysisController extends BaseController {

	@Resource
	private PaperRepository paperRepository;
	@Resource
	private TaskRepository taskRepository;
	
	/**
	 * 未参考人数、考试及格数，考试不及格数
	 * 考试前十名
	 * 考试积极性 开始答题时间前三名和交卷完成前三名
	 * @author eko.zhan at 2018年5月12日 上午10:56:21
	 * @param paperId
	 * @return
	 */
	@GetMapping("")
	public JSONObject execute(String paperId){
		JSONObject result = new JSONObject();
		Paper paper = paperRepository.findOne(paperId);
		result.put("paper", paper);
		List<Grade> gradeList = paper.getGrades();
		
		Double passLine = paper.getTotal()*0.6; //及格线
		
		Set<String> allUserSet = new HashSet<String>(); //所有待考试人员
		Set<String> taskUserSet = new HashSet<String>(); //所有参加考试人员
		if (gradeList!=null && gradeList.size()>0){
			gradeList.forEach(grade -> {
				grade.getUsers().forEach(user -> allUserSet.add(user.getUserInfo().getUsername()));
			});
		}
		
		List<Task> passUserList = new ArrayList<Task>(); //考试及格学生
		List<Task> failUserList = new ArrayList<Task>(); //考试不及格学生
		List<Task> unfinishedUserList = new ArrayList<Task>(); //还在答卷中的学生
		List<Task> taskList = taskRepository.findByPaper(paper);
		taskList.forEach(task -> {
			taskUserSet.add(task.getCreateUser());
			if (task.getStatus()==1){
				//已完成
				if (task.getScore()>=passLine){
					//及格
					passUserList.add(task);
				}else{
					//不及格
					failUserList.add(task);
				}
			}else{
				//未完成
				unfinishedUserList.add(task);
			}
		});
		
		//缺考学生
		List<String> missUserList = allUserSet.stream().filter(username -> !taskUserSet.contains(username)).collect(Collectors.toList());
//		missUserList.forEach(s -> log.debug(s));
//		log.debug("--------------------------");
//		passUserList.forEach(task -> log.debug(task.getCreateUser()));
//		log.debug("--------------------------");
//		failUserList.forEach(task -> log.debug(task.getCreateUser()));
//		log.debug("--------------------------");
//		unfinishedUserList.forEach(task -> log.debug(task.getCreateUser()));
		
		result.put("missUserList", missUserList);
		result.put("passUserList", passUserList);
		result.put("failUserList", failUserList);
		result.put("unfinishedUserList", unfinishedUserList);
		
		//考试完成后按分数倒排
		Comparator<Task> byScore = Comparator.comparing(Task::getScore);
		List<Task> scoreList = taskList.stream().filter(task -> task.getStatus()==1).sorted(byScore.reversed()).limit(10).collect(Collectors.toList());
//		scoreList.forEach(task -> {
//			log.debug(task.getCreateUser() + " --> " + task.getScore());
//		});
		result.put("scoreList", scoreList);
		
		//考试完成后最先开始做卷子的学生，前十名
		Comparator<Task> byCreateDate = Comparator.comparing(Task::getCreateDate);
		List<Task> firstStartList = taskList.stream().filter(task -> task.getStatus()==1).sorted(byCreateDate).limit(10).collect(Collectors.toList());
//		firstStartList.forEach(task -> {
//			log.debug(task.getCreateUser() + " --> " + DateFormatUtils.format(task.getCreateDate(), "yyyy-MM-dd HH:mm"));
//		});
		result.put("firstStartList", firstStartList);
		
		//考试完成后最先交卷的学生，前十名
		Comparator<Task> byModifyDate = Comparator.comparing(Task::getModifyDate);
		List<Task> firstFinishList = taskList.stream().filter(task -> task.getStatus()==1).sorted(byModifyDate).limit(10).collect(Collectors.toList());
//		firstFinishList.forEach(task -> {
//			log.debug(task.getCreateUser() + " --> " + DateFormatUtils.format(task.getModifyDate(), "yyyy-MM-dd HH:mm"));
//		});
		result.put("firstFinishList", firstFinishList);
		
		return result;
	}
}
