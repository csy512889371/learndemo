package com.cn.common.module.chat.response;
/**
 * 聊天类型定义
 *
 *
 */
public interface ChatType {
	
	/**
	 * 广播消息
	 */
	public byte PUBLIC_CHAT = 0;
	
	/**
	 * 私聊消息
	 */
	public byte PRIVATE_CHAT = 1;
}
