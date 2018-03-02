package com.ctoedu.demo.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;

@Configuration
//@ComponentScan(basePackages = "com.ctoedu.demo.api")
public class DubboConfig {
	
	@Value( "${dubbo.registry.address}" )
	private String dubboRegistryAddress;
	
	@Value( "${dubbo.application.consumerName}" )
	private String dubboApplicationName;
	
	@Value( "${dubbo.logger}" )
	private String dubboLogger;
	
	@Value( "${dubbo.registry.protocol}" )
	private String dubboRegistryProtocol;

	@Value( "${dubbo.protocol.name}" )
	private String dubboProtocolName;
	
	@Value( "${dubbo.protocol.port}" )
	private int dubboProtocolPort;

	@Value( "${dubbo.protocol.serialization}" )
	private String dubboProtocolSerialization;
	
	@Bean
    public static AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.ctoedu.demo.api");
        return annotationBean;
    }
	
	@Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setLogger(dubboLogger);
        applicationConfig.setName(dubboApplicationName);
        return applicationConfig;
    }
	
	@Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(dubboRegistryProtocol);
        registryConfig.setAddress(dubboRegistryAddress);
        return registryConfig;
    }
	
	@Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig=new ProtocolConfig(dubboProtocolName,dubboProtocolPort);
//        protocolConfig.setSerialization("kryo");
        protocolConfig.setSerialization(dubboProtocolSerialization);
//        protocolConfig.setSerialization("java");
        return protocolConfig;
    }
}
