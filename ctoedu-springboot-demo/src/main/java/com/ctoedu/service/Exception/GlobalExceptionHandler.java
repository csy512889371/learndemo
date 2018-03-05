package com.ctoedu.service.Exception;

import com.ctoedu.service.Domain.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
   
	@ExceptionHandler(value=MyException.class)
	@ResponseBody
	public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req,MyException e) throws Exception{
		
		ErrorInfo<String> errorInfo=new ErrorInfo<>();
		errorInfo.setMessage(e.getMessage());
		errorInfo.setCode(ErrorInfo.ERROR);
		errorInfo.setData("show error infomations");
		errorInfo.setUrl(req.getRequestURL().toString());
		return errorInfo;
	}
}
