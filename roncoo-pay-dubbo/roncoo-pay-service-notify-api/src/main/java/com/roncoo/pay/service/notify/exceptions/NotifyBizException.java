/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.notify.exceptions;

import com.roncoo.pay.common.core.exception.BizException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class NotifyBizException extends BizException {

    /**
     *
     */
    private static final long serialVersionUID = 3536909333010163563L;

    /** 保存的消息为空 **/
    public static final int NOTIFY_SYSTEM_EXCEPTION = 9001;

    private static final Log LOG = LogFactory.getLog(NotifyBizException.class);

    public NotifyBizException() {
    }

    public NotifyBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public NotifyBizException(int code, String msg) {
        super(code, msg);
    }

    public NotifyBizException print() {
        LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }
}
