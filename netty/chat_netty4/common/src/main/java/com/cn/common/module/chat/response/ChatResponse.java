package com.cn.common.module.chat.response;

import com.cn.common.core.serial.Serializer;
/**
 * 聊天消息
 *
 *
 */
public class ChatResponse extends Serializer{
	
	/**
	 * 发送者id
	 */
	private long sendPlayerId;
	
	/**
	 * 玩家名
	 */
	private String sendPlayerName;
	
	/**
	 * 目标玩家
	 */
	private String tartgetPlayerName;
	
	/**
	 * 消息类型
	 * 0 广播消息
	 * 1 私聊
	 * {@link ChatType}
	 */
	private byte chatType;
	
	/**
	 * 消息
	 */
	private String message;

	
	public String getTartgetPlayerName() {
		return tartgetPlayerName;
	}

	public void setTartgetPlayerName(String tartgetPlayerName) {
		this.tartgetPlayerName = tartgetPlayerName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getSendPlayerId() {
		return sendPlayerId;
	}

	public void setSendPlayerId(long sendPlayerId) {
		this.sendPlayerId = sendPlayerId;
	}

	public String getSendPlayerName() {
		return sendPlayerName;
	}

	public void setSendPlayerName(String sendPlayerName) {
		this.sendPlayerName = sendPlayerName;
	}

	public byte getChatType() {
		return chatType;
	}

	public void setChatType(byte chatType) {
		this.chatType = chatType;
	}

	@Override
	protected void read() {
		this.sendPlayerId = readLong();
		this.sendPlayerName = readString();
		this.tartgetPlayerName = readString();
		this.chatType = readByte();
		this.message = readString();
	}

	@Override
	protected void write() {
		writeLong(this.sendPlayerId);
		writeString(this.sendPlayerName);
		writeString(this.tartgetPlayerName);
		writeByte(this.chatType);
		writeString(this.message);
	}
}
                                                  