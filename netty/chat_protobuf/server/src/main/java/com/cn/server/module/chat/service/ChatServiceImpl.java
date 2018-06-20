package com.cn.server.module.chat.service;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.common.core.exception.ErrorCodeException;
import com.cn.common.core.model.ResultCode;
import com.cn.common.core.session.SessionManager;
import com.cn.common.module.ModuleId;
import com.cn.common.module.chat.ChatCmd;
import com.cn.common.module.chat.proto.ChatModule;
import com.cn.common.module.chat.proto.ChatModule.ChatResponse;
import com.cn.common.module.chat.proto.ChatModule.ChatType;
import com.cn.server.module.player.dao.PlayerDao;
import com.cn.server.module.player.dao.entity.Player;
/**
 * 聊天服务
 *
 *
 */
@Component
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private PlayerDao playerDao;

	@Override
	public void publicChat(long playerId, String content) {
		
		Player player = playerDao.getPlayerById(playerId);
		
		
		//获取所有在线玩家
		Set<Long> onlinePlayers = SessionManager.getOnlinePlayers();
		
		//创建消息对象
		ChatResponse chatResponse = ChatModule.ChatResponse.newBuilder()
				.setChatType(ChatType.PUBLIC)
				.setSendPlayerId(player.getPlayerId())
				.setSendPlayerName(player.getPlayerName())
				.setMessage(content)
				.build();
		
		//发送消息
		for(long targetPlayerId : onlinePlayers){
			SessionManager.sendMessage(targetPlayerId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
		}
		
	}

	@Override
	public void privateChat(long playerId, long targetPlayerId, String content) {
		
		//不能和自己私聊
		if(playerId == targetPlayerId){
			throw new ErrorCodeException(ResultCode.CAN_CHAT_YOUSELF);
		}
		
		Player player = playerDao.getPlayerById(playerId);
		
		//判断目标是否存在
		Player targetPlayer = playerDao.getPlayerById(targetPlayerId);
		if(targetPlayer == null){
			throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
		}
		
		//判断对方是否在线
		if(!SessionManager.isOnlinePlayer(targetPlayerId)){
			throw new ErrorCodeException(ResultCode.PLAYER_NO_ONLINE);
		}
		
		//创建消息对象
		ChatResponse chatResponse = ChatModule.ChatResponse.newBuilder()
				.setChatType(ChatType.PRIVATE)
				.setSendPlayerId(player.getPlayerId())
				.setSendPlayerName(player.getPlayerName())
				.setTargetPlayerName(targetPlayer.getPlayerName())
				.setMessage(content)
				.build();
		
		//给目标对象发送消息
		SessionManager.sendMessage(targetPlayerId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
		//给自己也回一个(偷懒做法)
		SessionManager.sendMessage(playerId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
	}
}
