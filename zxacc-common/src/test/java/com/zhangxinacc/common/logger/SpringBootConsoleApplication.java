/*
 * Power by www.xiaoi.com
 */
package com.zhangxinacc.common.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.zhangxinacc.common.logger.aop.IntervalAspectTests;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月21日 下午6:45:17
 * @version 1.0
 */
@SpringBootApplication
@Configuration
public class SpringBootConsoleApplication implements CommandLineRunner{

	@Autowired
	private IntervalAspectTests intervalAspectTests;
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }
	
	public void run(String... args) throws Exception {
		System.out.println("--begin--");
		intervalAspectTests.read();
		System.out.println("--end--");
	}

	
}
