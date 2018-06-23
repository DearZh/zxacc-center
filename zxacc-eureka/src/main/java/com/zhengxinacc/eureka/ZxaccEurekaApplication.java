package com.zhengxinacc.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ZxaccEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZxaccEurekaApplication.class, args);
	}
}
