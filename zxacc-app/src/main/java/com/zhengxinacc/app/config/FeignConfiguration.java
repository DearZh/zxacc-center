package com.zhengxinacc.app.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;

//@Configuration
public class FeignConfiguration implements RequestInterceptor {

//	@Bean
//	public Contract feignContract(){
//		return new feign.Contract.Default();
//	}
	@Value("${login.username}")
	String username;
	@Value("${login.password}")
	String password;

//	@Bean
//	public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
//		return new BasicAuthRequestInterceptor(username, password);
//	}

	@Override
	public void apply(RequestTemplate template) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		System.out.println(request.getHeader(HttpHeaders.AUTHORIZATION));
        template.header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
	}
}
