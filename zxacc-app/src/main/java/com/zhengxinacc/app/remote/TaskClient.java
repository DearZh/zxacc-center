/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.app.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.exam.domain.Task;
import com.zhengxinacc.exam.domain.TaskQuestion;
import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月7日 下午5:33:00
 * @version 1.0
 */

@FeignClient(name="zxacc-zuul")
public interface TaskClient {

	@RequestMapping(value="/api-exam/exam/task/loadList", method=RequestMethod.POST)
	public JSONObject loadList(@RequestParam("userid") String userid);
	
	
	@RequestMapping(value="/api-exam/exam/exec/loadPaper", method=RequestMethod.POST)
	public Task loadPaper(@RequestParam("id") String id, @RequestBody User user);
	
	@RequestMapping(value="/api-exam/exam/exec/loadQues", method=RequestMethod.POST)
	public TaskQuestion loadQues(@RequestParam("paperId") String paperId, @RequestParam("id") String id, @RequestBody User user);
	
	@RequestMapping(value="/api-exam/exam/exec/save", method=RequestMethod.POST)
	public JSONObject saveExec(@RequestParam("paperId") String paperId, @RequestParam("quesId") String quesId, 
			@RequestParam("ans") String ans, @RequestParam("limit") Integer limit, @RequestBody User user);
	
	
	@RequestMapping(value="/api-exam/exam/exec/submit", method=RequestMethod.POST)
	public Task submitExec(@RequestParam("id") String id, @RequestBody User user);

	@RequestMapping(value="/api-exam/exam/task/loadTask", method=RequestMethod.POST)
	public Task loadTask(@RequestParam("taskId") String taskId);
}
