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
import java.math.BigDecimal;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/5/25  下午3:43
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class ScanPayResultVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1324566713391515644L;

	/**
     * 支付方式编码
     */
    private String payWayCode;

    /** 金额 **/
    private BigDecimal orderAmount;

    /** 产品名称 **/
    private String productName;

    /**
     * 二维码地址
     */
    private String codeUrl;

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
