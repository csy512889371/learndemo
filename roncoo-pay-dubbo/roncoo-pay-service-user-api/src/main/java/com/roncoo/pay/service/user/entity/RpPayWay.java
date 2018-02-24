/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.user.entity;

import java.io.Serializable;

import com.roncoo.pay.common.core.entity.BaseEntity;

/**
 * @类功能说明： 支付方式实体类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
public class RpPayWay extends BaseEntity implements Serializable {


    private String payWayCode;

    private String payWayName;

    private String payTypeCode;

    private String payTypeName;

    private String payProductCode;

    private Integer sorts;

    private Double payRate;

    private static final long serialVersionUID = 1L;


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

    public String getPayProductCode() {
        return payProductCode;
    }

    public void setPayProductCode(String payProductCode) {
        this.payProductCode = payProductCode == null ? null : payProductCode.trim();
    }


    public Integer getSorts() {
        return sorts;
    }

    public void setSorts(Integer sorts) {
        this.sorts = sorts;
    }

    public Double getPayRate() {
        return payRate;
    }

    public void setPayRate(Double payRate) {
        this.payRate = payRate;
    }

}