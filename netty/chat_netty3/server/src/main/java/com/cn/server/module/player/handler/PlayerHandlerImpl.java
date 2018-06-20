package com.cn.server.module.player.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cn.common.core.exception.ErrorCodeException;
import com.cn.common.core.model.Result;
import com.cn.common.core.model.ResultCode;
import com.cn.common.core.session.Session;
import com.cn.common.module.player.request.LoginRequest;
import com.cn.common.module.player.request.RegisterRequest;
import com.cn.common.module.player.response.PlayerResponse;
import com.cn.server.module.player.service.PlayerService;
/**
 * 玩家模块
 *
 *
 */
@Component
public class PlayerHandlerImpl implements PlayerHandler{
	
	@Autowired
	private PlayerService playerService;

	@Override
	public Result<PlayerResponse> registerAndLogin(Session session, byte[] data) {
		
		PlayerResponse result = null;
		try {
			//反序列化
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.readFromBytes(data);
			
			//参数判空
			if(StringUtils.isEmpty(registerRequest.getPlayerName()) || StringUtils.isEmpty(registerRequest.getPassward())){
				return Result.ERROR(ResultCode.PLAYERNAME_NULL);
			}
			
			//执行业务
			result = playerService.registerAndLogin(session, registerRequest.getPlayerName(), registerRequest.getPassward());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS(result);
	}

	@Override
	public Result<PlayerResponse> login(Session session, byte[] data) {
		PlayerResponse result = null;
		try {
			//反序列化
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.readFromBytes(data);
			
			//参数判空
			if(StringUtils.isEmpty(loginRequest.getPlayerName()) || StringUtils.isEmpty(loginRequest.getPassward())){
				return Result.ERROR(ResultCode.PLAYERNAME_NULL);
			}
			
			//执行业务
			result = playerService.login(session, loginRequest.getPlayerName(), loginRequest.getPassward());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS(result);
	}

}
