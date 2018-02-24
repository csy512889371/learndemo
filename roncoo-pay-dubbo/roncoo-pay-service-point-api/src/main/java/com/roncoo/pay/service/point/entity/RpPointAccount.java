/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.point.entity;

import java.io.Serializable;

import com.roncoo.pay.common.core.entity.BaseEntity;

/**
 * @功能说明: 账户信息
 * @创建者: zenghao 龙果学院(www.roncoo.com)
 */
public class RpPointAccount extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1996976092574646493L;
	
    /** 用户编号 **/
    private String userNo;
    
    /** 账户余额 **/
    private Integer balance;
    
    
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }



}