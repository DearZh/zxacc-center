/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.app.config;

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
		/*app首页*/registry.addViewController( "/" ).setViewName("index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/login").setViewName("login");
		
		/*我答完后的试卷*/registry.addViewController("/read").setViewName("read");
        /*我的答题卡*/registry.addViewController("/exec").setViewName("exec");
		
		super.addViewControllers(registry);
	}
}
