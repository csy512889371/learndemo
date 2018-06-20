package com.cn.server.module.chat.service;
/**
 * 聊天服务
 *
 *
 */
public interface ChatService {
	
	
	/**
	 * 群发消息
	 * @param playerId
	 * @param content
	 */
	public void publicChat(long playerId, String content);
	
	
	/**
	 * 私聊
	 * @param playerId
	 * @param targetPlayerId
	 * @param content
	 */
	public void privateChat(long playerId, long targetPlayerId, String content);
	
}
