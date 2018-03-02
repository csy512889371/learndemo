package com.ctoedu.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import com.ctoedu.common.utils.SpringUtils;

@Configuration
@ComponentScan("com.ctoedu")
@PropertySource({"classpath:resources.properties"})
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Import({CacheConfig.class,DubboConfig.class,DataSourceConfig.class,PersistenceConfig.class})
@ImportResource("/spring/spring-transaction.xml")
public class ContextConfig{

	@Autowired
	private Environment env;
	
	@Bean
	public static SpringUtils springUtils()
	{
		SpringUtils springUtils = new SpringUtils();
		return springUtils;
	}
	
	@Bean
	public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource()
	{
		ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		reloadableResourceBundleMessageSource.setBasenames(env.getProperty("messageSource.basenames"));
		reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(Boolean.valueOf(env.getProperty("messageSource.useCodeAsDefaultMessage")));
		reloadableResourceBundleMessageSource.setDefaultEncoding(env.getProperty("messageSource.defaultEncoding"));
		reloadableResourceBundleMessageSource.setCacheSeconds(Integer.valueOf(env.getProperty("messageSource.cacheSeconds")));
		return reloadableResourceBundleMessageSource;
	}
}
