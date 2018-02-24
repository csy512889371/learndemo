/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.user.exceptions;

import com.roncoo.pay.common.core.exception.BizException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @功能说明:   支付业务异常类
 * @创建者: zh
 * @创建时间: 16/5/20  下午4:37
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class PayBizException extends BizException {

    /** 支付方式已存在 **/
    public static final int PAY_TYPE_IS_EXIST = 101;
    
    /** 支付产品已存在 **/
    public static final int PAY_PRODUCT_IS_EXIST = 102;
    
    /** 支付产品已关联数据 **/
    public static final int PAY_PRODUCT_HAS_DATA = 103;
    
    /** 用户支付配置已存在 **/
    public static final int USER_PAY_CONFIG_IS_EXIST = 104;
    
    /** 用户支付配置不存在 **/
    public static final int USER_PAY_CONFIG_IS_NOT_EXIST = 105;
    
    /** 用户支付配置已生效 **/
    public static final int USER_PAY_CONFIG_IS_EFFECTIVE = 106;
    
    /** 支付产品已生效 **/
    public static final int PAY_PRODUCT_IS_EFFECTIVE = 107;
    
    /** 支付产品不存在 **/
    public static final int PAY_PRODUCT_IS_NOT_EXIST = 108;
    
    /** 支付方式不存在 **/
    public static final int PAY_TYPE_IS_NOT_EXIST = 108;

    private static final Log LOG = LogFactory.getLog(PayBizException.class);

    public PayBizException() {
    }

    public PayBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public PayBizException(int code, String msg) {
        super(code, msg);
    }

    public PayBizException print() {
        LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }
}
