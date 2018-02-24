/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.controller.common;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * controller基类
 * @author zh
 * @version 2015-5-18
 */ 
public abstract class BaseController {

	/**
	 * 获取request
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
	
	/**
	 * 获取application
	 * 
	 * @return
	 */
	protected ServletContext getApplication() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext();
	}

	protected ServletContext getServletContext() {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext();
	}

}
