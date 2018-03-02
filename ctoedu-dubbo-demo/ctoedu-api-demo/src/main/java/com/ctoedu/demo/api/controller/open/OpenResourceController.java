package com.ctoedu.demo.api.controller.open;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.facade.auth.domain.Authorization;
import com.ctoedu.demo.facade.auth.service.UmsAuthFacade;
import com.ctoedu.demo.facade.jwt.domain.Payload;
import com.ctoedu.demo.facade.jwt.service.UmsJwtFacade;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;
import com.ctoedu.demo.facade.resource.service.UmsControllerResFacade;
import com.ctoedu.demo.facade.resource.service.UmsMenuResFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

@Controller
@RequestMapping("/api/open/resource")
public class OpenResourceController {
	
	@Reference
	private UmsMenuResFacade umsMenuResFacade;
	
	@Reference
	private UmsControllerResFacade umsControllerResFacade;
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsJwtFacade umsJwtFacade;

	@Reference
	private UmsAuthFacade umsAuthFacade;

	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public @ResponseBody ViewerResult getAll(HttpServletRequest request, HttpServletResponse response){
		ViewerResult result = new ViewerResult();
		try {
			JSONObject obj = JSONObject.parseObject(request.getHeader("Authorization"));
			if(obj == null || obj.getString("appSn") == null){
				request.getRequestDispatcher("/api/auth/pass/badRequest").forward(request, response);
			}else{
				String appSn = obj.getString("appSn");
				String jwt = obj.getString("token");
				if(jwt != null){
					Payload payload = umsJwtFacade.getPayload(jwt);
					if(payload == null){
						request.getRequestDispatcher("/api/auth/pass/loginExpired").forward(request, response);
					}else if(payload.getExp() > (new Date()).getTime()){
						String username = payload.getName();
						UmsUser user = umsUserFacade.findByUsername(username);
						Set<UmsMenuResources> menus = umsMenuResFacade.getMenusByUserAndAppSn(user, appSn, AvailableEnum.TRUE.getValue());
						Set<UmsControllerResources> controllers = umsControllerResFacade.getControllerResByUserAndAppSn(user, appSn, null, AvailableEnum.TRUE.getValue());
						JSONObject object = new JSONObject();
						object.put("menuResource", menus);
						object.put("requestResource", controllers);
						result.setData(object);
						result.setSuccess(true);
					}else{
						request.getRequestDispatcher("/api/auth/pass/loginExpired").forward(request, response);
					}
				}else{
					request.getRequestDispatcher("/api/auth/pass/noLogin").forward(request, response);
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("获取权限资源失败");
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/auth", method=RequestMethod.GET)
	public @ResponseBody ViewerResult auth(String path, HttpServletRequest request, HttpServletResponse response){
		ViewerResult result = new ViewerResult();
		try {
			JSONObject obj = JSONObject.parseObject(request.getHeader("Authorization"));
			if(obj == null){
				request.getRequestDispatcher("/api/auth/pass/badRequest").forward(request, response);
			}else{
				String appSn = obj.getString("appSn");
				String token = obj.getString("token");
				request.setAttribute("currentAppSn", appSn);
				Authorization authorization = umsAuthFacade.auth(path, appSn, token);
				int status = authorization.getStatus();
				UmsUser user = authorization.getUser();
				JSONObject data = new JSONObject();
				data.put("status", status);
				if(user != null) data.put("username", user.getUsername());
				result.setSuccess(true);
				result.setData(data);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage("获取权限资源失败");
			e.printStackTrace();
		}
		return result;
	}
}