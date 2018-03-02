package com.ctoedu.demo.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ctoedu.demo.api.interceptor.service.AuthService;

public class HttpInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private AuthService authService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception{
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		System.out.println("开始");
//		request.setAttribute("currentAppSn", "simba admin");
//		request.setAttribute("currentUsername", "fay123456");
//		return true;
		return authService.preHandle(request, response);
//		AuthService authService = new AuthService();
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception{
		long startTime = (long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		System.out.println("本次请求处理的时间为："+new Long(endTime-startTime)+"ms");
		request.setAttribute("handlingTime", endTime-startTime);
	}
	
	@Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("------------------afterCompletion--------------------------");
    }
	
//	@Override
//	public void afterConcurrentHandlingStarted(
//			HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		System.out.println("------------------afterConcurrentHandlingStarted--------------------------");
//	}
}
