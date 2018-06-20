package com.cn.server.module.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cn.common.core.exception.ErrorCodeException;
import com.cn.common.core.model.Result;
import com.cn.common.core.model.ResultCode;
import com.cn.common.module.chat.request.PrivateChatRequest;
import com.cn.common.module.chat.request.PublicChatRequest;
import com.cn.server.module.chat.service.ChatService;

@Component
public class ChatHandlerImpl implements ChatHandler{
	
	@Autowired
	private ChatService chatService;

	@Override
	public Result<?> publicChat(long playerId, byte[] data) {
		try {
			//反序列化
			PublicChatRequest request = new PublicChatRequest();
			request.readFromBytes(data);
			
			//参数校验
			if(StringUtils.isEmpty(request.getContext())){
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}
			
			//执行业务
			chatService.publicChat(playerId, request.getContext());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}

	@Override
	public Result<?> privateChat(long playerId, byte[] data) {
		try {
			//反序列化
			PrivateChatRequest request = new PrivateChatRequest();
			request.readFromBytes(data);
			
			//参数校验
			if(StringUtils.isEmpty(request.getContext()) || request.getTargetPlayerId() <= 0){
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}
			
			//执行业务
			chatService.privateChat(playerId, request.getTargetPlayerId(), request.getContext());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}
}
