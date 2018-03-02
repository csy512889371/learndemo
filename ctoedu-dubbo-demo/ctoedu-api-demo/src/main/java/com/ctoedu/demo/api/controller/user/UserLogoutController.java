package com.ctoedu.demo.api.controller.user;

import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ctoedu.common.utils.DateUtil;
import com.ctoedu.common.utils.NetworkUtil;
import com.ctoedu.common.vo.ViewerResult;
import com.ctoedu.demo.facade.jwt.service.UmsJwtFacade;
import com.ctoedu.demo.facade.log.entity.UmsLog;
import com.ctoedu.demo.facade.log.enums.LogLevelEnum;
import com.ctoedu.demo.facade.log.enums.LogTypeEnum;
import com.ctoedu.demo.facade.log.enums.OpResultEnum;
import com.ctoedu.demo.facade.log.service.UmsLogFacade;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

/**
 * user api
 * @author feichongzheng
 *
 */
@Controller
@RequestMapping("/api/user/logout")
public class UserLogoutController {
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsJwtFacade umsJwtFacade;
	
	@Reference
	private UmsLogFacade umsLogFacade;
	
	/**
	 * logout
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody ViewerResult logout(@RequestBody JSONObject obj, HttpServletRequest request){
		ViewerResult result = new ViewerResult();
		String username = null;
		String jwt = null;
		LocalDateTime opTime = DateUtil.utilDateToLocalDateTime(new Date());
		long startTime = System.currentTimeMillis();
		try {
			username = obj.getString("username");
			jwt = obj.getString("token");
			umsJwtFacade.removeJwt(username, jwt);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		try {
			logout(username, endTime-startTime, opTime, jwt, result.isSuccess(), request);
		} catch (Exception e) {
		}
		return result;
	}
	
	private void logout(String username, long execTime, LocalDateTime opTime, String jwt, boolean success, HttpServletRequest request){
		try {
			UmsLog umsLog = new UmsLog();
			umsLog.setBackEndAccessPath("/uums/api/user/logout");
			umsLog.setBrowser(NetworkUtil.getBrowser(request));
			umsLog.setExecTime(execTime);
			umsLog.setIp(NetworkUtil.getIpAddress(request));
			umsLog.setLogLevel(LogLevelEnum.NORMAL.getValue());
			umsLog.setLogType(LogTypeEnum.LOGOUT.getValue());
			umsLog.setOpResource("用户登出");
			String desc = "用户登出：用户名【"+username+"】、令牌【"+jwt+"】";
			umsLog.setOpDesc(desc);
			umsLog.setOpResult(success ? OpResultEnum.SUCCESS.getValue() : OpResultEnum.FAIl.getValue());
			umsLog.setOpSystem(NetworkUtil.getOS(request));
			umsLog.setOpTime(opTime);
			umsLog.setUsername(username);
			umsLogFacade.save(umsLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}