package com.cn.server.module.player.handler;

import com.cn.common.core.annotion.SocketCommand;
import com.cn.common.core.annotion.SocketModule;
import com.cn.common.core.model.Result;
import com.cn.common.core.session.Session;
import com.cn.common.module.ModuleId;
import com.cn.common.module.player.PlayerCmd;
import com.cn.common.module.player.proto.PlayerModule.LoginRequest;
import com.cn.common.module.player.proto.PlayerModule.PlayerResponse;
import com.cn.common.module.player.proto.PlayerModule.RegisterRequest;
/**
 * 玩家模块
 *
 *
 */
@SocketModule(module = ModuleId.PLAYER)
public interface PlayerHandler {
	
	
	/**
	 * 创建并登录帐号
	 * @param session
	 * @param data {@link RegisterRequest}
	 * @return
	 */
	@SocketCommand(cmd = PlayerCmd.REGISTER_AND_LOGIN)
	public Result<PlayerResponse> registerAndLogin(Session session, byte[] data);
	

	/**
	 * 登录帐号
	 * @param session
	 * @param data {@link LoginRequest}
	 * @return
	 */
	@SocketCommand(cmd = PlayerCmd.LOGIN)
	public Result<PlayerResponse> login(Session session, byte[] data);

}
