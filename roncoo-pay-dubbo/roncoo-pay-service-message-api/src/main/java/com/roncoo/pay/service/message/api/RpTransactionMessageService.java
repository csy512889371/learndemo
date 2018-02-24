/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.message.api;

import java.util.Map;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.message.entity.RpTransactionMessage;
import com.roncoo.pay.service.message.exceptions.MessageBizException;

/**
 * <b>功能说明: </b>
 * @author Peter <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public interface RpTransactionMessageService {

	/**
	 * 预存储消息. 
	 */
	public int saveMessageWaitingConfirm(RpTransactionMessage rpTransactionMessage) throws MessageBizException;
	
	
	/**
	 * 确认并发送消息.
	 */
	public void confirmAndSendMessage(String messageId) throws MessageBizException;

	
	/**
	 * 存储并发送消息.
	 */
	public int saveAndSendMessage(RpTransactionMessage rpTransactionMessage) throws MessageBizException;

	
	/**
	 * 直接发送消息.
	 */
	public void directSendMessage(RpTransactionMessage rpTransactionMessage) throws MessageBizException;
	
	
	/**
	 * 重发消息.
	 */
	public void reSendMessage(RpTransactionMessage rpTransactionMessage) throws MessageBizException;
	
	
	/**
	 * 根据messageId重发某条消息.
	 */
	public void reSendMessageByMessageId(String messageId) throws MessageBizException;
	
	
	/**
	 * 将消息标记为死亡消息.
	 */
	public void setMessageToAreadlyDead(String messageId) throws MessageBizException;


	/**
	 * 根据消息ID获取消息
	 */
	public RpTransactionMessage getMessageByMessageId(String messageId) throws MessageBizException;

	/**
	 * 根据消息ID删除消息
	 */
	public void deleteMessageByMessageId(String messageId) throws MessageBizException;
	
	
	/**
	 * 重发某个消息队列中的全部已死亡的消息.
	 */
	public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) throws MessageBizException;
	
	/**
	 * 获取分页数据
	 */
	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws MessageBizException;


}
