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
import com.ctoedu.demo.api.controller.vo.user.LoginUserVO;
import com.ctoedu.demo.facade.jwt.service.UmsJwtFacade;
import com.ctoedu.demo.facade.log.entity.UmsLog;
import com.ctoedu.demo.facade.log.enums.LogLevelEnum;
import com.ctoedu.demo.facade.log.enums.LogTypeEnum;
import com.ctoedu.demo.facade.log.enums.OpResultEnum;
import com.ctoedu.demo.facade.log.service.UmsLogFacade;
import com.ctoedu.demo.facade.user.entity.UmsUser;
import com.ctoedu.demo.facade.user.service.UmsUserFacade;

/**
 * user api
 * @author feichongzheng
 *
 */
@Controller
@RequestMapping("/api/user/login")
public class UserLoginController {
	
	@Reference
	private UmsUserFacade umsUserFacade;
	
	@Reference
	private UmsJwtFacade umsJwtFacade;

	@Reference
	private UmsLogFacade umsLogFacade;
	
	/**
	 * login
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody ViewerResult login(@RequestBody JSONObject obj, HttpServletRequest request){
		ViewerResult result = new ViewerResult();
		UmsUser user = null;
		String appSn = null;
		String username = null;
		String password = null;
		boolean remember = false;
		String jwt = null;
		LocalDateTime opTime = DateUtil.utilDateToLocalDateTime(new Date());
		long startTime = System.currentTimeMillis();
		try {
			appSn = obj.getString("appSn");
			username = obj.getString("username");
			password = obj.getString("password");
			remember = obj.getBooleanValue("remember");
			user = umsUserFacade.login(username, password);
			username = user.getUsername();
			LoginUserVO userVO = new LoginUserVO();
			userVO.convertPOToVO(user);
			jwt = umsJwtFacade.createJwt(user.getUsername(), remember);
			userVO.setToken(jwt);
			result.setSuccess(true);
			result.setData(userVO);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrMessage(e.getMessage());
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		try {
			log(appSn, username, remember, endTime-startTime, opTime, jwt, result.isSuccess(), request);
		} catch (Exception e) {
		}
		return result;
	}
	
	private void log(String appSn, String username, boolean remember,
			long execTime, LocalDateTime opTime, String jwt, boolean success, HttpServletRequest request){
		try {
			UmsLog umsLog = new UmsLog();
			umsLog.setAppSn(appSn);
			umsLog.setBackEndAccessPath("/uums/api/user/login");
			umsLog.setBrowser(NetworkUtil.getBrowser(request));
			umsLog.setExecTime(execTime);
			umsLog.setIp(NetworkUtil.getIpAddress(request));
			umsLog.setOpResource("用户登录");
			umsLog.setLogLevel(LogLevelEnum.NORMAL.getValue());
			umsLog.setLogType(LogTypeEnum.LOGIN.getValue());
			String desc = "用户登录：用户名【"+username+"】、记住密码【"+(remember?"是":"否")+"】";
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