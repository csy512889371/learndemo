package cn.ctoedu.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import cn.ctoedu.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * 消费者获取消息
 * 
 * @author old wang
 * 
 */
public class Recv {

	private static final String QUEUE_NAME = "test_simple_queue";

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException,
			TimeoutException, ShutdownSignalException,
			ConsumerCancelledException, InterruptedException {

		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		// 创建频道
		Channel channel = connection.createChannel();
		
		//队列声明  
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//定义消费者
		DefaultConsumer consumer = new DefaultConsumer(channel){
			//获取到达消息
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
			 
				String msg=new String(body,"utf-8");
				System.out.println("new api recv:"+msg);
			}
		};
		
		//监听队列    
		channel.basicConsume(QUEUE_NAME, true,consumer);
	}

	private static void oldapiu() throws IOException, TimeoutException,
			InterruptedException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();

		// 创建频道
		Channel channel = connection.createChannel();
		// 定义队列的消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
		while (true) {
			Delivery delivery = consumer.nextDelivery();
			String msgString = new String(delivery.getBody());
			System.out.println("[recv] msg:" + msgString);
		}
	}

}
