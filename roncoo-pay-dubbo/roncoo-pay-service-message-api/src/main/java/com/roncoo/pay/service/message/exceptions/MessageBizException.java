/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.message.exceptions;

import com.roncoo.pay.common.core.exception.BizException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 消息模块业务异常类
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class MessageBizException extends BizException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3536909333010163563L;

	/** 保存的消息为空 **/
	public static final int SAVA_MESSAGE_IS_NULL = 8001;
	
	/** 消息的消费队列为空 **/
	public static final int MESSAGE_CONSUMER_QUEUE_IS_NULL = 8002;

	private static final Log LOG = LogFactory.getLog(MessageBizException.class);

	public MessageBizException() {
	}

	public MessageBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public MessageBizException(int code, String msg) {
		super(code, msg);
	}

	public MessageBizException print() {
		LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
		return this;
	}
}
