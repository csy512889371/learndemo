/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.roncoo.pay.app.queue.utils.SpringContextUtil;

/**
 * 
 * @描述: 通知APP.
 * @作者: ZengHao,WuShuicheng.
 * @创建: 2016-5-14,下午3:11:19
 * @版本: V1.0
 *
 */
public class App { 

	private static final Log LOG = LogFactory.getLog(App.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring/spring-context.xml" });
			// 初始化SpringContextUtil
			SpringContextUtil ctxUtil = new SpringContextUtil();
			ctxUtil.setApplicationContext(context);
			
			context.start();
			LOG.info("== context start");
			
		} catch (Exception e) {
			LOG.error("== application start error:", e);
			return;
		}
		
		synchronized (App.class) {
			while (true) {
				try {
					App.class.wait();
				} catch (InterruptedException e) {
					LOG.error("== synchronized error:", e);
				}
			}
		}
	}
}
