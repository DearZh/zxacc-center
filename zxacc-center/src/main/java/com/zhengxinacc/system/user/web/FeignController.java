package com.zhengxinacc.system.user.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

	@RequestMapping(value="/demo", method=RequestMethod.POST)
	public String hello(@RequestParam("name") String name){
		return "Hello World, " + name;
	}
}
