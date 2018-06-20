package com.cn.server.module.chat.handler;
import com.cn.common.core.annotion.SocketCommand;
import com.cn.common.core.annotion.SocketModule;
import com.cn.common.core.model.Result;
import com.cn.common.module.ModuleId;
import com.cn.common.module.chat.ChatCmd;
import com.cn.common.module.chat.request.PrivateChatRequest;
import com.cn.common.module.chat.request.PublicChatRequest;
/**
 * 聊天
 *
 *
 */
@SocketModule(module = ModuleId.CHAT)
public interface ChatHandler {
	
	
	/**
	 * 广播消息
	 * @param playerId
	 * @param data {@link PublicChatRequest}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT)
	public Result<?> publicChat(long playerId, byte[] data);
	
	
	
	/**
	 * 私人消息
	 * @param playerId
	 * @param data {@link PrivateChatRequest}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT)
	public Result<?> privateChat(long playerId, byte[] data);
}
