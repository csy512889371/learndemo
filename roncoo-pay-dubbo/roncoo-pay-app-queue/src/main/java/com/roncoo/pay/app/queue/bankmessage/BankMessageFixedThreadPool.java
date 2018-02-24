/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.queue.bankmessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class BankMessageFixedThreadPool {

  private  static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

   public static void addTask(BankMessageTask bankMessageTask){
       fixedThreadPool.execute(bankMessageTask);
   }
}
