package cn.ctoedu.service.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public Queue bigdataQueue(){
		return new Queue("bigdataboot");
	}
}
