/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.roncoo.pay.common.core.dwz.DWZ;
import com.roncoo.pay.common.core.dwz.DwzAjax;
import com.roncoo.pay.common.core.exception.BizException;

/**
 * @描述: Spring异常拦截器.
 * 
 * @作者: zh .
 * @创建时间: 2016年5月23日-下午2:02:56.
 * @版本: 1.0 .
 */
@ControllerAdvice
public class WebExceptionHandler {

	private static final Log LOG = LogFactory.getLog(WebExceptionHandler.class);

	/**
	 * 没有权限 异常
	 * <p/>
	 * 后续根据不同的需求定制即可
	 */
	@ExceptionHandler({ BizException.class })
	@ResponseStatus(HttpStatus.OK)
	public String processBizException(HttpServletRequest request, BizException e) {
		LOG.error("BizException", e);
		DwzAjax dwz = new DwzAjax();
		dwz.setStatusCode(DWZ.ERROR);
		dwz.setMessage(e.getMsg());
		request.setAttribute("dwz", dwz);
		return "common/ajaxDone";
	}


	/**
	 * 总异常
	 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	public String processException(Exception e, HttpServletRequest request) {
		LOG.error("Exception", e);
		DwzAjax dwz = new DwzAjax();
		dwz.setStatusCode(DWZ.ERROR);
		dwz.setMessage("系统异常");
		request.setAttribute("dwz", dwz);
		return "common/ajaxDone";
	}

}
