package com.zhengxinacc.app.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.app.remote.TaskClient;
import com.zhengxinacc.app.remote.UserClient;
import com.zhengxinacc.common.config.BaseController;
import com.zhengxinacc.exam.domain.Task;
import com.zhengxinacc.exam.domain.TaskQuestion;
import com.zhengxinacc.system.domain.User;

@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

	@Resource
	UserClient userClient;
	
	@Resource
	TaskClient taskClient;
	
	@ApiOperation(value="获取当前用户的考卷清单")
	@PostMapping("/loadTaskList")
	public JSONObject loadTaskList(@SessionAttribute("CURRENT_USER") User user){
		return taskClient.loadList(user.getId());
	}
	@ApiOperation(value="获取当前用户信息")
	@PostMapping("/loadUser")
	public User loadUser(@SessionAttribute("CURRENT_USER") User user){
		return user;
	}
	
	@ApiOperation(value="根据指定的试卷id获取试卷信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id", value="试卷id", required=true, dataType="String", paramType="query")
	})
	@PostMapping("/loadByPaper")
	public Task loadByPaper(@RequestParam("id") String id, @SessionAttribute("CURRENT_USER") User user){
		return taskClient.loadPaper(id, user);
	}
	
	@PostMapping("/loadQues")
	public TaskQuestion loadQues(@RequestParam("paperId") String paperId, @RequestParam("id") String id, @SessionAttribute("CURRENT_USER") User user){
		return taskClient.loadQues(paperId, id, user);
	}
	
	@PostMapping("/saveExec")
	public JSONObject saveExec(@RequestBody JSONObject params, @SessionAttribute("CURRENT_USER") User user){
//		@RequestParam("paperId") String paperId, @RequestParam("quesId") String quesId, 
//		@RequestParam("ans") String ans, @RequestParam("limit") Integer limit, 
		String paperId = params.getString("paperId");
		String quesId = params.getString("quesId");
		String ans = params.getString("ans");
		Integer limit = params.getInteger("limit");
		return taskClient.saveExec(paperId, quesId, ans, limit, user);
	}
	
	@PostMapping("/submitExec")
	public Task submitExec(@RequestBody JSONObject params, @SessionAttribute("CURRENT_USER") User user){
		String id = params.getString("id");
		return taskClient.submitExec(id, user);
	}
	
	@PostMapping("/loadTask")
	public Task loadTask(@RequestBody JSONObject params){
		return taskClient.loadTask(params.getString("taskId"));
	}
}
