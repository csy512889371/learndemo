/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.account.api;

import com.roncoo.pay.service.account.entity.RpAccount;
import com.roncoo.pay.service.account.exceptions.AccountBizException;
import org.mengyun.tcctransaction.api.TransactionContext;

import java.math.BigDecimal;


/**
 * @类功能说明： 账户操作service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
public interface RpAccountTransactionService {

	/** 加款:有银行流水 **/
	RpAccount creditToAccount(String userNo, BigDecimal amount, String requestNo,String bankTrxNo, String trxType, String remark) throws AccountBizException;

	/** 加款:有银行流水 **/
	void creditToAccountTcc(TransactionContext transactionContext, String userNo, BigDecimal amount, String requestNo,String bankTrxNo, String trxType, String remark) throws AccountBizException;

	/** 减款 :有银行流水**/
	RpAccount debitToAccount(String userNo, BigDecimal amount, String requestNo,String bankTrxNo, String trxType, String remark) throws AccountBizException;
	
	/** 加款 **/
	RpAccount creditToAccount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark) throws AccountBizException;

	/** 减款 **/
	RpAccount debitToAccount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark) throws AccountBizException;


	/** 冻结 **/
	RpAccount freezeAmount(String userNo, BigDecimal freezeAmount) throws AccountBizException;

	/** 结算成功：解冻+减款 **/
	RpAccount unFreezeAmount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark) throws AccountBizException;
	
	/** 结算失败：解冻 **/
	RpAccount unFreezeSettAmount(String userNo, BigDecimal amount) throws AccountBizException;

	/** 更新账户历史中的结算状态，并且累加可结算金额 **/
	void settCollectSuccess(String accountNo, String collectDate, int riskDay, BigDecimal totalAmount) throws AccountBizException;

}