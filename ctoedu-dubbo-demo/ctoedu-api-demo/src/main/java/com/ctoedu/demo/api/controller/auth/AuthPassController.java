package com.ctoedu.demo.api.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.api.http.ResponseStatus;

@Controller
@RequestMapping("/api/auth/pass")
public class AuthPassController {
	
	@RequestMapping("/notAuth")
	public @ResponseBody ViewerResult notAuth(HttpServletRequest request, HttpServletResponse response){
		Object requestName = request.getAttribute("requestName");
		ViewerResult result = new ViewerResult();
		result.setSuccess(false);
		if(requestName == null){
			result.setErrMessage("你无权访问");
		}else{
			result.setErrMessage((String)requestName+"，你无权访问");
		}
		response.setStatus(ResponseStatus.FORBIDDEN);
		return result;
	}
	
	@RequestMapping("/loginExpired")
	public @ResponseBody ViewerResult loginExpired(HttpServletResponse response){
		ViewerResult result = new ViewerResult();
		result.setSuccess(false);
		result.setErrMessage("账号已过期，请重新登录");
		response.setStatus(ResponseStatus.NEED_LOGIN_AGAIN);
		return result;
	}
	
	@RequestMapping("/noLogin")
	public @ResponseBody ViewerResult noLogin(HttpServletResponse response){
		ViewerResult result = new ViewerResult();
		result.setSuccess(false);
		result.setErrMessage("您尚未登录，请先登录");
		response.setStatus(ResponseStatus.NEED_LOGIN_AGAIN);
		return result;
	}
	
	@RequestMapping("/badRequest")
	public @ResponseBody ViewerResult badRequest(HttpServletResponse response){
		ViewerResult result = new ViewerResult();
		result.setSuccess(false);
		result.setErrMessage("发送的请求不正确");
		response.setStatus(ResponseStatus.BAD_REQUEST);
		return result;
	}
	
	@RequestMapping("/logoutAndRequestAgain")
	public @ResponseBody ViewerResult logoutAndRequestAgain(HttpServletResponse response){
		ViewerResult result = new ViewerResult();
		result.setSuccess(false);
		result.setErrMessage("退出登录后重新请求");
		response.setStatus(ResponseStatus.NEED_LOGIN_AGAIN);
		return result;
	}
	
	@RequestMapping("/userIsNotAvailable")
	public @ResponseBody ViewerResult userIsNotAvailable(HttpServletResponse response){
		ViewerResult result = new ViewerResult();
		result.setSuccess(false);
		result.setErrMessage("该用户已被禁用");
		response.setStatus(ResponseStatus.USER_FORBIDDEN);
		return result;
	}
}
