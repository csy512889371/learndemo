/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.accounting.entity;

import java.io.Serializable;
import java.util.Date;

import com.roncoo.pay.common.core.entity.BaseEntity;

/**
 * 会计原始凭证.
 * @author WuShuicheng.
 *
 */
public class RpAccountingVoucher extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7332733320863891479L;

	/** 会计分录类型（对应枚举EntryTypeEnum.java） **/
	private Integer entryType;
	
	/** 请求号 (会计系统自动生成) **/
	private String requestNo;
	
	/** 来源系统 **/
	private Integer fromSystem;
	
	/** 原始凭证号 （交易记录的唯一凭证号） **/
	private String voucherNo;
	
	/** 会计日期 **/
	private Date accountingDate;
	
	/** 平台银行帐户变动金额 **/
	private Double bankChangeAmount = 0D;
	
	/**
	 * 付款方账户编号(退款交易：原路退，既交易付款方为payerAccountNo，那么退款也用payerAccountNo作为收钱一方)
	 */
	private String payerAccountNo;
	
	/**
	 * 收款方账户编号(退款交易：原路退，既交易付款方为receiverAccountNo，那么退款也用receiverAccountNo作为退钱一方)
	 */
	private String receiverAccountNo;
	
	/** 银行账户 **/
	private String bankAccount;
	
	/** 银行渠道编号 **/
	private String bankChannelCode;
	
	/**
	 * 付款方帐户变动金额（退款交易：付款方实际变动金额）
	 */
	private Double payerChangeAmount = 0D;
	
	/**
	 * 收款方帐户变动金额（退款交易：收款方实际变动金额。）
	 */
	private Double receiverChangeAmount = 0D;
	
	/**
	 * 平台利润（退款交易：平台利润实际变动金额。）
	 */
	private Double profit = 0D;
	
	/**
	 * 平台收入（退款交易：平台收入实际变动金额）
	 */
	private Double income = 0D;
	
	/**
	 * 平台成本（退款交易：平台成本实际变动金额。）
	 */
	private Double cost = 0D;
	
	/** 银行订单号 **/
	private String bankOrderNo;
	
	/** 付款方账户类型 **/
	private Integer payerAccountType;
	
	/** 支付金额 **/
	private Double payAmount = 0D;
	
	/** 收款方账户类型 **/
	private Integer receiverAccountType;

	/**
	 * 收款方手续费(退款交易：原路退，实际退收款方手续费。既:交易收收款方手续费
	 * 10元，如分2次退款每次退5元，则receiverFee为5元，如一次退完，则receiverFee为10元)
	 */
	private Double receiverFee = 0D;
	/**
	 * 付款方手续费（退款交易：原路退，实际退付款方手续费。既:交易收付款方手续费
	 * 10元，如分2次退款每次退5元，则payerFee为5元，如一次退完，则payerFee为10元）
	 */
	private Double payerFee = 0D;
	
	/** 分录步骤，1：产生交易 2：清算对账 */
	private Integer step; // 非数据库映射字段，只用于传参
	
	private String messageId; // 非数据库映射字段，只用于传参

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Integer getEntryType() {
		return entryType;
	}

	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Integer getFromSystem() {
		return fromSystem;
	}

	public void setFromSystem(Integer fromSystem) {
		this.fromSystem = fromSystem;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public Date getAccountingDate() {
		return accountingDate;
	}

	public void setAccountingDate(Date accountingDate) {
		this.accountingDate = accountingDate;
	}

	public Double getBankChangeAmount() {
		return bankChangeAmount;
	}

	public void setBankChangeAmount(Double bankChangeAmount) {
		this.bankChangeAmount = bankChangeAmount;
	}

	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}

	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankChannelCode() {
		return bankChannelCode;
	}

	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}

	public Double getPayerChangeAmount() {
		return payerChangeAmount;
	}

	public void setPayerChangeAmount(Double payerChangeAmount) {
		this.payerChangeAmount = payerChangeAmount;
	}

	public Double getReceiverChangeAmount() {
		return receiverChangeAmount;
	}

	public void setReceiverChangeAmount(Double receiverChangeAmount) {
		this.receiverChangeAmount = receiverChangeAmount;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}


	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	public Integer getPayerAccountType() {
		return payerAccountType;
	}

	public void setPayerAccountType(Integer payerAccountType) {
		this.payerAccountType = payerAccountType;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getReceiverAccountType() {
		return receiverAccountType;
	}

	public void setReceiverAccountType(Integer receiverAccountType) {
		this.receiverAccountType = receiverAccountType;
	}

	public Double getReceiverFee() {
		return receiverFee;
	}

	public void setReceiverFee(Double receiverFee) {
		this.receiverFee = receiverFee;
	}

	public Double getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Double payerFee) {
		this.payerFee = payerFee;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public RpAccountingVoucher() {

	}
}
