/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.entity;

import com.roncoo.pay.common.core.entity.BaseEntity;
import com.roncoo.pay.common.core.enums.PublicEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @功能说明: 支付订单表
 * @创建者: Peter
 * @创建时间: 16/5/18  下午4:14
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class RpTradePaymentOrder extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7350571920098973424L;

	/** 商品名称 **/
    private String productName;

    /** 商户订单编号 **/
    private String merchantOrderNo;

    /** 订单金额 **/
    private BigDecimal orderAmount;

    /** 订单来源 **/
    private String orderFrom;

    /** 商户名称 **/
    private String merchantName;

    /** 商户编号 **/
    private String merchantNo;

    /** 订单时间 **/
    private Date orderTime;

    /** 订单日期 **/
    private Date orderDate;

    /** 订单来源IP **/
    private String orderIp;

    /** 页面链接 **/
    private String orderRefererUrl;

    /** 页面回调通知地址 **/
    private String returnUrl;

    /** 后台异步通知地址 **/
    private String notifyUrl;

    /** 订单撤销原因 **/
    private String cancelReason;

    /** 订单有效期 **/
    private Integer orderPeriod;

    /** 订单到期时间 **/
    private Date expireTime;

    /** 支付通道编号 **/
    private String payWayCode;

    /** 支付方式名称 **/
    private String payWayName;

    /** 备注 **/
    private String remark;

    /** 交易业务类型 **/
    private String trxType;

    /** 支付方式类型编码 **/
    private String payTypeCode;

    /** 支付方式类型名称 **/
    private String payTypeName;

    /** 资金流入类型 **/
    private String fundIntoType;

    /** 是否退款 **/
    private String isRefund = PublicEnum.NO.name();

    /** 退款次数 **/
    private Short refundTimes;

    /** 成功退款金额 **/
    private BigDecimal successRefundAmount;

    /** 扩展字段1 **/
    private String field1;

    /** 扩展字段2 **/
    private String field2;

    /** 扩展字段3 **/
    private String field3;

    /** 扩展字段4 **/
    private String field4;

    /** 扩展字段5 **/
    private String field5;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom == null ? null : orderFrom.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderIp() {
        return orderIp;
    }

    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp == null ? null : orderIp.trim();
    }

    public String getOrderRefererUrl() {
        return orderRefererUrl;
    }

    public void setOrderRefererUrl(String orderRefererUrl) {
        this.orderRefererUrl = orderRefererUrl == null ? null : orderRefererUrl.trim();
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl == null ? null : returnUrl.trim();
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
    }

    public Integer getOrderPeriod() {
        return orderPeriod;
    }

    public void setOrderPeriod(Integer orderPeriod) {
        this.orderPeriod = orderPeriod;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode == null ? null : payWayCode.trim();
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName == null ? null : payWayName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType == null ? null : trxType.trim();
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode == null ? null : payTypeCode.trim();
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName == null ? null : payTypeName.trim();
    }

    public String getFundIntoType() {
        return fundIntoType;
    }

    public void setFundIntoType(String fundIntoType) {
        this.fundIntoType = fundIntoType == null ? null : fundIntoType.trim();
    }

    public String getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund == null ? null : isRefund.trim();
    }

    public Short getRefundTimes() {
        return refundTimes;
    }

    public void setRefundTimes(Short refundTimes) {
        this.refundTimes = refundTimes;
    }

    public BigDecimal getSuccessRefundAmount() {
        return successRefundAmount;
    }

    public void setSuccessRefundAmount(BigDecimal successRefundAmount) {
        this.successRefundAmount = successRefundAmount;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4 == null ? null : field4.trim();
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5 == null ? null : field5.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(super.getId());
        sb.append(", version=").append(super.getVersion());
        sb.append(", createTime=").append(super.getCreateTime());
        sb.append(", editor=").append(super.getEditor());
        sb.append(", creater=").append(super.getCreater());
        sb.append(", editTime=").append(super.getEditTime());
        sb.append(", status=").append(super.getStatus());
        sb.append(", productName=").append(productName);
        sb.append(", merchantOrderNo=").append(merchantOrderNo);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", orderFrom=").append(orderFrom);
        sb.append(", merchantName=").append(merchantName);
        sb.append(", merchantNo=").append(merchantNo);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", orderIp=").append(orderIp);
        sb.append(", orderRefererUrl=").append(orderRefererUrl);
        sb.append(", returnUrl=").append(returnUrl);
        sb.append(", notifyUrl=").append(notifyUrl);
        sb.append(", cancelReason=").append(cancelReason);
        sb.append(", orderPeriod=").append(orderPeriod);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", payWayCode=").append(payWayCode);
        sb.append(", payWayName=").append(payWayName);
        sb.append(", remark=").append(remark);
        sb.append(", trxType=").append(trxType);
        sb.append(", payTypeCode=").append(payTypeCode);
        sb.append(", payTypeName=").append(payTypeName);
        sb.append(", fundIntoType=").append(fundIntoType);
        sb.append(", isRefund=").append(isRefund);
        sb.append(", refundTimes=").append(refundTimes);
        sb.append(", successRefundAmount=").append(successRefundAmount);
        sb.append(", field1=").append(field1);
        sb.append(", field2=").append(field2);
        sb.append(", field3=").append(field3);
        sb.append(", field4=").append(field4);
        sb.append(", field5=").append(field5);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}