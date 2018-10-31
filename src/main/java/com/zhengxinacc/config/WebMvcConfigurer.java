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
		registry.addViewController( "/" ).setViewName("exam/task/list");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
        
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/login").setViewName("login");
		registry.addViewController( "/main" ).setViewName("main");


		registry.addViewController( "/article" ).setViewName("article/main");
		registry.addViewController( "/feedback" ).setViewName("feedback/main");
		
        //系统管理
        /*用户管理*/registry.addViewController("/user/list").setViewName("system/user/list");
        /*角色管理*/registry.addViewController("/role/list").setViewName("system/role/list");
        /*菜单管理*/registry.addViewController("/menu/list").setViewName("system/menu/list");
        /*权限管理*/registry.addViewController("/permission/list").setViewName("system/permission/list");
        
        //考试管理
        /*试题管理*/registry.addViewController("/exam/question/view").setViewName("exam/question/view");
        /*?????题目管理*/registry.addViewController("/exam/question/cate").setViewName("exam/question/cate");
        /*班级管理*/registry.addViewController("/exam/grade/list").setViewName("exam/grade/list");
        /*试卷管理*/registry.addViewController("/exam/paper/list").setViewName("exam/paper/list");
        /*我的试卷-后台*/registry.addViewController("/exam/task/view").setViewName("exam/task/view");
        /*我的试卷*/registry.addViewController("/exam/task/list").setViewName("exam/task/list");
        /*我答完后的试卷*/registry.addViewController("/exam/task/read").setViewName("exam/task/read");
        /*我的答题卡*/registry.addViewController("/exam/task/exec").setViewName("exam/task/exec");
        /*考试结果分析*/registry.addViewController("/exam/analysis/view").setViewName("exam/analysis/view");
        
        //个人中心
        /*基本资料*/registry.addViewController("/personal/info").setViewName("personal/info");
        /*安全设置*/registry.addViewController("/personal/passwd").setViewName("personal/passwd");

		super.addViewControllers(registry);
	}
}
