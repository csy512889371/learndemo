/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.roncoo.pay.app.message.scheduled.MessageScheduled;

/**
 * 消息处理定时器<br/>
 * 主要分为两步： MessageStatusEnum <br/>
 * 1.处理状态为“待确认”但已超时的消息 <br/>
 * 2.处理状态为“发送中”但超时没有被成功消费确认的消息 <br/>
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class MessageTask {

	private static final Log log = LogFactory.getLog(MessageTask.class);

	private MessageTask() {

	}

	public static void main(String[] args) {

		try {

			@SuppressWarnings("resource")
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-context.xml" });
			context.start();
			log.info("定时任务开始执行>>>");
			final MessageScheduled settScheduled = (MessageScheduled) context.getBean("messageScheduled");
			ThreadPoolTaskExecutor threadPool = (ThreadPoolTaskExecutor) context.getBean("threadPool");

			// 开一个子线程处理状态为“待确认”但已超时的消息.
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {

						log.info("执行(处理[waiting_confirm]状态的消息)任务开始");
						settScheduled.handleWaitingConfirmTimeOutMessages();
						log.info("执行(处理[waiting_confirm]状态的消息)任务结束");
						
						try {
							log.info("[waiting_confirm]睡眠60秒");
							Thread.sleep(60000);
						} catch (InterruptedException e) {
						}
					}
				}
			});

			// 开一个子线程处理状态为“发送中”但超时没有被成功消费确认的消息
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						log.info("执行(处理[SENDING]的消息)任务开始");
						settScheduled.handleSendingTimeOutMessage();
						log.info("执行(处理[SENDING]的消息)任务结束");
						try {
							log.info("[SENDING]睡眠60秒");
							Thread.sleep(60000);
						} catch (InterruptedException e) {
						}
					}
				}
			});

		} catch (Exception e) {
			log.error("===>DubboReference context start error:", e);
		}
	}
}
