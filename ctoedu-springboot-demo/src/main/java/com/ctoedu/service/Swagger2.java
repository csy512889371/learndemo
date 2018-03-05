package com.ctoedu.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 在完成了上述配置后，其实已经可以生产文档内容，但是这样的文档主要针对请求本身，而描述主要来源于函数等命名产生，
 * 对用户并不友好，我们通常需要自己增加一些说明来丰富文档内容。如下所示，我们通过@ApiOperation注解来给API增加说明、
 * 通过@ApiImplicitParams、@ApiImplicitParam注解来给参数增加说明。
 * 完成上述代码添加上，启动Spring Boot程序，访问：http://localhost:8080/swagger-ui.html
 *
 */

@Configuration
@EnableSwagger2
public class Swagger2 {
	 @Bean
	    public Docket createRestApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.ctoedu.service.web"))
	                .paths(PathSelectors.any())
	                .build();
	    }
	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("结合swagger使用，实现restful api文档")
	                .description("欢迎大家，基于java语言的构建中小型互联网公司后台架构")
	                .termsOfServiceUrl("http://blog.csdn.net/huohuangfengcheng/article/details/77326490")
	                .contact("走在大数据的边缘")
	                .version("1.0")
	                .build();
	    }
}
