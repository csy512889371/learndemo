package com.ctoedu.demo.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:resources.properties")
@PropertySource("classpath:${profiles.activation}/necessary.properties")
@PropertySource("classpath:${profiles.activation}/dubbo.properties")
//@PropertySource("classpath:necessary.properties")
//@PropertySource("classpath:dubbo.properties")

public class PropertiesConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
