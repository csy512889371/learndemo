package com.cn.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cn.client.swing.Swingclient;
import com.cn.common.core.codc.RequestEncoder;
import com.cn.common.core.codc.ResponseDecoder;
import com.cn.common.core.model.Request;

/**
 * netty客户端入门
 * 
 *
 * 
 */
@Component
public class Client {
	
	/**
	 * 界面
	 */
	@Autowired
	private Swingclient swingclient;

	/**
	 * 服务类
	 */
	Bootstrap bootstrap = new Bootstrap();

	/**
	 * 会话
	 */
	private Channel channel;

	/**
	 * 线程池
	 */
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		
		// 设置循环线程组事例
		bootstrap.group(workerGroup);

		// 设置channel工厂
		bootstrap.channel(NioSocketChannel.class);

		// 设置管道
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ResponseDecoder());
				ch.pipeline().addLast(new RequestEncoder());
				ch.pipeline().addLast(new ClientHandler(swingclient));
			}
		});
	}

	/**
	 * 连接
	 * 
	 * @param ip
	 * @param port
	 * @throws InterruptedException
	 */
	public void connect() throws InterruptedException {

		// 连接服务端
		ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 10102));
		connect.sync();
		channel = connect.channel();
	}

	/**
	 * 关闭
	 */
	public void shutdown() {
		workerGroup.shutdownGracefully();
	}

	/**
	 * 获取会话
	 * 
	 * @return
	 */
	public Channel getChannel() {
		return channel;
	}
	
	/**
	 * 发送消息
	 * @param request
	 * @throws InterruptedException 
	 */
	public void sendRequest(Request request) throws InterruptedException{
		if(channel == null || !channel.isActive()){
			connect();
		}
		channel.writeAndFlush(request);
	}
	
}
