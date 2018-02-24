/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.queue.bankmessage.biz;

import com.roncoo.pay.service.message.api.RpTransactionMessageService;
import com.roncoo.pay.service.trade.api.RpTradePaymentManagerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class BankMessageBiz {

    private static final Log LOG = LogFactory.getLog(BankMessageBiz.class);

    @Autowired
    private RpTradePaymentManagerService rpTradePaymentManagerService;

    @Autowired
    private RpTransactionMessageService rpTransactionMessageService;

    public void completePay(Map<String , String > notifyMessageMap){

        String messageId = notifyMessageMap.get("messageId");
        String payWayCode = notifyMessageMap.get("payWayCode");
        //调用业务方法,完成交易
        try{

            rpTradePaymentManagerService.completeScanPay(payWayCode, notifyMessageMap);

            //删除消息
            rpTransactionMessageService.deleteMessageByMessageId(messageId);
        }catch (Exception e){
            LOG.error("完成支付结果异常:",e);
        }

    }

    public RpTradePaymentManagerService getRpTradePaymentManagerService() {
        return rpTradePaymentManagerService;
    }

    public void setRpTradePaymentManagerService(RpTradePaymentManagerService rpTradePaymentManagerService) {
        this.rpTradePaymentManagerService = rpTradePaymentManagerService;
    }

    public RpTransactionMessageService getRpTransactionMessageService() {
        return rpTransactionMessageService;
    }

    public void setRpTransactionMessageService(RpTransactionMessageService rpTransactionMessageService) {
        this.rpTransactionMessageService = rpTransactionMessageService;
    }
}
