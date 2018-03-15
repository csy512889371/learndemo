package cn.ctoedu.rabbitmq.spring;

public class MyConsumer {

	
	   //具体执行业务的方法
    public void listen(String foo) {
        System.out.println("消费者： " + foo);
    }
	
}
