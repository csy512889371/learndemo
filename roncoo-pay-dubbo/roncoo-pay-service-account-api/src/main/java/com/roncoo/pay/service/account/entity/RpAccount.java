/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.roncoo.pay.common.core.entity.BaseEntity;

/**
 * @功能说明:   账户信息
 * @创建者: zenghao 龙果学院(www.roncoo.com)
 */
public class RpAccount extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1996976092574646493L;

	/** 账户编号 **/
    private String accountNo;
    
    /** 账户余额 **/
    private BigDecimal balance;

    /** 不可用余额 **/
    private BigDecimal unbalance;

    /** 保证金 **/
    private BigDecimal securityMoney;

    /** 总收益 **/
    private BigDecimal totalIncome;

    /** 总支出 **/
    private BigDecimal totalExpend;

    /** 今日收益  **/
    private BigDecimal todayIncome;

    /** 今日支出 **/
    private BigDecimal todayExpend;

    /** 账户类型 **/
    private String accountType;

    /** 可结算金额 **/
    private BigDecimal settAmount;

    /** 用户编号 **/
    private String userNo;
    
    /************************* just show ************************************/
	private String userName;
	/************************* just show ************************************/
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取可用余额
	 * 
	 * @return
	 */
	public BigDecimal getAvailableBalance() {
		return this.balance.subtract(unbalance);
	}

	/**
	 * 获取实际可结算金额
	 * 
	 * @return
	 */
	public BigDecimal getAvailableSettAmount() {
		BigDecimal subSettAmount = this.settAmount.subtract(unbalance);
		if (getAvailableBalance().compareTo(subSettAmount) == -1) {
			return getAvailableBalance();
		}
		return subSettAmount;
	}

	/**
	 * 验证可用余额是否足够
	 * 
	 * @param amount
	 * @return
	 */
	public boolean availableBalanceIsEnough(BigDecimal amount) {

		return this.getAvailableBalance().compareTo(amount) >= 0;
	}


    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getUnbalance() {
        return unbalance;
    }

    public void setUnbalance(BigDecimal unbalance) {
        this.unbalance = unbalance;
    }

    public BigDecimal getSecurityMoney() {
        return securityMoney;
    }

    public void setSecurityMoney(BigDecimal securityMoney) {
        this.securityMoney = securityMoney;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpend() {
        return totalExpend;
    }

    public void setTotalExpend(BigDecimal totalExpend) {
        this.totalExpend = totalExpend;
    }

    public BigDecimal getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(BigDecimal todayIncome) {
        this.todayIncome = todayIncome;
    }

    public BigDecimal getTodayExpend() {
        return todayExpend;
    }

    public void setTodayExpend(BigDecimal todayExpend) {
        this.todayExpend = todayExpend;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public BigDecimal getSettAmount() {
        return settAmount;
    }

    public void setSettAmount(BigDecimal settAmount) {
        this.settAmount = settAmount;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

}