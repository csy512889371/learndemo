/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.message.dao.impl;

import org.springframework.stereotype.Repository;

import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.service.message.dao.RpTransactionMessageDao;
import com.roncoo.pay.service.message.entity.RpTransactionMessage;

/**
 * <b>功能说明: </b>
 *
 * @author Peter <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Repository("rpTransactionMessageDao")
public class RpTransactionMessageDaoImpl extends BaseDaoImpl<RpTransactionMessage> implements RpTransactionMessageDao {

}
