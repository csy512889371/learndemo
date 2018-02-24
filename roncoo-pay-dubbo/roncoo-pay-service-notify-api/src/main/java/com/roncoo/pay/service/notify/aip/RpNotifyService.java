/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.notify.aip;

import java.util.Map;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.notify.entity.RpNotifyRecord;
import com.roncoo.pay.service.notify.entity.RpNotifyRecordLog;
import com.roncoo.pay.service.notify.exceptions.NotifyBizException;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/2  上午10:41
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */


public interface RpNotifyService {
	
    /**
     * 创建消息通知
     * @param rpNotifyRecord
     */
    public long createNotifyRecord(RpNotifyRecord rpNotifyRecord) throws NotifyBizException;

    /**
     * 修改消息通知
     * @param rpNotifyRecord
     */
    public void updateNotifyRecord(RpNotifyRecord rpNotifyRecord) throws NotifyBizException;

    /**
     * 创建消息通知记录
     * @param rpNotifyRecordLog
     * @return
     */
    public long createNotifyRecordLog(RpNotifyRecordLog rpNotifyRecordLog) throws NotifyBizException;
    

    /**
     * 发送消息通知
     * @param notifyUrl 通知地址
     * @param merchantOrderNo   商户订单号
     * @param merchantNo    商户编号
     */
    public void notifySend(String notifyUrl,String merchantOrderNo,String merchantNo) throws NotifyBizException;


    /**
     * 通过ID获取通知记录
     * @param id
     * @return
     */
    public RpNotifyRecord getNotifyRecordById(String id) throws NotifyBizException;

    /**
     * 根据商户编号,商户订单号,通知类型获取通知记录
     * @param merchantNo    商户编号
     * @param merchantOrderNo   商户订单号
     * @param notifyType    消息类型
     * @return
     */
    public RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo , String merchantOrderNo , String notifyType) throws NotifyBizException;

    /**
     * 按条件分页查询通知记录.
     */
    public PageBean<RpNotifyRecord> queryNotifyRecordListPage(PageParam pageParam , Map<String, Object> paramMap) throws NotifyBizException;


}
