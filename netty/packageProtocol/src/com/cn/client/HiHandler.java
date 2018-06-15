package com.cn.client;

import com.cn.common.model.Response;
import com.cn.common.module.fuben.response.FightResponse;
import org.jboss.netty.channel.*;

/**
 * 消息接受处理类
 *
 *
 */
public class HiHandler extends SimpleChannelHandler {

	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
			Response message = (Response)e.getMessage();

			if(message.getModule() == 1){
				
				if(message.getCmd() == 1){
					FightResponse fightResponse = new FightResponse();
					fightResponse.readFromBytes(message.getData());
					
					System.out.println("gold:" + fightResponse.getGold());
					
				}else if(message.getCmd() == 2){
					
				}
				
			}else if (message.getModule() == 1){
				
				
			}
	}

	/**
	 * 捕获异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.out.println("exceptionCaught");
		super.exceptionCaught(ctx, e);
	}

	/**
	 * 新连接
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelConnected");
		super.channelConnected(ctx, e);
	}

	/**
	 * 必须是链接已经建立，关闭通道的时候才会触发
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelDisconnected");
		super.channelDisconnected(ctx, e);
	}

	/**
	 * channel关闭的时候触发
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelClosed");
		super.channelClosed(ctx, e);
	}
}
