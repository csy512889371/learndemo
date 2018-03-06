package cn.ctoedu.service.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {
  
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public void send(){
		String context="bigdataspringboot"+new Date();
		System.out.println("Sender:"+context);
		this.rabbitTemplate.convertAndSend("bigdataboot",context);
	}
}
