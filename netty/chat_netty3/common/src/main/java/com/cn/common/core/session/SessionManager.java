package com.cn.common.core.session;

import java.util.Collections;
import java.util.Set;

import org.jboss.netty.util.internal.ConcurrentHashMap;

import com.cn.common.core.model.Response;
import com.cn.common.core.serial.Serializer;
import com.cn.common.core.session.Session;
import com.google.protobuf.GeneratedMessage;
/**
 * 会话管理者
 *
 *
 */
public class SessionManager {

	/**
	 * 在线会话
	 */
	private static final ConcurrentHashMap<Long, Session> onlineSessions = new ConcurrentHashMap<>();
	
	/**
	 * 加入
	 * @param playerId
	 * @param channel
	 * @return
	 */
	public static boolean putSession(long playerId, Session session){
		if(!onlineSessions.containsKey(playerId)){
			boolean success = onlineSessions.putIfAbsent(playerId, session)== null? true : false;
			return success;
		}
		return false;
	}
	
	/**
	 * 移除
	 * @param playerId
	 */
	public static Session removeSession(long playerId){
		return onlineSessions.remove(playerId);
	}
	
	/**
	 * 发送消息[自定义协议]
	 * @param <T>
	 * @param playerId
	 * @param message
	 */
	public static <T extends Serializer> void sendMessage(long playerId, short module, short cmd, T message){
		Session session = onlineSessions.get(playerId);
		if (session != null && session.isConnected()) {
			Response response = new Response(module, cmd, message.getBytes());
			session.write(response);
		}
	}
	
	/**
	 * 发送消息[protoBuf协议]
	 * @param <T>
	 * @param playerId
	 * @param message
	 */
	public static <T extends GeneratedMessage> void sendMessage(long playerId, short module, short cmd, T message){
		Session session = onlineSessions.get(playerId);
		if (session != null && session.isConnected()) {
			Response response = new Response(module, cmd, message.toByteArray());
			session.write(response);
		}
	}
	
	/**
	 * 是否在线
	 * @param playerId
	 * @return
	 */
	public static boolean isOnlinePlayer(long playerId){
		return onlineSessions.containsKey(playerId);
	}
	
	/**
	 * 获取所有在线玩家
	 * @return
	 */
	public static Set<Long> getOnlinePlayers() {
		return Collections.unmodifiableSet(onlineSessions.keySet());
	}
}
