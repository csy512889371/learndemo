/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.notify.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.pay.app.notify.param.NotifyParam;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.notify.aip.RpNotifyService;
import com.roncoo.pay.service.notify.entity.RpNotifyRecord;
import com.roncoo.pay.service.notify.entity.RpNotifyRecordLog;

/**
 * @功能说明: 通知记录持久化类.
 * @创建者: Peter
 * @创建时间: 16/6/2  下午5:32
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
@Service("notifyPersist")
public class NotifyPersist {
	
	private static final Log LOG = LogFactory.getLog(NotifyPersist.class);

    @Autowired
    private RpNotifyService rpNotifyService;
    
    @Autowired
    private NotifyParam notifyParam;
    
    @Autowired
    private NotifyQueue notifyQueue;

    /**
     * 创建商户通知记录.<br/>
     *
     * @param notifyRecord
     * @return
     */
    public long saveNotifyRecord(RpNotifyRecord notifyRecord) {
        return rpNotifyService.createNotifyRecord(notifyRecord);
    }

    /**
     * 更新商户通知记录.<br/>
     *
     * @param id
     * @param notifyTimes
     *            通知次数.<br/>
     * @param status
     *            通知状态.<br/>
     * @return 更新结果
     */
    public  void updateNotifyRord(String id, int notifyTimes, String status, Date editTime) {
        RpNotifyRecord notifyRecord = rpNotifyService.getNotifyRecordById(id);
        notifyRecord.setNotifyTimes(notifyTimes);
        notifyRecord.setStatus(status);
        notifyRecord.setEditTime(editTime);
        notifyRecord.setLastNotifyTime(editTime);
        rpNotifyService.updateNotifyRecord(notifyRecord);
    }

    /**
     * 创建商户通知日志记录.<br/>
     *
     * @param notifyId
     *            通知记录ID.<br/>
     * @param merchantNo
     *            商户编号.<br/>
     * @param merchantOrderNo
     *            商户订单号.<br/>
     * @param request
     *            请求信息.<br/>
     * @param response
     *            返回信息.<br/>
     * @param httpStatus
     *            通知状态(HTTP状态).<br/>
     * @return 创建结果
     */
    public long saveNotifyRecordLogs(String notifyId, String merchantNo, String merchantOrderNo, String request, String response, int httpStatus) {
        
    	RpNotifyRecordLog notifyRecordLog = new RpNotifyRecordLog();
    	notifyRecordLog.setNotifyId(notifyId);
    	notifyRecordLog.setMerchantNo(merchantNo);
        notifyRecordLog.setMerchantOrderNo(merchantOrderNo);
        notifyRecordLog.setRequest(request);
        notifyRecordLog.setResponse(response);
        notifyRecordLog.setHttpStatus(httpStatus);
        notifyRecordLog.setCreateTime(new Date());
        notifyRecordLog.setEditTime(new Date());
        return rpNotifyService.createNotifyRecordLog(notifyRecordLog);
        
    }
    
    /**
     * 从数据库中取一次数据用来当系统启动时初始化
     */
    public void initNotifyDataFromDB() {
    	LOG.info("===>init get notify data from database");

    	int pageNum = 1; // 当前页
        int numPerPage = 10; // 每页记录数
        PageParam pageParam = new PageParam(pageNum, numPerPage);
        
        List<RpNotifyRecord> recordList = new ArrayList<RpNotifyRecord>(); // 每次拿到的结果集
        
        // 组装查询条件，通知状态不成功，并且还没有超过最大通知次数的
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("statusNotSuccess", "statusNotSuccess"); // 不是成功状态
        paramMap.put("statusNotFailed", "statusNotFailed"); // 不是失败状态
        paramMap.put("maxNotifyTimes", notifyParam.getMaxNotifyTimes());
        
        // >>>>>>>>>> 查第一页开始 >>>>>>>>>>
     	LOG.info("==>pageNum:" + pageNum + ", numPerPage:" + numPerPage);
        PageBean<RpNotifyRecord> pageBean = rpNotifyService.queryNotifyRecordListPage(pageParam, paramMap);
        recordList = pageBean.getRecordList();
		if (recordList == null || recordList.isEmpty()) {
			LOG.info("==>recordList is empty");
			return;
		}
		LOG.info("==>now page size:" + recordList.size());
		
        int totalPage = pageBean.getTotalPage(); //总页数
        int totalCount = pageBean.getTotalCount(); // 总记录数
        LOG.info("===>totalPage:" + totalPage);
        LOG.info("===>totalCount:" + totalCount);
        
        for (RpNotifyRecord notifyRecord : recordList) {
            notifyQueue.addToNotifyTaskDelayQueue(notifyRecord);
        }
        
        // 如果有第2页或以上页
        for (pageNum = 2; pageNum <= totalPage; pageNum++) {
        	LOG.info("==>pageNum:" + pageNum + ", numPerPage:" + numPerPage);
            pageBean = rpNotifyService.queryNotifyRecordListPage(new PageParam(pageNum, numPerPage), paramMap);
            recordList = pageBean.getRecordList();
    		if (recordList == null || recordList.isEmpty()) {
    			LOG.info("==>recordList is empty");
    			return;
    		}
    		LOG.info("==>now page size:" + recordList.size());
            
            for (RpNotifyRecord notifyRecord : recordList) {
                notifyQueue.addToNotifyTaskDelayQueue(notifyRecord);
            }
        }
    }

}
