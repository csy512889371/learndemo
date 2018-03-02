package com.ctoedu.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
@PropertySource({"classpath:${profiles.activation}/dubbo.properties"})
public class DubboConfig {
	@Autowired
	private Environment env;
	
	/*与<dubbo:annotation/>相当.提供方扫描带有@com.alibaba.dubbo.config.annotation.Service的注解类*/
	@Bean
    public static AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.ctoedu.demo.facade");
        return annotationBean;
    }
    /*与<dubbo:application/>相当*/
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setLogger(env.getProperty("dubbo.logger"));
        applicationConfig.setName(env.getProperty("dubbo.application.providerName"));
        return applicationConfig;
    }
    /*与<dubbo:registry/>相当*/
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(env.getProperty("dubbo.registry.protocol"));
        registryConfig.setAddress(env.getProperty("dubbo.registry.address"));
        return registryConfig;
    }
    /*与<dubbo:protocol/>相当*/
    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig=new ProtocolConfig(env.getProperty("dubbo.protocol.name"),Integer.valueOf(env.getProperty("dubbo.protocol.port")));
        protocolConfig.setSerialization(env.getProperty("dubbo.protocol.serialization"));//默认为hessian2,但不支持无参构造函数类,而这种方式的效率很低
        return protocolConfig;
    }
    /*与<dubbo:monitor/>相当*/
    @Bean
    public MonitorConfig monitorConfig(){
    	MonitorConfig monitorConfig=new MonitorConfig();
    	monitorConfig.setProtocol(env.getProperty("dubbo.monitor.protocol"));
        return monitorConfig;
    }
    /*与<dubbo:provider/>相当*/
    @Bean
    public ProviderConfig providerConfig(){
    	ProviderConfig providerConfig=new ProviderConfig();
    	providerConfig.setTimeout(Integer.valueOf(env.getProperty("dubbo.provider.timeout")));
    	providerConfig.setThreadpool(env.getProperty("dubbo.provider.threadpool"));
    	providerConfig.setThreads(Integer.valueOf(env.getProperty("dubbo.provider.threads")));
    	providerConfig.setAccepts(Integer.valueOf(env.getProperty("dubbo.provider.accepts")));
        return providerConfig;
    }
}
