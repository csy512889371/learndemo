/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.notify.listener;

import java.util.Date;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.app.notify.core.NotifyPersist;
import com.roncoo.pay.app.notify.core.NotifyQueue;
import com.roncoo.pay.app.notify.param.NotifyParam;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.service.notify.entity.RpNotifyRecord;
import com.roncoo.pay.service.notify.enums.NotifyStatusEnum;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/2  下午5:36
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class ConsumerSessionAwareMessageListener  implements MessageListener {

    private static final Log log = LogFactory.getLog(ConsumerSessionAwareMessageListener.class);

    @Autowired
    private NotifyQueue notifyQueue;

    @Autowired
    private NotifyPersist notifyPersist;
    
    @Autowired
    private NotifyParam notifyParam;

    /**
     * 监听消费MQ队列中的消息.
     */
    public void onMessage(Message message) {
        try {
            ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
            final String msgText = msg.getText();
            log.info("== receive message:" + msgText);

            JSON json = (JSON) JSONObject.parse(msgText);
            RpNotifyRecord notifyRecord = JSONObject.toJavaObject(json, RpNotifyRecord.class);
            if (notifyRecord == null) {
                return;
            }
            // log.info("notifyParam:" + notifyParam);
            notifyRecord.setStatus(NotifyStatusEnum.CREATED.name());
            notifyRecord.setCreateTime(new Date());
            notifyRecord.setEditTime(new Date());
            notifyRecord.setLastNotifyTime(new Date());
            notifyRecord.setNotifyTimes(0); // 初始化通知0次
            notifyRecord.setLimitNotifyTimes(notifyParam.getMaxNotifyTimes()); // 最大通知次数
            Map<Integer, Integer> notifyParams = notifyParam.getNotifyParams();
            notifyRecord.setNotifyRule(JSONObject.toJSONString(notifyParams)); // 保存JSON

//            if ( !StringUtil.isEmpty(notifyRecord.getId())){
//                RpNotifyRecord notifyRecordById = rpNotifyService.getNotifyRecordById(notifyRecord.getId());
//                if (notifyRecordById != null){
//                    return;
//                }
//            }

            try {
            	
                notifyPersist.saveNotifyRecord(notifyRecord); // 将获取到的通知先保存到数据库中
                notifyQueue.addToNotifyTaskDelayQueue(notifyRecord); // 添加到通知队列(第一次通知)
                
            }  catch (BizException e) {
                log.error("BizException :", e);
            } catch (Exception e) {
                log.error(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }


}
