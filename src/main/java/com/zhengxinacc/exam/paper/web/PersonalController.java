/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhengxinacc.config.BaseController;
import com.zhengxinacc.exam.paper.domain.Paper;
import com.zhengxinacc.exam.paper.service.PaperService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月1日 下午4:46:09
 * @version 1.0
 */
@RestController
@RequestMapping("/exam/personal")
public class PersonalController extends BaseController {

	@Resource
	private PaperService paperService;
	
	@RequestMapping("/list")
	public void list(HttpServletRequest request){
		List<Paper> paperList = paperService.findByUser(getCurrentUser(request));
		System.out.println(paperList.size());
		for (Paper paper : paperList){
			System.out.println(paper);
		}
	}
}
