/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.notify.core;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.roncoo.pay.app.notify.App;
import com.roncoo.pay.app.notify.param.NotifyParam;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.common.core.utils.httpclient.SimpleHttpParam;
import com.roncoo.pay.common.core.utils.httpclient.SimpleHttpResult;
import com.roncoo.pay.common.core.utils.httpclient.SimpleHttpUtils;
import com.roncoo.pay.service.notify.entity.RpNotifyRecord;
import com.roncoo.pay.service.notify.enums.NotifyStatusEnum;

/**
 * @功能说明: 通知任务类.
 * @创建者: Peter
 * @创建时间: 16/6/2  下午5:34
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class NotifyTask implements Runnable, Delayed {

    private static final Log LOG = LogFactory.getLog(NotifyTask.class);

    private long executeTime;

    private RpNotifyRecord notifyRecord;

    private NotifyQueue notifyQueue;

    private NotifyParam notifyParam;

    private NotifyPersist notifyPersist = App.notifyPersist;

    public NotifyTask() {
    }

    public NotifyTask(RpNotifyRecord notifyRecord, NotifyQueue notifyQueue, NotifyParam notifyParam) {
        super();
        this.notifyRecord = notifyRecord;
        this.notifyQueue = notifyQueue;
        this.notifyParam = notifyParam;
        this.executeTime = getExecuteTime(notifyRecord);
    }

    /**
     * 计算任务允许执行的开始时间(executeTime).<br/>
     * @param record
     * @return
     */
    private long getExecuteTime(RpNotifyRecord record) {
        long lastNotifyTime = record.getLastNotifyTime().getTime(); // 最后通知时间（上次通知时间）
        Integer notifyTimes = record.getNotifyTimes(); // 已通知次数
        LOG.info("===>notifyTimes:" + notifyTimes);
        //Integer nextNotifyTimeInterval = notifyParam.getNotifyParams().get(notifyTimes + 1); // 当前发送次数对应的时间间隔数（分钟数）
        Integer nextNotifyTimeInterval = record.getNotifyRuleMap().get(String.valueOf(notifyTimes + 1)); // 当前发送次数对应的时间间隔数（分钟数）
        long nextNotifyTime = (nextNotifyTimeInterval == null ? 0 : nextNotifyTimeInterval * 60 * 1000) + lastNotifyTime;
        LOG.info("===>notify id:" + record.getId() + ", nextNotifyTime:" + DateUtils.formatDate(new Date(nextNotifyTime), "yyyy-MM-dd HH:mm:ss SSS"));
        return nextNotifyTime;
    }

    /**
     * 比较当前时间(task.executeTime)与任务允许执行的开始时间(executeTime).<br/>
     * 如果当前时间到了或超过任务允许执行的开始时间，那么就返回-1，可以执行。
     */
    public int compareTo(Delayed o) {
        NotifyTask task = (NotifyTask) o;
        return executeTime > task.executeTime ? 1 : (executeTime < task.executeTime ? -1 : 0);
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(executeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 执行通知处理.
     */
    public void run() {
        
        Integer notifyTimes = notifyRecord.getNotifyTimes(); // 得到当前通知对象的通知次数
        Integer maxNotifyTimes = notifyRecord.getLimitNotifyTimes(); // 最大通知次数
        Date notifyTime = new Date(); // 本次通知的时间
        
        // 去通知
        try {
            LOG.info("===>notify url " + notifyRecord.getUrl()+", notify id:" + notifyRecord.getId()+", notifyTimes:" + notifyTimes);

            // 执行HTTP通知请求
            SimpleHttpParam param = new SimpleHttpParam(notifyRecord.getUrl());
            SimpleHttpResult result = SimpleHttpUtils.httpRequest(param);
            
            notifyRecord.setEditTime(notifyTime); // 取本次通知时间作为最后修改时间
            notifyRecord.setNotifyTimes(notifyTimes + 1); // 通知次数+1
            
            String successValue = notifyParam.getSuccessValue(); // 通知成功标识
            String responseMsg = "";
            Integer responseStatus = result.getStatusCode();
            
            // 写通知日志表
            notifyPersist.saveNotifyRecordLogs(notifyRecord.getId(), notifyRecord.getMerchantNo(), notifyRecord.getMerchantOrderNo(), notifyRecord.getUrl(), responseMsg, responseStatus);
            LOG.info("===>insert NotifyRecordLog, merchantNo:" + notifyRecord.getMerchantNo() + ", merchantOrderNo:" + notifyRecord.getMerchantOrderNo());
            
            // 得到返回状态，如果是20X，也就是通知成功
            if (responseStatus == 200 || responseStatus == 201 || responseStatus == 202 || responseStatus == 203
                    || responseStatus == 204 || responseStatus == 205 || responseStatus == 206) {
            	
                responseMsg = result.getContent().trim();
                responseMsg = responseMsg.length() >= 600 ? responseMsg.substring(0, 600) : responseMsg; // 避免异常日志过长
                
                LOG.info("===>订单号: " + notifyRecord.getMerchantOrderNo() + " HTTP_STATUS:" + responseStatus + ",请求返回信息：" + responseMsg);
                
                // 通知成功,更新通知记录为已通知成功（以后不再通知）
                if (responseMsg.trim().equals(successValue)) {
                    notifyPersist.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(), NotifyStatusEnum.SUCCESS.name(), notifyTime);
                    return;
                }
                
                // 通知不成功（返回的结果不是success）
                if (notifyRecord.getNotifyTimes() < maxNotifyTimes) {
                	// 判断是否超过重发次数，未超重发次数的，再次进入延迟发送队列
                	notifyQueue.addToNotifyTaskDelayQueue(notifyRecord);
                	notifyPersist.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(), NotifyStatusEnum.HTTP_REQUEST_SUCCESS.name(), notifyTime);
                	LOG.info("===>update NotifyRecord status to HTTP_REQUEST_SUCCESS, notifyId:" + notifyRecord.getId());
                }else{
                	// 到达最大通知次数限制，标记为通知失败
                	notifyPersist.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(), NotifyStatusEnum.FAILED.name(), notifyTime);
                	LOG.info("===>update NotifyRecord status to failed, notifyId:" + notifyRecord.getId());
                }
                
            } else {
            	
            	// 其它HTTP响应状态码情况下
            	if (notifyRecord.getNotifyTimes() < maxNotifyTimes) {
                	// 判断是否超过重发次数，未超重发次数的，再次进入延迟发送队列
                	notifyQueue.addToNotifyTaskDelayQueue(notifyRecord);
                	notifyPersist.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(), NotifyStatusEnum.HTTP_REQUEST_FALIED.name(), notifyTime);
                	LOG.info("===>update NotifyRecord status to HTTP_REQUEST_FALIED, notifyId:" + notifyRecord.getId());
                }else{
                	// 到达最大通知次数限制，标记为通知失败
                	notifyPersist.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(), NotifyStatusEnum.FAILED.name(), notifyTime);
                	LOG.info("===>update NotifyRecord status to failed, notifyId:" + notifyRecord.getId());
                }
            }
            
        } catch (BizException e) {
            LOG.error("===>NotifyTask", e);
        } catch (Exception e) {
        	// 异常
            LOG.error("===>NotifyTask", e);
            notifyQueue.addToNotifyTaskDelayQueue(notifyRecord); // 判断是否超过重发次数，未超重发次数的，再次进入延迟发送队列
            notifyPersist.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(), NotifyStatusEnum.HTTP_REQUEST_FALIED.name(), notifyTime);
            notifyPersist.saveNotifyRecordLogs(notifyRecord.getId(), notifyRecord.getMerchantNo(), notifyRecord.getMerchantOrderNo(), notifyRecord.getUrl(), "", 0);
        }

    }
    

}
