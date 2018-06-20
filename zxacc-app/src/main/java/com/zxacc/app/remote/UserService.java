package com.zxacc.app.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="zxacc-center")
public interface UserService {

	@RequestMapping(value="/zxacc/demo", method=RequestMethod.POST)
	public String feign(@RequestParam("name") String name);
}
