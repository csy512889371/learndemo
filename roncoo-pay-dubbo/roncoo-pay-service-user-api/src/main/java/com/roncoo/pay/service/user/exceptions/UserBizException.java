/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.user.exceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.roncoo.pay.common.core.exception.BizException;

/**
 * @功能说明:   用户业务异常类
 * @创建者: Peter
 * @创建时间: 16/5/20  下午4:37
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class UserBizException extends BizException {

    /** 用户不存在 **/
    public static final int USER_IS_NULL = 101;

    /** 用户支付配置有误 **/
    public static final int USER_PAY_CONFIG_ERRPR = 102;
    
    public static final UserBizException USER_BANK_ACCOUNT_IS_NULL = new UserBizException(10010002, "用户未设置银行账户信息!");

    private static final Log LOG = LogFactory.getLog(UserBizException.class);

    public UserBizException() {
    }

    public UserBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public UserBizException(int code, String msg) {
        super(code, msg);
    }

    public UserBizException print() {
        LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }
}
