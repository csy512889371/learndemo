package com.cn.common.core.codc;

import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import com.cn.common.core.model.Request;
/**
 * 数据包解码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头	|  模块号      |  命令号    |   长度     |   数据       |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * 包头4字节
 * 模块号2字节 
 * 命令号2字节
 * 长度4字节(数据部分占有字节数量)
 * 
 *
 *
 */
public class RequestDecoder extends ByteToMessageDecoder {
	
	/**
	 * 数据包基本长度
	 */
	public static int BASE_LENTH = 4 + 2 + 2 + 4;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		
		while(true){
			if(buffer.readableBytes() >= BASE_LENTH){
				//第一个可读数据包的起始位置
				int beginIndex;
				
				while(true) {
					//包头开始游标点
					beginIndex = buffer.readerIndex();
					//标记初始读游标位置
					buffer.markReaderIndex();
					if (buffer.readInt() == ConstantValue.HEADER_FLAG) {
						break;
					}
					//未读到包头标识略过一个字节
					buffer.resetReaderIndex();
					buffer.readByte();
					
					//不满足
					if(buffer.readableBytes() < BASE_LENTH){
						return ;
					}
				}
				//读取命令号
				short module = buffer.readShort();
				short cmd = buffer.readShort();
				
				//读取数据长度 
				int lenth = buffer.readInt();
				if(lenth < 0 ){
					ctx.channel().close();
				}
				
				//数据包还没到齐
				if(buffer.readableBytes() < lenth){
					buffer.readerIndex(beginIndex);
					return ;
				}
				
				//读数据部分
				byte[] data = new byte[lenth];
				buffer.readBytes(data);
				
				Request message = new Request();
				message.setModule(module);
				message.setCmd(cmd);
				message.setData(data);
				//解析出消息对象，继续往下面的handler传递
				out.add(message);
			}else{
				break;
			}
		}
		
		//数据不完整，等待完整的数据包
		return ;
	}
}
