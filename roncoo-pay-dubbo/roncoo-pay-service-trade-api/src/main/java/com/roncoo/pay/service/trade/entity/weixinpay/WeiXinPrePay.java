/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.entity.weixinpay;

import java.io.Serializable;

import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.service.trade.enums.weixinpay.WeiXinTradeTypeEnum;

/**
 * @功能说明:   微信统一下单请求实体
 * @创建者: Peter
 * @创建时间: 16/5/24  上午10:09
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class WeiXinPrePay implements Serializable {

    /** 公众账号ID 必填 **/
    private String appid;

    /** 商户号 必填 **/
    private String mchId;

    /** 设备号  终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" 默认为'WEB'**/
    private String deviceInfo = "WEB";

    /** 随机字符串 **/
    private String nonceStr = StringUtil.get32UUID();

    /** 签名 **/
    private String sign;

    /** 商品描述 **/
    private String body;

    /** 商品详情 **/
    private String detail;

    /** 附加数据 **/
    private String attach;

    /** 商户订单号 **/
    private String outTradeNo;

    /** 货币类型 默认为人民币 **/
    private String feeType = "CNY";

    /** 总金额 **/
    private Integer totalFee;

    /** 终端IP **/
    private String spbillCreateIp;

    /** 交易起始时间 **/
    private String timeStart;

    /** 交易结束时间 **/
    private String timeExpire;

    /** 商品标记 **/
    private String goodsTag;

    /** 通知地址 **/
    private String notifyUrl;

    /** 交易类型 **/
    private WeiXinTradeTypeEnum tradeType;

    /** 商品ID **/
    private String productId;

    /** 制定支付方式 **/
    private String limitPay;

    /** 用户标识 **/
    private String openid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public WeiXinTradeTypeEnum getTradeType() {
        return tradeType;
    }

    public void setTradeType(WeiXinTradeTypeEnum tradeType) {
        this.tradeType = tradeType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }
}
