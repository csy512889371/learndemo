/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.message.scheduled.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.roncoo.pay.app.message.biz.MessageBiz;
import com.roncoo.pay.app.message.scheduled.MessageScheduled;
import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.common.core.utils.PublicConfigUtil;
import com.roncoo.pay.service.message.api.RpTransactionMessageService;
import com.roncoo.pay.service.message.entity.RpTransactionMessage;
import com.roncoo.pay.service.message.enums.MessageStatusEnum;

/**
 * 消息定时器接口实现
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
@Component("messageScheduled")
public class MessageScheduledImpl implements MessageScheduled {
	
	private static final Log log = LogFactory.getLog(MessageScheduledImpl.class);

	@Autowired
	private RpTransactionMessageService rpTransactionMessageService;
	@Autowired
	private MessageBiz messageBiz;

	/**
	 * 处理状态为“待确认”但已超时的消息.
	 */
	public void handleWaitingConfirmTimeOutMessages() {
		try {
			
			int numPerPage = 2000; // 每页条数
			int maxHandlePageCount = 3; // 一次最多处理页数
			
			Map<String, Object> paramMap = new HashMap<String, Object>(); // 查询条件
			//paramMap.put("consumerQueue", queueName); // 队列名（可以按不同业务队列分开处理）
			paramMap.put("listPageSortType", "ASC"); // 分页查询的排序方式，正向排序
			// 获取配置的开始处理的时间
			String dateStr = getCreateTimeBefore();
			paramMap.put("createTimeBefore", dateStr);// 取存放了多久的消息
			paramMap.put("status", MessageStatusEnum.WAITING_CONFIRM.name());// 取状态为“待确认”的消息

			
			Map<String, RpTransactionMessage> messageMap = getMessageMap(numPerPage, maxHandlePageCount, paramMap);

			messageBiz.handleWaitingConfirmTimeOutMessages(messageMap);
			
		} catch (Exception e) {
			log.error("处理[waiting_confirm]状态的消息异常" + e);
		}
	}
	
	
	/**
	 * 处理状态为“发送中”但超时没有被成功消费确认的消息
	 */
	public void handleSendingTimeOutMessage() {
		try {

			int numPerPage = 2000; // 每页条数
			int maxHandlePageCount = 3; // 一次最多处理页数
			
			Map<String, Object> paramMap = new HashMap<String, Object>(); // 查询条件
			//paramMap.put("consumerQueue", queueName); // 队列名（可以按不同业务队列分开处理）
			paramMap.put("listPageSortType", "ASC"); // 分页查询的排序方式，正向排序
			// 获取配置的开始处理的时间
			String dateStr = getCreateTimeBefore();
			paramMap.put("createTimeBefore", dateStr);// 取存放了多久的消息
			paramMap.put("status", MessageStatusEnum.SENDING.name());// 取状态为发送中的消息
			paramMap.put("areadlyDead", PublicEnum.NO.name());// 取存活的发送中消息

			Map<String, RpTransactionMessage> messageMap = getMessageMap(numPerPage, maxHandlePageCount, paramMap);
			
			messageBiz.handleSendingTimeOutMessage(messageMap);
		} catch (Exception e) {
			log.error("处理发送中的消息异常" + e);
		}
	}
	
	/**
	 * 根据分页参数及查询条件批量获取消息数据.
	 * @param numPerPage 每页记录数.
	 * @param maxHandlePageCount 最多获取页数.
	 * @param paramMap 查询参数.
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, RpTransactionMessage> getMessageMap(int numPerPage, int maxHandlePageCount, Map<String, Object> paramMap){
		
		int pageNum = 1; // 当前页
		
		Map<String, RpTransactionMessage> messageMap = new HashMap<String, RpTransactionMessage>(); // 转换成map
		List<RpTransactionMessage> recordList = new ArrayList<RpTransactionMessage>(); // 每次拿到的结果集
		int pageCount = 1; // 总页数
		
		log.info("==>pageNum:" + pageNum + ", numPerPage:" + numPerPage);
		PageBean pageBean = rpTransactionMessageService.listPage(new PageParam(pageNum, numPerPage), paramMap);
		recordList = pageBean.getRecordList();
		if (recordList == null || recordList.isEmpty()) {
			log.info("==>recordList is empty");
			return messageMap;
		}
		log.info("==>now page size:" + recordList.size());
		
		for (RpTransactionMessage message : recordList) {
			messageMap.put(message.getMessageId(), message);
		}
		
		pageCount = pageBean.getTotalPage(); // 总页数(可以通过这个值的判断来控制最多取多少页)
		log.info("==>pageCount:" + pageCount);
		if (pageCount > maxHandlePageCount){
			pageCount = maxHandlePageCount;
			log.info("==>set pageCount:" + pageCount);
		}

		for (pageNum = 2; pageNum <= pageCount; pageNum++) {
			log.info("==>pageNum:" + pageNum + ", numPerPage:" + numPerPage);
			pageBean = rpTransactionMessageService.listPage(new PageParam(pageNum, numPerPage), paramMap);
			recordList = pageBean.getRecordList();
			if (recordList == null || recordList.isEmpty()) {
				break;
			}
			log.info("==>now page size:" + recordList.size());
			
			for (RpTransactionMessage message : recordList) {
				messageMap.put(message.getMessageId(), message);
			}
		}
		
		recordList = null;
		pageBean = null;
		
		return messageMap;
	}

	/**
	 * 获取配置的开始处理的时间
	 * 
	 * @return
	 */
	private String getCreateTimeBefore() {
		String duration = PublicConfigUtil.readConfig("message.handle.duration");
		long currentTimeInMillis = Calendar.getInstance().getTimeInMillis();
		Date date = new Date(currentTimeInMillis - Integer.valueOf(duration) * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}
}
