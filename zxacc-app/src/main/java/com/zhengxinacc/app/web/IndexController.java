package com.zhengxinacc.app.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhengxinacc.app.remote.UserService;

@RestController
public class IndexController {

	@Resource
	UserService userService;
	
	@GetMapping("/")
	public String index(){
		return "HelloWorld";
	}
	
	@GetMapping("/feign")
	public String feign(){
		System.out.println("===========================");
		System.out.println(userService);
		System.out.println("===========================");
		return userService.feign("eko.zhan");
	}
}
