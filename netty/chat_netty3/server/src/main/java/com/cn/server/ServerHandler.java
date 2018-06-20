package com.cn.server;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.cn.common.core.model.Request;
import com.cn.common.core.model.Response;
import com.cn.common.core.model.Result;
import com.cn.common.core.model.ResultCode;
import com.cn.common.core.serial.Serializer;
import com.cn.common.core.session.Session;
import com.cn.common.core.session.SessionImpl;
import com.cn.common.core.session.SessionManager;
import com.cn.common.module.ModuleId;
import com.cn.server.module.player.dao.entity.Player;
import com.cn.server.scanner.Invoker;
import com.cn.server.scanner.InvokerHoler;
import com.google.protobuf.GeneratedMessage;
/**
 * 消息接受处理类
 *
 *
 */
public class ServerHandler extends SimpleChannelHandler {
	
	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

		Request request = (Request)e.getMessage();
	
		handlerMessage(new SessionImpl(ctx.getChannel()), request);
	}
	
	
	/**
	 * 消息处理
	 * @param channelId
	 * @param request
	 */
	private void handlerMessage(Session session, Request request){
		
		Response response = new Response(request);
		
		System.out.println("module:"+request.getModule() + "   " + "cmd：" + request.getCmd());
		
		//获取命令执行器
		Invoker invoker = InvokerHoler.getInvoker(request.getModule(), request.getCmd());
		if(invoker != null){
			try {
				Result<?> result = null;
				//假如是玩家模块传入channel参数，否则传入playerId参数
				if(request.getModule() == ModuleId.PLAYER){
					result = (Result<?>)invoker.invoke(session, request.getData());
				}else{
					Object attachment = session.getAttachment();
					if(attachment != null){
						Player player = (Player) attachment;
						result = (Result<?>)invoker.invoke(player.getPlayerId(), request.getData());
					}else{
						//会话未登录拒绝请求
						response.setStateCode(ResultCode.LOGIN_PLEASE);
						session.write(response);
						return;
					}
				}
				
				//判断请求是否成功
				if(result.getResultCode() == ResultCode.SUCCESS){
					//回写数据
					Object object = result.getContent();
					if(object != null){
						if(object instanceof Serializer){
							Serializer content = (Serializer)object;
							response.setData(content.getBytes());
						}else if(object instanceof GeneratedMessage){
							GeneratedMessage content = (GeneratedMessage)object;
							response.setData(content.toByteArray());
						}else{
							System.out.println(String.format("不可识别传输对象:%s", object));
						}
					}
					session.write(response);
				}else{
					//返回错误码
					response.setStateCode(result.getResultCode());
					session.write(response);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				//系统未知异常
				response.setStateCode(ResultCode.UNKOWN_EXCEPTION);
				session.write(response);
			}
		}else{
			//未找到执行者
			response.setStateCode(ResultCode.NO_INVOKER);
			session.write(response);
			return;
		}
	}
	
	/**
	 * 断线移除会话
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		Session session = new SessionImpl(ctx.getChannel());
		Object object = session.getAttachment();
		if(object != null){
			Player player = (Player)object;
			SessionManager.removeSession(player.getPlayerId());
		}
	}
}
