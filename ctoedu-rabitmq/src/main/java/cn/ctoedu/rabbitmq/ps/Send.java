package cn.ctoedu.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import cn.ctoedu.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

	private static final String  EXCHANGE_NAME="test_exchange_fanout";
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//分发
		
		//发送消息
		String msg="hello ps";
		
		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		
		System.out.println("Send :"+msg);
		
		channel.close();
		connection.close();
	}
}
