package cn.ctoedu.service.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="bigdataboot")
public class Receiver {
   
	@RabbitHandler
	public void process(String data){
		System.out.println("Receiver:"+data);
	}
}
