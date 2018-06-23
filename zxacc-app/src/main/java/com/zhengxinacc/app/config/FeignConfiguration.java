package com.zhengxinacc.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignConfiguration {

	@Bean
	public Contract feignContract(){
		return new feign.Contract.Default();
	}
	@Value("${login.username}")
	String username;
	@Value("${login.password}")
	String password;

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
		return new BasicAuthRequestInterceptor(username, password);
	}
}
