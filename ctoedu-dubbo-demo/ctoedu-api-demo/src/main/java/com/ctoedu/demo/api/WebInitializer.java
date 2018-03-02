package com.ctoedu.demo.api;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.ctoedu.demo.api.config.MyMvcConfig;

public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		initializeSpringParamEncoding(servletContext);
		initializeSpringConfig(servletContext);
		
	}
	
	private void initializeSpringParamEncoding(ServletContext servletContext){
		servletContext.addFilter("CharacterEncodingFilter", new CharacterEncodingFilter("UTF-8", true, true)).addMappingForUrlPatterns(null, false, "/*");
	}
	
	private void initializeSpringConfig(ServletContext servletContext){
//		servletContext.addFilter("encodingFilter", new CharacterEncodingFilter("UTF-8", true));
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(MyMvcConfig.class);
		context.setServletContext(servletContext);
		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
	}
	
//	protected Filter[] getServletFilters() {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();  
//        characterEncodingFilter.setEncoding("UTF-8");  
//        characterEncodingFilter.setForceEncoding(true);  
//        return new Filter[] {characterEncodingFilter};
//	}
}
