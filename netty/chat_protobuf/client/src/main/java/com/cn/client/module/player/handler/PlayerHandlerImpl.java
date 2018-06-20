package com.cn.client.module.player.handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.client.swing.ResultCodeTip;
import com.cn.client.swing.Swingclient;
import com.cn.common.core.model.ResultCode;
import com.cn.common.module.player.proto.PlayerModule;
import com.cn.common.module.player.proto.PlayerModule.PlayerResponse;
import com.google.protobuf.InvalidProtocolBufferException;
/**
 * 玩家模块
 *
 *
 */
@Component
public class PlayerHandlerImpl implements PlayerHandler{
	
	@Autowired
	private Swingclient swingclient;
	@Autowired
	private ResultCodeTip resultCodeTip;

	@Override
	public void registerAndLogin(int resultCode, byte[] data) {
		if(resultCode == ResultCode.SUCCESS){
			try {
				//反序列化
				PlayerResponse playerResponse = PlayerModule.PlayerResponse.parseFrom(data);
				
				swingclient.setPlayerResponse(playerResponse);
				swingclient.getTips().setText("注册登录成功");
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
				swingclient.getTips().setText("反序列化异常");
			}
		}else{
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void login(int resultCode, byte[] data) {
		if(resultCode == ResultCode.SUCCESS){
			try {
				//反序列化
				PlayerResponse playerResponse = PlayerModule.PlayerResponse.parseFrom(data);
				
				swingclient.setPlayerResponse(playerResponse);
				swingclient.getTips().setText("登录成功");
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
				swingclient.getTips().setText("反序列化异常");
			}
		}else{
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}
}
