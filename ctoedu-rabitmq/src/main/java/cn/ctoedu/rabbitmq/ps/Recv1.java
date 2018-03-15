package cn.ctoedu.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import cn.ctoedu.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Recv1 {
	
	private static final String QUEUE_NAME="test_queue_fanout_email";
	private static final String  EXCHANGE_NAME="test_exchange_fanout";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		final Channel channel = connection.createChannel();
		
		//队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		//绑定队列到交换机 转发器
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		
		
		channel.basicQos(1);//保证一次只分发一个  
		
		//定义一个消费者
		Consumer consumer=new DefaultConsumer(channel){
			//消息到达 触发这个方法
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
			 
				String msg=new String(body,"utf-8");
				System.out.println("[1] Recv msg:"+msg);
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					System.out.println("[1] done ");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		
		boolean autoAck=false;//自动应答 false
		channel.basicConsume(QUEUE_NAME,autoAck , consumer);
	}
}
