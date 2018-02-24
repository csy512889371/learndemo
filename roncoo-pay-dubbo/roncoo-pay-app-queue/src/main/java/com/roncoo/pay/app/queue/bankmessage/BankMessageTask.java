/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.queue.bankmessage;

import com.roncoo.pay.app.queue.bankmessage.biz.BankMessageBiz;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class BankMessageTask implements Runnable {

    private static final Log LOG = LogFactory.getLog(BankMessageTask.class);

    @Autowired
    private BankMessageBiz bankMessageBiz;

    private Map<String , String> notifyMessageMap;

    public BankMessageTask(Map<String , String> notifyMessageMap){
        this.notifyMessageMap = notifyMessageMap;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        bankMessageBiz.completePay(notifyMessageMap);

    }

    public Map<String, String> getNotifyMessageMap() {
        return notifyMessageMap;
    }

    public void setNotifyMessageMap(Map<String, String> notifyMessageMap) {
        this.notifyMessageMap = notifyMessageMap;
    }

    public BankMessageBiz getBankMessageBiz() {
        return bankMessageBiz;
    }

    public void setBankMessageBiz(BankMessageBiz bankMessageBiz) {
        this.bankMessageBiz = bankMessageBiz;
    }
}
