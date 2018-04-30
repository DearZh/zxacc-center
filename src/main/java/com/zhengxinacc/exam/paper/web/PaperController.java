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
import com.zhengxinacc.exam.question.repository.QuestionRepository;
import com.zhengxinacc.exam.task.domain.Task;
import com.zhengxinacc.exam.task.repository.TaskRepository;

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
	@Resource
	private TaskRepository taskRepository;

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
		Page<Paper> pager = paperService.findAll(page, limit, param, Direction.DESC);
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", pager.getTotalElements());
		
		JSONArray dataArr = new JSONArray();
		List<Paper> list = pager.getContent();
		for (Paper paper : list){
			paper = paperService.setQuestionList(paper);
			JSONObject tmp = (JSONObject)JSONObject.toJSON(paper);
			tmp.put("createDate", DateFormatUtils.format(paper.getCreateDate(), "yyyy-MM-dd"));
			String grades = "";
			if (paper.getGrades()!=null){
				for (Grade grade : paper.getGrades()){
					if (grade!=null){
						grades += grade.getName() + " ";
					}
				}
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
	public JSONObject save(HttpServletRequest request){
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
		paperService.save(param);
		return writeSuccess();
	}
	
	/**
	 * 试卷删除
	 * 如果试卷已经存在考试记录，则不能删除
	 * @author eko.zhan at 2018年3月9日 上午10:23:08
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public JSONObject delete(String id){
		Paper paper = paperRepository.findOne(id);
		List<Task> taskList = taskRepository.findByPaper(paper);
		if (taskList.size()==0){
			paperRepository.delete(id);
		}else{
			return writeFailure("当前试卷已存在考试，无法删除");
		}
		return writeSuccess();
	}
	
	/**
	 * 获取所有考试成绩
	 * @author eko.zhan at 2018年1月12日 下午12:58:31
	 * @return
	 */
	@RequestMapping("/loadTask")
	public JSONObject loadTask(String paperId){
		Paper paper = paperRepository.findOne(paperId);
		List<Task> list = taskRepository.findByPaper(paper);
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
			dataArr.add(tmp);
		}
		
		result.put("data", dataArr);
		return result;
	}
	
	@RequestMapping("/copy")
	public JSONObject copy(String id){
		paperService.copy(id);
		
		
		return writeSuccess();
	}
}
