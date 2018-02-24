/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.biz.impl;


import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.service.account.api.RpAccountTransactionService;
import com.roncoo.pay.service.point.api.RpPointAccountService;
import com.roncoo.pay.service.trade.dao.RpTradePaymentOrderDao;
import com.roncoo.pay.service.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.service.trade.entity.RpTradePaymentOrder;
import com.roncoo.pay.service.trade.entity.RpTradePaymentRecord;
import com.roncoo.pay.service.trade.enums.TradeStatusEnum;
import com.roncoo.pay.service.user.enums.FundInfoTypeEnum;
import org.mengyun.tcctransaction.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Service
public class RpTradePaymentManagerBizImpl {

    private static final Logger LOG = LoggerFactory.getLogger(RpTradePaymentManagerBizImpl.class);

    @Autowired
    private RpTradePaymentOrderDao rpTradePaymentOrderDao;

    @Autowired
    private RpTradePaymentRecordDao rpTradePaymentRecordDao;

    @Autowired
    private RpAccountTransactionService rpAccountTransactionService;

    @Autowired
    private RpPointAccountService rpPointAccountService;

    /**
     * 支付成功方法
     * @param rpTradePaymentRecord
     */
    @Compensable(confirmMethod = "confirmCompleteSuccessOrder",cancelMethod = "cancelCompleteSuccessOrder")
    public void completeSuccessOrder(RpTradePaymentRecord rpTradePaymentRecord , String bankTrxNo ,Date timeEnd ,  String bankReturnMsg) throws Exception{

        LOG.info("------completeSuccessOrder[订单{}完成支付TRYING阶段开始时间{}]------",rpTradePaymentRecord.getBankOrderNo(), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));

        // 修改支付记录状态
        rpTradePaymentRecord.setPaySuccessTime(timeEnd);
        rpTradePaymentRecord.setBankTrxNo(bankTrxNo);
        rpTradePaymentRecord.setBankReturnMsg(bankReturnMsg);
        rpTradePaymentRecord.setStatus(TradeStatusEnum.WAITING_PAYMENT_RESULT.name());
        rpTradePaymentRecordDao.update(rpTradePaymentRecord);

        // 修改支付订单状态
        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getMerchantOrderNo());
        rpTradePaymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT_RESULT.name());
        rpTradePaymentOrderDao.update(rpTradePaymentOrder);

        // 给商户资金帐户加款（平台收款）
        if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(rpTradePaymentRecord.getFundIntoType())){
            LOG.info("==>修改订单账户金额");
            rpAccountTransactionService.creditToAccountTcc(null, rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getOrderAmount().subtract(rpTradePaymentRecord.getPlatIncome()), rpTradePaymentRecord.getBankOrderNo(),rpTradePaymentRecord.getBankTrxNo(), rpTradePaymentRecord.getTrxType(), rpTradePaymentRecord.getRemark());
        }

        // 增加消费积分（模拟TCC事务使用，非真实场景）
        rpPointAccountService.creditToPointAccountTcc(null,rpTradePaymentRecord.getMerchantNo(), 10, rpTradePaymentRecord.getBankOrderNo(),rpTradePaymentRecord.getBankTrxNo(), rpTradePaymentRecord.getTrxType(), rpTradePaymentRecord.getRemark());
        LOG.info("==>修改账户金额");

        LOG.info("------completeSuccessOrder[订单{}完成支付TRYING阶段结束时间{}]------",rpTradePaymentRecord.getBankOrderNo(), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
    }

    @Transactional
    public void confirmCompleteSuccessOrder(RpTradePaymentRecord rpTradePaymentRecord , String bankTrxNo ,Date timeEnd ,  String bankReturnMsg){

        LOG.info("------confirmCompleteSuccessOrder[订单{}完成支付CONFIRMING阶段开始时间{}]------",rpTradePaymentRecord.getBankOrderNo(), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
        // 修改支付记录状态
        rpTradePaymentRecord.setPaySuccessTime(timeEnd);
        rpTradePaymentRecord.setBankTrxNo(bankTrxNo);
        rpTradePaymentRecord.setBankReturnMsg(bankReturnMsg);
        rpTradePaymentRecord.setStatus(TradeStatusEnum.SUCCESS.name());
        rpTradePaymentRecordDao.update(rpTradePaymentRecord);

        // 修改支付订单状态
        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getMerchantOrderNo());
        rpTradePaymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());
        rpTradePaymentOrderDao.update(rpTradePaymentOrder);

        LOG.info("------confirmCompleteSuccessOrder[订单{}完成支付CONFIRMING阶段结束时间{}]------", rpTradePaymentRecord.getBankOrderNo(), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));

    }

    @Transactional
    public void cancelCompleteSuccessOrder(RpTradePaymentRecord rpTradePaymentRecord , String bankTrxNo ,Date timeEnd ,  String bankReturnMsg){

        //根据银行订单号获取支付信息
        RpTradePaymentRecord dataRpTradePaymentRecord = rpTradePaymentRecordDao.getByBankOrderNo(rpTradePaymentRecord.getBankOrderNo());//数据库数据
     // 幂等判断
        if(TradeStatusEnum.SUCCESS.name().equals(dataRpTradePaymentRecord.getStatus()) ||TradeStatusEnum.WAITING_PAYMENT.name().equals(dataRpTradePaymentRecord.getStatus()) ){
        	LOG.info("订单状态：{}，不能执行取消动作",dataRpTradePaymentRecord.getStatus());
        	return;
        }
    	
        LOG.info("------cancelCompleteSuccessOrder[订单{}完成支付CANCELING阶段开始时间{}]------",rpTradePaymentRecord.getBankOrderNo(), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
        // 修改支付记录状态
        rpTradePaymentRecord.setPaySuccessTime(timeEnd);
        rpTradePaymentRecord.setBankTrxNo(bankTrxNo);
        rpTradePaymentRecord.setBankReturnMsg(bankReturnMsg);
        rpTradePaymentRecord.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());
        rpTradePaymentRecordDao.update(rpTradePaymentRecord);

        // 修改支付订单状态
        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(rpTradePaymentRecord.getMerchantNo(), rpTradePaymentRecord.getMerchantOrderNo());
        rpTradePaymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());
        rpTradePaymentOrderDao.update(rpTradePaymentOrder);

        LOG.info("------cancelCompleteSuccessOrder[订单{}完成支付CANCELING阶段结束时间{}]------",rpTradePaymentRecord.getBankOrderNo(), DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
    }
}
