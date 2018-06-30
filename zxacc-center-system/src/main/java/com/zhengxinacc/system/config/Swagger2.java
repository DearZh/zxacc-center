/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.zhengxinacc.common.config.ZxaccSwagger2;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月2日 上午11:39:23
 * @version 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2 extends ZxaccSwagger2{
	
	@Bean
    public Docket createRestApi() {
        return super.createRestApi("com.zhengxinacc.system.web", "");
    }

}
