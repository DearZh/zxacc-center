/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 下午2:14:09
 * @version 1.0
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController( "/" ).setViewName("main");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );

        registry.addViewController("/login").setViewName("login");
        
        //系统管理
        //用户管理
        registry.addViewController("/user/list").setViewName("system/user/list");
        
        //考试管理
        registry.addViewController("/exam/question/view").setViewName("exam/question/view");
        //班级管理
        registry.addViewController("/exam/grade/list").setViewName("exam/grade/list");
        //试卷管理
        registry.addViewController("/exam/paper/list").setViewName("exam/paper/list");
        //我的试卷
        registry.addViewController("/exam/task/list").setViewName("exam/task/list");
        registry.addViewController("/exam/task/read").setViewName("exam/task/read");
        registry.addViewController("/exam/task/exec").setViewName("exam/task/exec");

		super.addViewControllers(registry);
	}
}
