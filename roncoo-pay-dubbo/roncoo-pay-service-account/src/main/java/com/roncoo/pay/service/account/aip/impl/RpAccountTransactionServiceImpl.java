/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.account.aip.impl;

import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.enums.TrxTypeEnum;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.service.account.api.RpAccountTransactionService;
import com.roncoo.pay.service.account.dao.RpAccountDao;
import com.roncoo.pay.service.account.dao.RpAccountHistoryDao;
import com.roncoo.pay.service.account.entity.RpAccount;
import com.roncoo.pay.service.account.entity.RpAccountHistory;
import com.roncoo.pay.service.account.enums.AccountFundDirectionEnum;
import com.roncoo.pay.service.account.enums.AccountHistoryStatusEnum;
import com.roncoo.pay.service.account.exceptions.AccountBizException;
import org.mengyun.tcctransaction.Compensable;
import org.mengyun.tcctransaction.api.TransactionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @类功能说明： 账户操作service实现类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
@Service("rpAccountTransactionService")
public class RpAccountTransactionServiceImpl implements RpAccountTransactionService {

	private static final Logger LOG = LoggerFactory.getLogger(RpAccountTransactionServiceImpl.class);

	@Autowired
	private RpAccountDao rpAccountDao;
	@Autowired
	private RpAccountHistoryDao rpAccountHistoryDao;
	
	

