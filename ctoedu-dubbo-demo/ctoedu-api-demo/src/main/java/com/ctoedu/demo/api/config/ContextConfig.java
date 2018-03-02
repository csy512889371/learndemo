package com.ctoedu.demo.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.ctoedu.common.utils.SpringUtils;
@Configuration
@ComponentScan("com.ctoedu")
public class ContextConfig{
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.addBasenames("classpath:messages");
		messageSource.setUseCodeAsDefaultMessage(false);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(60);
		return messageSource;
	}

	@Bean
	public static SpringUtils springUtils()
	{
		SpringUtils springUtils = new SpringUtils();
		return springUtils;
	}
}
