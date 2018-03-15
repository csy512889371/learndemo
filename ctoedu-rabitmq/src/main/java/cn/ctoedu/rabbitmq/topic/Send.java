package cn.ctoedu.rabbitmq.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import cn.ctoedu.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
	private static final String EXCHANGE_NAME = "test_exchange_topic";

	public static void main(String[] args) throws IOException, TimeoutException {
		
		
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		//exchange
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		String msgString="商品....";
		channel.basicPublish(EXCHANGE_NAME, "goods.delete", null, msgString.getBytes());
		System.out.println("---send "+msgString);

		channel.close();
		connection.close();
	}
}
