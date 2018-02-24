/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.notify.core;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.pay.app.notify.App;
import com.roncoo.pay.app.notify.param.NotifyParam;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.service.notify.entity.RpNotifyRecord;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/2  下午5:34
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
@Service("notifyQueue")
public class NotifyQueue implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Log LOG = LogFactory.getLog(NotifyQueue.class);

    @Autowired
    private NotifyParam notifyParam;
    

    /**
     * 将传过来的对象进行通知次数判断，决定是否放在任务队列中.<br/>
     * @param notifyRecord
     * @throws Exception
     */
    public void addToNotifyTaskDelayQueue(RpNotifyRecord notifyRecord) {
        if (notifyRecord == null) {
            return;
        }
        LOG.info("===>addToNotifyTaskDelayQueue notify id:" + notifyRecord.getId());
        Integer notifyTimes = notifyRecord.getNotifyTimes(); // 通知次数
        Integer maxNotifyTimes = notifyRecord.getLimitNotifyTimes(); // 最大通知次数
        
        if (notifyRecord.getNotifyTimes().intValue() == 0) {
            notifyRecord.setLastNotifyTime(new Date()); // 第一次发送(取当前时间)
        }else{
        	notifyRecord.setLastNotifyTime(notifyRecord.getEditTime()); // 非第一次发送（取上一次修改时间，也是上一次发送时间）
        }
        
        if (notifyTimes < maxNotifyTimes) {
        	// 未超过最大通知次数，继续下一次通知
            LOG.info("===>notify id:" + notifyRecord.getId() + ", 上次通知时间lastNotifyTime:" + DateUtils.formatDate(notifyRecord.getLastNotifyTime(), "yyyy-MM-dd HH:mm:ss SSS"));
            App.tasks.put(new NotifyTask(notifyRecord, this, notifyParam));
        }
        
    }
}
