package com.ctoedu.demo.api.interceptor.service;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.demo.api.constant.FaySysRoleConstant;
import com.ctoedu.demo.api.constant.FayUserConstant;
import com.ctoedu.demo.api.service.role.RoleService;
import com.ctoedu.demo.facade.auth.domain.Authorization;
import com.ctoedu.demo.facade.auth.service.UmsAuthFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;

@Service
public class AuthService {
	
	@Reference
	private UmsAuthFacade umsAuthFacade;

	@Autowired
	private RoleService roleService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String servletPath = request.getServletPath();
		if(servletPath.equals("/api/user/login")
				||servletPath.startsWith("/api/user/logout")
				||servletPath.startsWith("/api/auth/pass")
				||servletPath.startsWith("/api/open")){
			return true;
		}
		JSONObject obj = JSONObject.parseObject(request.getHeader("Authorization"));
		if(obj == null || obj.getString("appSn") == null){
			request.getRequestDispatcher("/api/auth/pass/badRequest").forward(request, response);
			return false;
		}else{
			String appSn = obj.getString("appSn");
			request.setAttribute("currentAppSn", appSn);
			String jwt = obj.getString("token");
			Authorization authorization = umsAuthFacade.auth(servletPath, appSn, jwt);
			int status = authorization.getStatus();
			UmsUser user = authorization.getUser();
			if(status == 200){
				if(user == null){
					if(jwt != null){
						request.getRequestDispatcher("/api/auth/pass/logoutAndRequestAgain").forward(request, response);
						return false;
					}
				}else{
					request.setAttribute("currentUsername", user.getUsername());
				}
				return true;
			}else{
				if(user != null
						&& (Arrays.asList(FayUserConstant.SUPER_MANAGE_USERNAME).contains(user.getUsername())
								|| roleService.validate(user.getUsername(), FaySysRoleConstant.SUPER_MANAGE_ROLE_SN))){
					request.setAttribute("currentUsername", user.getUsername());
					return true;
				}
				if(status == 400){
					request.getRequestDispatcher("/api/auth/pass/badRequest").forward(request, response);
				}else if(status == 4011){
					request.getRequestDispatcher("/api/auth/pass/loginExpired").forward(request, response);
				}else if(status == 4012){
					request.getRequestDispatcher("/api/auth/pass/noLogin").forward(request, response);
				}else if(status == 403){
					request.getRequestDispatcher("/api/auth/pass/notAuth").forward(request, response);
				}else if(status == 4031){
					request.getRequestDispatcher("/api/auth/pass/userIsNotAvailable").forward(request, response);
				}
				return false;
			}
		}
	}
}
