package com.cn.common.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import com.cn.common.constant.ConstantValue;
import com.cn.common.model.Response;

/**
 * response解码器
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
public class ResponseDecoder extends FrameDecoder{
	
	/**
	 * 数据包基本长度
	 */
	public static int BASE_LENTH = 4 + 2 + 2 + 4;

	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel arg1, ChannelBuffer buffer) throws Exception {
		
		//可读长度必须大于基本长度
		if(buffer.readableBytes() >= BASE_LENTH){
			
			//记录包头开始的index
			int beginReader = buffer.readerIndex();
			
			while(true){
				if(buffer.readInt() == ConstantValue.FLAG){
					break;
				}
			}
			
			//模块号
			short module = buffer.readShort();
			//命令号
			short cmd = buffer.readShort();
			//状态码
			int stateCode = buffer.readInt();
			//长度
			int length = buffer.readInt();
			
			if(buffer.readableBytes() < length){
				//还原读指针
				buffer.readerIndex(beginReader);
				return null;
			}
			
			byte[] data = new byte[length];
			buffer.readBytes(data);
			
			Response response = new Response();
			response.setModule(module);
			response.setCmd(cmd);
			response.setStateCode(stateCode);
			response.setData(data);
			
			//继续往下传递 
			return response;
			
		}
		//数据包不完整，需要等待后面的包来
		return null;
	}

}
