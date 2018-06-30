/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.common.config;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月2日 上午11:39:23
 * @version 1.0
 */
public class ZxaccSwagger2 {
	
	/**
	 * 创建Api
	 * @param packageName 需要扫描的 controller 层
	 * @param serviceUrl 需要显示的 api 地址
	 * @return
	 */
    public Docket createRestApi(String packageName, String serviceUrl) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(serviceUrl))
                .select()
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(String serviceUrl) {
        return new ApiInfoBuilder()
                .title("Spring Boot 中使用 Swagger2 构建 RESTful APIs ")
                .description("")
                .termsOfServiceUrl(serviceUrl)
                .contact(new Contact("ekozhan", "http://ekozhan.com/", "eko.z@outlook.com"))
                .version("1.0")
                .build();
    }

}
