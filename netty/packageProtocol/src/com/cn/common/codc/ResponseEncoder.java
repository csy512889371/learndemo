package com.cn.common.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import com.cn.common.constant.ConstantValue;
import com.cn.common.model.Response;

/**
 * 请求编码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+——-----——+
 * | 包头          | 模块号        | 命令号       |  状态码    |  长度          |   数据       |
 * +——----——+——-----——+——----——+——----——+——-----——+——-----——+
 * </pre>
 * 包头4字节
 * 模块号2字节short
 * 命令号2字节short
 * 长度4字节(描述数据部分字节长度)
 * 
 *
 *
 */
public class ResponseEncoder extends OneToOneEncoder{

	@Override
	protected Object encode(ChannelHandlerContext context, Channel channel, Object rs) throws Exception {
		Response response = (Response)(rs);
		
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		//包头
		buffer.writeInt(ConstantValue.FLAG);
		//module
		buffer.writeShort(response.getModule());
		//cmd
		buffer.writeShort(response.getCmd());
		//状态码
		buffer.writeInt(response.getStateCode());
		//长度
		buffer.writeInt(response.getDataLength());
		//data
		if(response.getData() != null){
			buffer.writeBytes(response.getData());
		}
	
		return buffer;
	}

}
