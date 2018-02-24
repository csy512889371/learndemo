/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.vo;

import java.io.Serializable;

/**
 * @功能说明:   支付订单查询条件实体
 * @创建者: Peter
 * @创建时间: 16/6/12  下午4:53
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class PaymentOrderQueryVo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4657916227076727051L;

	/** 商户编号 **/
    private String merchantNo;

    /** 商户名称 **/
    private String merchantName;

    /** 商户订单号 **/
    private String merchantOrderNo;

    /** 订单日期开始时间 **/
    private String orderDateBegin;

    /** 订单日期结束时间 **/
    private String orderDateEnd;

    /** 支付方式 **/
    private String payWayName;

    /** 支付类型 **/
    private String payTypeName;

    /** 资金流入类型 **/
    private String fundIntoType;

    /** 状态 **/
    private String status;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getFundIntoType() {
        return fundIntoType;
    }

    public void setFundIntoType(String fundIntoType) {
        this.fundIntoType = fundIntoType;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDateBegin() {
        return orderDateBegin;
    }

    public void setOrderDateBegin(String orderDateBegin) {
        this.orderDateBegin = orderDateBegin;
    }

    public String getOrderDateEnd() {
        return orderDateEnd;
    }

    public void setOrderDateEnd(String orderDateEnd) {
        this.orderDateEnd = orderDateEnd;
    }
}
