/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.question.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.config.BaseController;
import com.zhengxinacc.exam.question.domain.Question;
import com.zhengxinacc.exam.question.domain.QuestionCate;
import com.zhengxinacc.exam.question.repository.AnswerRepository;
import com.zhengxinacc.exam.question.repository.QuestionCateRepository;
import com.zhengxinacc.exam.question.repository.QuestionRepository;
import com.zhengxinacc.exam.question.service.QuestionService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 上午9:38:06
 * @version 1.0
 */
@RestController
@RequestMapping("/exam/question")
public class QuestionController extends BaseController {
	
	@Resource
	private QuestionCateRepository questionCateRepository;
	@Resource
	private QuestionRepository questionRepository;
	@Resource
	private AnswerRepository answerRepository;
	@Resource
	private QuestionService questionService;
	
	/**
	 * 保存问题
	 * @author eko.zhan at 2017年12月24日 下午1:15:41
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	public Question save(HttpServletRequest request){
		String id = request.getParameter("id");
		String cateid = request.getParameter("cateid");
		String catename = request.getParameter("catename");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String answers = request.getParameter("answers");
		String key = request.getParameter("key");
		
		JSONObject param = new JSONObject();
		param.put("id", id);
		param.put("cateid", cateid);
		param.put("catename", catename);
		param.put("name", name);
		param.put("type", type);
		param.put("answers", answers);
		param.put("key", key);
		
		return questionService.save(param, getUsername(request));
	}
	/**
	 * 问题删除
	 * @author eko.zhan at 2017年12月24日 下午9:01:51
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public JSONObject delete(String id){
		questionService.delete(id);
		return writeSuccess();
	}

	/**
	 * 根据指定的分类id加载题目数据
	 * @author eko.zhan at 2017年12月24日 下午12:43:20
	 * @param cateId
	 * @return
	 */
	@RequestMapping("/loadList")
	public JSONObject loadList(String cateId, Integer page, Integer limit){
		page = page==null?1:page;
		limit = limit==null?10:limit;
		JSONObject param = new JSONObject();
		param.put("cateId", cateId);
		param.put("property", "createDate");
		Page<Question> pager = questionService.findAll(page, limit, param, Direction.DESC);
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", pager.getTotalElements());
		
		JSONArray dataArr = new JSONArray();
		List<Question> list = pager.getContent();
		for (Question question : list){
			JSONObject tmp = (JSONObject)JSONObject.toJSON(question);
			tmp.put("typeDesc", question.getType()==0?"单选题":(question.getType()==1?"多选题":"判断题"));
			tmp.put("createDate", DateFormatUtils.format(question.getCreateDate(), "yyyy-MM-dd"));
			tmp.put("cateid", question.getCate().getId());
			tmp.put("catename", question.getCate().getName());
			
			dataArr.add(tmp);
		}
		
		result.put("data", dataArr);
		
		return result;
	}
	
	/**
	 * 加载分类树
	 * @author eko.zhan at 2017年12月24日 上午9:55:37
	 * @param id
	 * @return
	 */
	@RequestMapping("/loadCate")
	public JSONArray loadCate(String id){
		JSONArray arr = new JSONArray();
		if (StringUtils.isBlank(id)){
			JSONObject json = new JSONObject();
			json.put("id", "ROOT");
			json.put("pid", "");
			json.put("name", "题目分类");
			json.put("isParent", true);
			json.put("open", false);
			arr.add(json);
		}else{
			List<QuestionCate> list = questionCateRepository.findByPid(id);
			for (QuestionCate cate : list){
				JSONObject json = new JSONObject();
				json.put("id", cate.getId());
				json.put("pid", cate.getPid());
				json.put("name", cate.getName());
				json.put("isParent", true);
				json.put("open", false);
				arr.add(json);
			}
		}
		return arr;
	}
	/**
	 * 保存分类
	 * @author eko.zhan at 2017年12月24日 上午10:14:40
	 * @param pid
	 * @param name
	 * @return
	 */
	@RequestMapping("/saveCate")
	public QuestionCate saveCate(String id, String pid, String name, HttpServletRequest request){
		QuestionCate cate = null;
		if (StringUtils.isBlank(id)){
			cate = new QuestionCate();
			cate.setCreateUser(getUsername(request));
			cate.setModifyUser(getUsername(request));
		}else{
			cate = questionCateRepository.findOne(id);
			cate.setModifyUser(getUsername(request));
		}
		cate.setName(name);
		if (StringUtils.isNotBlank(pid)){
			cate.setPid(pid);
		}else{
			cate.setPid("");
		}
		questionCateRepository.save(cate);
		return cate;
	}
	/**
	 * 删除分类
	 * @author eko.zhan at 2017年12月24日 下午12:42:37
	 * @param id
	 * @return
	 */
	@RequestMapping("delCate")
	public JSONObject delCate(String id){
		QuestionCate cate = questionCateRepository.findOne(id);
		if (cate.getQuestions()!=null && cate.getQuestions().size()>0){
			return writeFailure("当前分类下有题目，不可删除");
		}
		List<QuestionCate> list = questionCateRepository.findByPid(id);
		if (list!=null && list.size()>0){
			return writeFailure("当前分类下有子分类，不可删除");
		}
		questionCateRepository.delete(cate);
		return writeSuccess();
	}
}
