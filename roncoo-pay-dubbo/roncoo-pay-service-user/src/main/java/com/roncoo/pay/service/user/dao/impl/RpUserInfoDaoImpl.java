/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.user.dao.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.util.StringUtils;
import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.service.user.dao.RpUserInfoDao;
import com.roncoo.pay.service.user.entity.RpUserInfo;
import com.roncoo.pay.service.user.entity.SeqBuild;


/**
 * @类功能说明： 用户信息dao实现类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
@Repository
public class RpUserInfoDaoImpl  extends BaseDaoImpl<RpUserInfo> implements RpUserInfoDao{
	
	/** 用户编号前缀 **/
	private static final String USER_NO_PREFIX = "8888";
	
	/** 账户编号前缀 **/
	private static final String ACCOUNT_NO_PREFIX = "9999";

	@Override
	public String buildUserNo() {

		// 获取用户编号序列
		String userNoSeq = null;
		String userNo = null;
		
		try {
			// 获取用户编号序列
			userNoSeq = super.getSqlSession().selectOne(getStatement("buildUserNoSeq"));
			// 20位的用户编号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
			String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
			userNo = USER_NO_PREFIX + dateString + userNoSeq.substring(userNoSeq.length() - 8, userNoSeq.length());
		} catch (Exception e) {
			LOG.error("生成用户编号异常：", e);
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		if (StringUtils.isEmpty(userNo)) {
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		
		return userNo;

	}

	@Override
	public String buildAccountNo() {
		
		// 获取账户编号序列值，用于生成20位的账户编号
		String accountNoSeq = null;
		// 20位的账户编号规范：'9999' + yyyyMMdd(时间) + 序列的后8位
		String accountNo = null;

		try {
			// 获取账户编号序列值，用于生成20位的账户编号
			accountNoSeq = super.getSqlSession().selectOne(getStatement("buildAccountNoSeq"));
			// 20位的账户编号规范：'9999' + yyyyMMdd(时间) + 序列的后8位
			String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
			accountNo = ACCOUNT_NO_PREFIX + dateString + accountNoSeq.substring(accountNoSeq.length() - 8, accountNoSeq.length());

		} catch (Exception e) {
			LOG.error("生成账户编号异常：", e);
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		if (StringUtils.isEmpty(accountNo)) {
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
		}
		
		return accountNo;
	}
	
}