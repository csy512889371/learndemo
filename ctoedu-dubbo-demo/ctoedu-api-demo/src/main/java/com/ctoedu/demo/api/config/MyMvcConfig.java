package com.ctoedu.demo.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ctoedu.demo.api.interceptor.HttpInterceptor;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan("com.ctoedu.demo.api")
public class MyMvcConfig extends WebMvcConfigurerAdapter{
	
	@Value( "${AccessControlAllowOrigin}" )
	private String ACCESS_CONTROL_ALLOW_ORIGIN;
//	@Bean
//	public InternalResourceViewResolver viewResoler(){
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setPrefix("/WEB-INF/classes/views/");
//		viewResolver.setSuffix(".jsp");
//		viewResolver.setViewClass(JstlView.class);
//		return viewResolver;
//	}
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry){
//		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
//		registry.addResourceHandler("/**").addResourceLocations("classpath:/app/");
//	}
	
	@Bean
	public HttpInterceptor fayInterceptor(){
		return new HttpInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(fayInterceptor());
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000);
		return multipartResolver;
	}
	
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry){
//		registry.addViewController("/").setViewName("/index");
//		registry.addViewController("/index").setViewName("/index");
//		registry.addViewController("/nana_1/nana1").setViewName("/nana_1/nana1");
//		registry.addViewController("/nana_2/nana2").setViewName("/nana_2/nana2");
//	}
	
	/**
	 * 重写configurePathMatch此方法
	 * 设置其参数PathMatchConfigurer的属性UseSuffixPatternMatch值为false
	 * 可以让路径中带小数点“.”后面的值不被忽略
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer){
		configurer.setUseSuffixPatternMatch(false);
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(ACCESS_CONTROL_ALLOW_ORIGIN)
            .allowedMethods("PUT", "DELETE", "POST", "OPTIONS", "GET")
            .allowedHeaders("Content-Type","Authorization")
//            .exposedHeaders("header1", "header2")
            .allowCredentials(true).maxAge(3600);
    }
	
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter(){
		return new StringHttpMessageConverter();
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		return new MappingJackson2HttpMessageConverter();
	}
	
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }
}