	/**
	 * 根据用户编号编号获取账户信息
	 * 
	 * @param userNo
	 *            用户编号
	 * @param isPessimist
	 *            是否加行锁
	 * @return
	 */
	private RpAccount getByUserNo_IsPessimist(String userNo, boolean isPessimist) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("isPessimist", isPessimist);
		return rpAccountDao.getByUserNo(map);
	}

	/**
	 * 加款
	 * 
	 * @param userNo
	 *            用户编号
	 * @param amount
	 *            加款金额
	 * @param requestNo
	 *            请求号
	 * @param trxType
	 *            业务类型
	 * @param remark
	 *            备注
	 */
	@Transactional(rollbackFor = Exception.class)
	public RpAccount creditToAccount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark){

		return this.creditToAccount(userNo, amount, requestNo, null, trxType, remark);
	}

	/**
	 * 加款:有银行流水
	 * 
	 * @param userNo
	 *            用户编号
	 * @param amount
	 *            加款金额
	 * @param requestNo
	 *            请求号
	 * @param trxType
	 *            业务类型
	 * @param remark
	 *            备注
	 */
	@Transactional(rollbackFor = Exception.class)
	public RpAccount creditToAccount(String userNo, BigDecimal amount, String requestNo, String bankTrxNo, String trxType, String remark) {
		RpAccount account = this.getByUserNo_IsPessimist(userNo, true);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT;
		}

		Date lastModifyDate = account.getEditTime();
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(lastModifyDate)) {
			account.setTodayExpend(BigDecimal.ZERO);
			account.setTodayIncome(BigDecimal.ZERO);
		}

		// 总收益累加和今日收益
		if (TrxTypeEnum.EXPENSE.name().equals(trxType)) {// 业务类型是交易
			account.setTotalIncome(account.getTotalIncome().add(amount));

			/***** 根据上次修改时间，统计今日收益 *******/
			if (DateUtils.isSameDayWithToday(lastModifyDate)) {
				// 如果是同一天
				account.setTodayIncome(account.getTodayIncome().add(amount));
			} else {
				// 不是同一天
				account.setTodayIncome(amount);
			}
			/************************************/
		}

		String completeSett = PublicEnum.NO.name();
		String isAllowSett = PublicEnum.YES.name();

		/** 设置余额的值 **/
		account.setBalance(account.getBalance().add(amount));
		account.setEditTime(new Date());

		// 记录账户历史
		RpAccountHistory accountHistoryEntity = new RpAccountHistory();
		accountHistoryEntity.setCreateTime(new Date());
		accountHistoryEntity.setEditTime(new Date());
		accountHistoryEntity.setIsAllowSett(isAllowSett);
		accountHistoryEntity.setAmount(amount);
		accountHistoryEntity.setBalance(account.getBalance());
		accountHistoryEntity.setRequestNo(requestNo);
		accountHistoryEntity.setBankTrxNo(bankTrxNo);
		accountHistoryEntity.setIsCompleteSett(completeSett);
		accountHistoryEntity.setRemark(remark);
		accountHistoryEntity.setFundDirection(AccountFundDirectionEnum.ADD.name());
		accountHistoryEntity.setAccountNo(account.getAccountNo());
		accountHistoryEntity.setTrxType(trxType);
		accountHistoryEntity.setId(StringUtil.get32UUID());
		accountHistoryEntity.setUserNo(userNo);
		accountHistoryEntity.setStatus(AccountHistoryStatusEnum.CONFORM.name());

		this.rpAccountHistoryDao.insert(accountHistoryEntity);
		this.rpAccountDao.update(account);
		LOG.info("账户加款成功，并记录了账户历史");
		return account;
	}

	/**
	 * 加款:有银行流水
	 *
	 * @param userNo
	 *            用户编号
	 * @param amount
	 *            加款金额
	 * @param requestNo
	 *            请求号
	 * @param trxType
	 *            业务类型
	 * @param remark
	 *            备注
	 */
	@Transactional(rollbackFor = Exception.class)
	@Compensable(confirmMethod = "confirmCreditToAccountTcc",cancelMethod = "cancelCreditToAccountTcc")
	public void creditToAccountTcc(TransactionContext transactionContext, String userNo, BigDecimal amount, String requestNo, String bankTrxNo, String trxType, String remark) {

		LOG.info("===>creditToAccountTcc TRYING begin");

		RpAccount account = this.getByUserNo_IsPessimist(userNo, true);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT;
		}

		String completeSett = PublicEnum.NO.name();
		String isAllowSett = PublicEnum.YES.name();

		// 通过请求号唯一来做幂等判断
		RpAccountHistory accountHistoryEntity = rpAccountHistoryDao.getByRequestNo(requestNo);

		if (accountHistoryEntity == null){//如果账户历史为空,则创建数据,否则,不创建账户历史  防止多次提交
			// 记录账户历史
			accountHistoryEntity = new RpAccountHistory();
			accountHistoryEntity.setCreateTime(new Date());
			accountHistoryEntity.setEditTime(new Date());
			accountHistoryEntity.setIsAllowSett(isAllowSett);
			accountHistoryEntity.setAmount(amount);
			accountHistoryEntity.setBalance(account.getBalance());
			accountHistoryEntity.setRequestNo(requestNo);
			accountHistoryEntity.setBankTrxNo(bankTrxNo);
			accountHistoryEntity.setIsCompleteSett(completeSett);
			accountHistoryEntity.setRemark(remark);
			accountHistoryEntity.setFundDirection(AccountFundDirectionEnum.ADD.name());
			accountHistoryEntity.setAccountNo(account.getAccountNo());
			accountHistoryEntity.setTrxType(trxType);
			accountHistoryEntity.setId(StringUtil.get32UUID());
			accountHistoryEntity.setUserNo(userNo);
			// 状态为TRYING（业务表设计上要有对应的状态为配合TCC的状态）
			accountHistoryEntity.setStatus(AccountHistoryStatusEnum.TRYING.name()); 

			this.rpAccountHistoryDao.insert(accountHistoryEntity);
		}else if (AccountHistoryStatusEnum.CANCEL.name().equals(accountHistoryEntity.getStatus())){
			//如果是取消的,有可能是之前的业务出现异常问题而取消,那么重试阶段,再将状态更新为TYING状态,而不是重新创建一条
			LOG.info("之前因为业务问题取消后,又重试的{}" , accountHistoryEntity.getBankTrxNo());
			accountHistoryEntity.setStatus(AccountHistoryStatusEnum.TRYING.name());
			this.rpAccountHistoryDao.update(accountHistoryEntity);
		}

		LOG.info("===>creditToAccountTcc TRYING end");
	}

	/**
	 * 资金账户加款确认阶段
	 * @param transactionContext
	 * @param userNo
	 * @param amount
	 * @param requestNo
	 * @param bankTrxNo
	 * @param trxType
	 * @param remark
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void confirmCreditToAccountTcc(TransactionContext transactionContext, String userNo, BigDecimal amount, String requestNo, String bankTrxNo, String trxType, String remark) {
		LOG.info("===>confirmCreditToAccountTcc begin");

		RpAccountHistory rpAccountHistory = rpAccountHistoryDao.getByRequestNo(requestNo);

		// 幂等判断
		if (rpAccountHistory == null || !AccountHistoryStatusEnum.TRYING.name().equals(rpAccountHistory.getStatus())){
			//如果账户历史为空,或者状态为非TRYING中的,就不执行确认操作
			return;
		}

		rpAccountHistory.setStatus(AccountHistoryStatusEnum.CONFORM.name()); // 设置状态为CONFORM
		rpAccountHistoryDao.update(rpAccountHistory);

		RpAccount account = this.getByUserNo_IsPessimist(userNo, true);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT;
		}

		Date lastModifyDate = account.getEditTime();
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(lastModifyDate)) {
			account.setTodayExpend(BigDecimal.ZERO);
			account.setTodayIncome(BigDecimal.ZERO);
		}

		// 总收益累加和今日收益
		if (TrxTypeEnum.EXPENSE.name().equals(trxType)) {// 业务类型是交易
			account.setTotalIncome(account.getTotalIncome().add(amount));

			/***** 根据上次修改时间，统计今日收益 *******/
			if (DateUtils.isSameDayWithToday(lastModifyDate)) {
				// 如果是同一天
				account.setTodayIncome(account.getTodayIncome().add(amount));
			} else {
				// 不是同一天
				account.setTodayIncome(amount);
			}
			/************************************/
		}
		/** 设置余额的值 **/
		account.setBalance(account.getBalance().add(amount));
		account.setEditTime(new Date());
		this.rpAccountDao.update(account);

		LOG.info("===>confirmCreditToAccountTcc end");
	}

	
	@Transactional(rollbackFor = Exception.class)
	public void cancelCreditToAccountTcc(TransactionContext transactionContext, String userNo, BigDecimal amount, String requestNo, String bankTrxNo, String trxType, String remark) {
		LOG.info("===>cancelCreditToAccountTcc begin");

		RpAccountHistory rpAccountHistory = rpAccountHistoryDao.getByRequestNo(requestNo);

		// 幂等判断
		if (rpAccountHistory == null || !AccountHistoryStatusEnum.TRYING.name().equals(rpAccountHistory.getStatus())){//如果账户历史为空,或者状态为非TRYING中的,就不执行确认操作
			return;
		}

		rpAccountHistory.setStatus(AccountHistoryStatusEnum.CANCEL.name());//设置状态为取消状态
		rpAccountHistory.setIsAllowSett(PublicEnum.NO.name());//设置为不可结算
		rpAccountHistoryDao.update(rpAccountHistory);

		LOG.info("===>cancelCreditToAccountTcc end");
	}

	/**
	 * 减款
	 * 
	 * @param userNo
	 *            用户编号
	 * @param amount
	 *            减款金额
	 * @param requestNo
	 *            请求号
	 * @param trxType
	 *            业务类型
	 * @param remark
	 *            备注
	 */
	@Transactional(rollbackFor = Exception.class)
	public RpAccount debitToAccount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark){
		return this.debitToAccount(userNo, amount, requestNo, null, trxType, remark);
	}

	/**
	 * 减款:有银行流水
	 * 
	 * @param userNo
	 *            用户编号
	 * @param amount
	 *            减款金额
	 * @param requestNo
	 *            请求号
	 * @param trxType
	 *            业务类型
	 * @param remark
	 *            备注
	 */
	@Transactional(rollbackFor = Exception.class)
	public RpAccount debitToAccount(String userNo, BigDecimal amount, String requestNo, String bankTrxNo, String trxType, String remark) {
		RpAccount account = this.getByUserNo_IsPessimist(userNo, true);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT;
		}
		// 获取可用余额
		BigDecimal availableBalance = account.getAvailableBalance();

		String isAllowSett = PublicEnum.YES.name();
		String completeSett = PublicEnum.NO.name();

		if (availableBalance.compareTo(amount) == -1) {
			throw AccountBizException.ACCOUNT_SUB_AMOUNT_OUTLIMIT;
		}

		/** 减少总余额 **/
		account.setBalance(account.getBalance().subtract(amount));

		Date lastModifyDate = account.getEditTime();
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(lastModifyDate)) {
			account.setTodayExpend(BigDecimal.ZERO);
			account.setTodayIncome(BigDecimal.ZERO);
			account.setTodayExpend(amount);
		}else{
			account.setTodayExpend(account.getTodayExpend().add(amount));
		}
		account.setTotalExpend(account.getTodayExpend().add(amount));
		account.setEditTime(new Date());

		// 记录账户历史
		RpAccountHistory accountHistoryEntity = new RpAccountHistory();
		accountHistoryEntity.setCreateTime(new Date());
		accountHistoryEntity.setEditTime(new Date());
		accountHistoryEntity.setIsAllowSett(isAllowSett);
		accountHistoryEntity.setAmount(amount);
		accountHistoryEntity.setBalance(account.getBalance());
		accountHistoryEntity.setRequestNo(requestNo);
		accountHistoryEntity.setBankTrxNo(bankTrxNo);
		accountHistoryEntity.setIsCompleteSett(completeSett);
		accountHistoryEntity.setRemark(remark);
		accountHistoryEntity.setFundDirection(AccountFundDirectionEnum.SUB.name());
		accountHistoryEntity.setAccountNo(account.getAccountNo());
		accountHistoryEntity.setTrxType(trxType);
		accountHistoryEntity.setId(StringUtil.get32UUID());
		accountHistoryEntity.setUserNo(userNo);
		this.rpAccountHistoryDao.insert(accountHistoryEntity);
		this.rpAccountDao.update(account);
		return account;
	}

	/**
	 * 冻结账户资金
	 * 
	 * @param userNo
	 *            用户编号
	 * @param freezeAmount
	 *            冻结金额
	 **/

	@Transactional(rollbackFor = Exception.class)
	public RpAccount freezeAmount(String userNo, BigDecimal freezeAmount) {
		RpAccount account = this.getByUserNo_IsPessimist(userNo, true);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT;
		}
		account.setEditTime(new Date());
		// 比较可用余额和冻结金额
		if (!account.availableBalanceIsEnough(freezeAmount)) {
			// 可用余额不足
			throw AccountBizException.ACCOUNT_FROZEN_AMOUNT_OUTLIMIT;
		}
		account.setUnbalance(account.getUnbalance().add(freezeAmount));
		this.rpAccountDao.update(account);
		return account;
	}

	/**
	 * 结算成功 解冻金额+减款
	 * 
	 * @param userNo
	 *            用户编号
	 * @param amount
	 *            解冻和减款金额
	 * @param requestNo
	 *            流水号
	 * @param trxType
	 *            业务类型
	 * @param remark
	 *            备注
	 */

	@Transactional(rollbackFor = Exception.class)
	public RpAccount unFreezeAmount(String userNo, BigDecimal amount, String requestNo, String trxType, String remark) {
		RpAccount account = this.getByUserNo_IsPessimist(userNo, true);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT;
		}

		Date lastModifyDate = account.getEditTime();
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(lastModifyDate)) {
			account.setTodayExpend(BigDecimal.ZERO);
			account.setTodayIncome(BigDecimal.ZERO);
			account.setTodayExpend(amount);
		}else{
			account.setTodayExpend(account.getTodayExpend().add(amount));
		}
		account.setTotalExpend(account.getTodayExpend().add(amount));

		// 判断解冻金额是否充足
		if (account.getUnbalance().subtract(amount).compareTo(BigDecimal.ZERO) == -1) {
			// 解冻金额超限
			throw AccountBizException.ACCOUNT_UN_FROZEN_AMOUNT_OUTLIMIT;
		}
		account.setEditTime(new Date());
		account.setBalance(account.getBalance().subtract(amount));// 减款
		account.setUnbalance(account.getUnbalance().subtract(amount));// 解冻
		account.setSettAmount(account.getSettAmount().subtract(amount));// 减少可结算金额

		String isAllowSett = PublicEnum.NO.name();
		String completeSett = PublicEnum.NO.name();
		// 记录账户历史
		RpAccountHistory accountHistoryEntity = new RpAccountHistory();
		accountHistoryEntity.setCreateTime(new Date());
		accountHistoryEntity.setEditTime(new Date());
		accountHistoryEntity.setIsAllowSett(isAllowSett);
		accountHistoryEntity.setAmount(amount);
		accountHistoryEntity.setBalance(account.getBalance());
		accountHistoryEntity.setRequestNo(requestNo);
		accountHistoryEntity.setIsCompleteSett(completeSett);
		accountHistoryEntity.setRemark(remark);
		accountHistoryEntity.setFundDirection(AccountFundDirectionEnum.SUB.name());
		accountHistoryEntity.setAccountNo(account.getAccountNo());
		accountHistoryEntity.setTrxType(trxType);
		accountHistoryEntity.setUserNo(userNo);
		this.rpAccountHistoryDao.insert(accountHistoryEntity);
		this.rpAccountDao.update(account);
		return account;
	}

	/**
	 * 结算失败 解冻金额
	 * 
	 * @param userNo
	 *            用户编号
	 * @param amount
	 *            解冻和减款金额
	 */

	@Transactional(rollbackFor = Exception.class)
	public RpAccount unFreezeSettAmount(String userNo, BigDecimal amount) {
		RpAccount account = this.getByUserNo_IsPessimist(userNo, true);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT;
		}

		Date lastModifyDate = account.getEditTime();
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(lastModifyDate)) {
			account.setTodayExpend(BigDecimal.ZERO);
			account.setTodayIncome(BigDecimal.ZERO);
		}

		// 判断解冻金额是否充足
		if (account.getUnbalance().subtract(amount).compareTo(BigDecimal.ZERO) == -1) {
			// 解冻金额超限
			throw AccountBizException.ACCOUNT_UN_FROZEN_AMOUNT_OUTLIMIT;
		}
		account.setEditTime(new Date());
		account.setUnbalance(account.getUnbalance().subtract(amount));// 解冻

		this.rpAccountDao.update(account);
		return account;
	}

	/**
	 * 更新账户历史中的结算状态，并且累加可结算金额
	 * 
	 * @param userNo
	 *            用户编号
	 * @param collectDate
	 *            汇总日期
	 * @param riskDay
	 *            风险预存期
	 * @param totalAmount
	 *            可结算金额累计
	 * 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void settCollectSuccess(String userNo, String collectDate, int riskDay, BigDecimal totalAmount) {

		LOG.info("==>settCollectSuccess");
		LOG.info(String.format("==>userNo:%s, collectDate:%s, riskDay:%s", userNo, collectDate, riskDay));

		RpAccount account = this.getByUserNo_IsPessimist(userNo, true);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT.newInstance("账户不存在,用户编号{%s}", userNo).print();
		}
		// 更新账户历史状态
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", account.getAccountNo());
		params.put("statDate", collectDate);
		params.put("riskDay", riskDay);
		rpAccountHistoryDao.updateCompleteSettTo100(params);

		// 账户可结算金额的累加
		account.setSettAmount(account.getSettAmount().add(totalAmount));
		rpAccountDao.update(account);
		LOG.info("==>settCollectSuccess<==");
	}
}