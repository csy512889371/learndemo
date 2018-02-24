/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.accounting.dao.impl;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.service.accounting.dao.RpAccountingVoucherDao;
import com.roncoo.pay.service.accounting.entity.RpAccountingVoucher;

/**
 * 会计原始凭证服务表数据访问层接口实现.
 * @author WuShuicheng.
 *
 */
@Repository(value = "rpAccountingVoucherDao")
public class RpAccountingVoucherDaoImpl extends BaseDaoImpl<RpAccountingVoucher> implements RpAccountingVoucherDao {

	/**
	 * 创建会计分录原始凭证信息.
	 * @param entryType 会计分录类型.
	 * @param voucherNo 原始凭证号 （交易记录的唯一凭证号）.
	 * @param payerAccountNo 付款方账户编号.
	 * @param receiverAccountNo 收款方账户编号.
	 * @param payerChangeAmount 付款方帐户变动金额.
	 * @param receiverChangeAmount 收款方帐户变动金额.
	 * @param income 平台收入.
	 * @param cost 平台成本.
	 * @param profit 平台利润.
	 * @param bankChangeAmount 平台银行帐户变动金额.
	 * @param requestNo 请求号 (会计系统自动生成).
	 * @param bankChannelCode 银行渠道编号.
	 * @param bankAccount 银行账户.
	 * @param fromSystem 来源系统.
	 * @param remark 备注.
	 * @param bankOrderNo 银行订单号.
	 * @param payerAccountType 付款方账户类型.
	 * @param payAmount 支付金额.
	 * @param receiverAccountType 收款方账户类型.
	 * @param payerFee 付款方手续费.
	 * @param receiverFee 收款方手续费.
	 */
	public void createAccountingVoucher(int entryType, String voucherNo, String payerAccountNo, String receiverAccountNo,
			double payerChangeAmount, double receiverChangeAmount, double income, double cost, double profit, double bankChangeAmount,
			String requestNo, String bankChannelCode, String bankAccount, int fromSystem, String remark, String bankOrderNo,
			int payerAccountType, double payAmount, int receiverAccountType, double payerFee, double receiverFee) {
		
		RpAccountingVoucher voucher = new RpAccountingVoucher();

		voucher.setAccountingDate(new Date());
		voucher.setBankChangeAmount(bankChangeAmount);
		voucher.setBankChannelCode(bankChannelCode);
		voucher.setBankAccount(bankAccount);
		voucher.setCost(cost);
		voucher.setRemark(remark);
		voucher.setFromSystem(fromSystem);
		voucher.setIncome(income);
		voucher.setEditTime(new Date());
		voucher.setProfit(profit);
		voucher.setRequestNo(requestNo);
		voucher.setReceiverChangeAmount(receiverChangeAmount);
		voucher.setVoucherNo(voucherNo);
		voucher.setReceiverAccountNo(receiverAccountNo);
		voucher.setPayerChangeAmount(payerChangeAmount);
		voucher.setPayerAccountNo(payerAccountNo);
		voucher.setEntryType(entryType);
		voucher.setBankOrderNo(bankOrderNo);
		voucher.setPayerAccountType(payerAccountType);
		voucher.setPayAmount(payAmount);
		voucher.setReceiverAccountType(receiverAccountType);
		voucher.setPayerFee(payerFee);
		voucher.setReceiverFee(receiverFee);
		super.insert(voucher);

		logger.info("===>创建会计分录原始凭证，凭证号:" + voucherNo);
	}

	/**
	 * 根据请求号查找会计请求表 .
	 * 
	 * @param requestNo
	 *            .
	 * @return AccountingRequestNote .
	 */
	public RpAccountingVoucher findByRequestNo(String requestNo) {
		return super.getSqlSession().selectOne(getStatement("getByRequestNo"), requestNo);
	}

	/***
	 * 根据条件查询分录请求表数据
	 * 
	 * @param searchMap
	 * @return
	 */
	public Map getMapBy(Map<String, Object> searchMap) {
		return (Map) super.getSessionTemplate().selectList(getStatement("listMapBy"), searchMap);
	}

	/**
	 * 生成请求号: 会计分录类型+日期+随机数字.
	 * @param entryType 会计分录类型
	 * @return requestNo.
	 */
	public String buildAccountingVoucherNo(int entryType) {
		return entryType + DateUtils.toString(new Date(), "yyyyMMdd") + super.getSqlSession().selectOne(getStatement("buildAccountingVoucherNo"));
	}

	/***
	 * 查询会计分录请求表中的分录数据
	 * 
	 * @param noteMap
	 * @return
	 */
	public List<RpAccountingVoucher> getListBy(Map<String, Object> noteMap) {
		return super.listBy(noteMap);
	}

	/**
	 * @param requestNo
	 *            请求号
	 * @param bankOrderNo
	 *            银行订单号
	 * @param voucherNo
	 *            交易流水号
	 * @return
	 */
	public RpAccountingVoucher getAccountingRequestNote(String requestNo, String bankOrderNo, String voucherNo) {
		Map<String, Object> paramMap = new Hashtable<String, Object>();
		paramMap.put("requestNo", requestNo);
		paramMap.put("bankOrderNo", bankOrderNo);
		paramMap.put("voucherNo", voucherNo);
		return super.getBy(paramMap);
	}
	
	/**
	 * @param entryType
	 *            会计分录类型
	 * @param voucherNo
	 *            交易流水号
	 * @param fromSystem
	 *            来源系统
	 * @return
	 */
	public RpAccountingVoucher getDataByVoucherNoFromSystem(int entryType, String voucherNo, int fromSystem) {
		Map<String, Object> paramMap = new Hashtable<String, Object>();
		paramMap.put("entryType", entryType);
		paramMap.put("voucherNo", voucherNo);
		paramMap.put("fromSystem", fromSystem);
		return super.getBy(paramMap);
	}
}