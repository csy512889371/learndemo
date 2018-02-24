/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.queue.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/** 
 * ClassName: SpringContextUtil <br/> 
 * Function:  <br/> 
 * date: 2016-7-11 下午4:04:16 <br/> 
 * @author zh
 */
public class SpringContextUtil implements ApplicationContextAware{

	 // Spring应用上下文环境  
    private static ApplicationContext applicationContext;  
  
    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     *  
     * @param applicationContext 
     */  
    public void setApplicationContext(ApplicationContext applicationContext) {  
        SpringContextUtil.applicationContext = applicationContext;  
    }  
  
    /** 
     * @return ApplicationContext 
     */  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    /** 
     * 获取对象 
     * 这里重写了bean方法，起主要作用 
     * @param name 
     * @return Object 一个以所给名字注册的bean的实例 
     * @throws BeansException 
     */  
    public static Object getBean(String name) throws BeansException {  
        return applicationContext.getBean(name);  
    }  
}
