package com.cn.common.module.chat.request;

import com.cn.common.core.serial.Serializer;
/**
 * 私聊请求
 *
 *
 */
public class PrivateChatRequest extends Serializer{
	
	/**
	 * 要向哪个会话发消息
	 */
	private long targetPlayerId;
	
	/**
	 * 内容
	 */
	private String context;


	public long getTargetPlayerId() {
		return targetPlayerId;
	}

	public void setTargetPlayerId(long targetPlayerId) {
		this.targetPlayerId = targetPlayerId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	protected void read() {
		this.targetPlayerId = readLong();
		this.context = readString();
	}

	@Override
	protected void write() {
		writeLong(targetPlayerId);
		writeString(context);
	}
}
