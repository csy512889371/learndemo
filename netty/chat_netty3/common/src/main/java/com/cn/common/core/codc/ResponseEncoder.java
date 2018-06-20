package com.cn.common.core.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.cn.common.core.model.Response;
/**
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * |  包头	|  模块号      |  命令号    |  结果码    |  长度       |   数据     |  
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * </pre>
 *
 *
 */
public class ResponseEncoder extends OneToOneEncoder{

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		
		Response response = (Response)msg;
		
		System.out.println("返回请求:" + "module:" +response.getModule() +" cmd:" + response.getCmd() + " resultCode:" + response.getStateCode());
		
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		//包头
		buffer.writeInt(ConstantValue.HEADER_FLAG);
		//module和cmd
		buffer.writeShort(response.getModule());
		buffer.writeShort(response.getCmd());
		//结果码
		buffer.writeInt(response.getStateCode());
		//长度
		int lenth = response.getData()==null? 0 : response.getData().length;
		if(lenth <= 0){
			buffer.writeInt(lenth);
		}else{
			buffer.writeInt(lenth);
			buffer.writeBytes(response.getData());
		}
		return buffer;
	}
}
