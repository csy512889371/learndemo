package com.cn.common.core.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.cn.common.core.model.Request;
/**
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头	|  模块号      |  命令号    |   长度     |   数据       |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 *
 *
 */
public class RequestEncoder extends OneToOneEncoder{

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {

		Request message = (Request)msg;
		
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		//包头
		buffer.writeInt(ConstantValue.HEADER_FLAG);
		//module
		buffer.writeShort(message.getModule());
		//cmd
		buffer.writeShort(message.getCmd());
		//长度
		int lenth = message.getData()==null? 0 : message.getData().length;
		if(lenth <= 0){
			buffer.writeInt(lenth);
		}else{
			buffer.writeInt(lenth);
			buffer.writeBytes(message.getData());
		}
		return buffer;
	}

}
