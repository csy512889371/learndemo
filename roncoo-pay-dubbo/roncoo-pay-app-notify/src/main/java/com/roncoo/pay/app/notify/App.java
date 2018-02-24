/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.notify;

import java.util.concurrent.DelayQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.roncoo.pay.app.notify.core.NotifyPersist;
import com.roncoo.pay.app.notify.core.NotifyTask;
import com.roncoo.pay.service.notify.aip.RpNotifyService;

/**
 * 商户通知应用启动类.
 * @author WuShuicheng.
 *
 */
public class App 
{
    private static final Log LOG = LogFactory.getLog(App.class);

    /**
     * 通知任务延时队列，对象只能在其到期时才能从队列中取走。
     */
    public static DelayQueue<NotifyTask> tasks = new DelayQueue<NotifyTask>();

    private static ClassPathXmlApplicationContext context;

    private static ThreadPoolTaskExecutor threadPool;

    public static RpNotifyService rpNotifyService;

    public static NotifyPersist notifyPersist;
    

    public static void main(String[] args) {
        try {
            context = new ClassPathXmlApplicationContext(new String[] { "spring/spring-context.xml" });
            context.start();
            threadPool = (ThreadPoolTaskExecutor) context.getBean("threadPool");
            rpNotifyService = (RpNotifyService) context.getBean("rpNotifyService");
            
            notifyPersist = (NotifyPersist) context.getBean("notifyPersist");
             .initNotifyDataFromDB();; // 从数据库中取一次数据用来当系统启动时初始化（此处可优化）
            
            startThread(); // 启动任务处理线程
            
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

    private static void startThread() {
        LOG.info("==>startThread");

        threadPool.execute(new Runnable() {
            public void run() {
                try {
                    while (true) {
                    	LOG.info("==>threadPool.getActiveCount():" + threadPool.getActiveCount());
                    	LOG.info("==>threadPool.getMaxPoolSize():" + threadPool.getMaxPoolSize());
                        // 如果当前活动线程等于最大线程，那么不执行
                        if (threadPool.getActiveCount() < threadPool.getMaxPoolSize()) {
                        	LOG.info("==>tasks.size():" + tasks.size());
                            final NotifyTask task = tasks.take(); //使用take方法获取过期任务,如果获取不到,就一直等待,知道获取到数据
                            if (task != null) {
                                threadPool.execute(new Runnable() {
                                    public void run() {
                                        tasks.remove(task);
                                        task.run(); // 执行通知处理
                                        LOG.info("==>tasks.size():" + tasks.size());
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.error("系统异常;",e);
                }
            }
        });
    }

}
