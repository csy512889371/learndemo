package com.roncoo.pay.service.trade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.enums.NotifyDestinationNameEnum;
import com.roncoo.pay.common.core.enums.PayTypeEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.enums.TrxTypeEnum;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.service.message.api.RpTransactionMessageService;
import com.roncoo.pay.service.message.entity.RpTransactionMessage;
import com.roncoo.pay.service.trade.dao.RpTradePaymentOrderDao;
import com.roncoo.pay.service.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.service.trade.entity.RpTradePaymentOrder;
import com.roncoo.pay.service.trade.entity.RpTradePaymentRecord;
import com.roncoo.pay.service.trade.enums.OrderFromEnum;
import com.roncoo.pay.service.trade.enums.TradeStatusEnum;

/**
 * 
 * @描述: 启动Dubbo服务用的MainClass.
 * @作者: WuShuicheng .
 * @创建时间: 2016-06-22,下午9:47:55 .
 * @版本: 1.0 .
 */
public class DubboProvider {
	private static final Log log = LogFactory.getLog(DubboProvider.class);

	public static void main(String[] args) {

		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
//			final RpTransactionMessageService rpTransactionMessageService = (RpTransactionMessageService) context.getBean("rpTransactionMessageService");
//			final RpTradePaymentOrderDao rpTradePaymentOrderDao = (RpTradePaymentOrderDao) context.getBean("rpTradePaymentOrderDao");
//			final RpTradePaymentRecordDao rpTradePaymentRecordDao = (RpTradePaymentRecordDao) context.getBean("rpTradePaymentRecordDao");
//			for (int i = 0 ; i < 10 ; i++){//线程数
//				new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						try {
//							creatTrade(rpTransactionMessageService, rpTradePaymentOrderDao, rpTradePaymentRecordDao);
//						} catch (ParseException e) {
//							System.err.println(e);
//						}
//					}
//				}).start();
//				
//
//			}
//			log.info("========>创建完成");

		} catch (Exception e) {
			log.error("== DubboProvider context start error:", e);
		}

		synchronized (DubboProvider.class) {
			while (true) {
				try {
					DubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:", e);
				}
			}
		}
	}

//	static class IntClass{
//		Integer i;
//
//		public Integer getI() {
//			return i;
//		}
//
//		public void setI(Integer i) {
//			this.i = i;
//		}
//	}
//
//	private static void creatTrade( RpTransactionMessageService rpTransactionMessageService ,  RpTradePaymentOrderDao rpTradePaymentOrderDao  ,RpTradePaymentRecordDao rpTradePaymentRecordDao) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//		long threadID = Thread.currentThread().getId();
//		for (int i = 0; i < 0; i++) {//每条线程生成数
//			// step1: 创建支付订单和支付记录
//			RpTradePaymentOrder rpTradePaymentOrder = new RpTradePaymentOrder();
//			rpTradePaymentOrder.setProductName("测试商品：" + (i + 1));// 商品名称
//			rpTradePaymentOrder.setMerchantOrderNo("pt"+ threadID +sdf.format(new Date()) + 000 + i);// 订单号
//			rpTradePaymentOrder.setOrderAmount(new BigDecimal(10));// 订单金额
//			rpTradePaymentOrder.setMerchantName("bankmessage");// 商户名称
//			rpTradePaymentOrder.setMerchantNo("88882016071900000003");// 商户编号
//			rpTradePaymentOrder.setOrderDate(sdf1.parse("2016-07-11"));// 下单日期
//			rpTradePaymentOrder.setOrderTime(new Date());// 下单时间
//			rpTradePaymentOrder.setOrderIp("127.0.0.1");// 下单IP
//			rpTradePaymentOrder.setOrderRefererUrl("");// 下单前页面
//			rpTradePaymentOrder.setReturnUrl("http://www.roncoo.com");// 页面通知地址
//			rpTradePaymentOrder.setNotifyUrl("http://www.roncoo.com");// 后台通知地址
//			rpTradePaymentOrder.setOrderPeriod(5);// 订单有效期
//			Date expireTime = DateUtils.addMinute(new Date(), 5);// 订单过期时间
//			rpTradePaymentOrder.setExpireTime(expireTime);// 订单过期时间
//			rpTradePaymentOrder.setPayWayCode(PayWayEnum.WEIXIN.name());// 支付通道编码
//			rpTradePaymentOrder.setPayWayName(PayWayEnum.WEIXIN.getDesc());// 支付通道名称
//			rpTradePaymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态
//			rpTradePaymentOrder.setPayTypeCode(PayTypeEnum.SCANPAY.name());// 支付类型
//			rpTradePaymentOrder.setPayTypeName(PayTypeEnum.SCANPAY.getDesc());// 支付方式
//			rpTradePaymentOrder.setFundIntoType("PLAT_RECEIVES");// 资金流入方向
//			rpTradePaymentOrderDao.insert(rpTradePaymentOrder);
//
//			// step2:创建支付记录
//			RpTradePaymentRecord rpTradePaymentRecord = new RpTradePaymentRecord();
//			rpTradePaymentRecord.setProductName(rpTradePaymentOrder.getProductName());// 产品名称
//			rpTradePaymentRecord.setMerchantOrderNo(rpTradePaymentOrder.getMerchantOrderNo());// 产品编号
//			String trxNo = rpTradePaymentRecordDao.buildTrxNo();
//			rpTradePaymentRecord.setTrxNo(trxNo);// 支付流水号
//			String bankOrderNo = rpTradePaymentRecordDao.buildBankOrderNo();
//			rpTradePaymentRecord.setBankOrderNo(bankOrderNo);// 银行订单号
//			rpTradePaymentRecord.setMerchantName(rpTradePaymentOrder.getMerchantName());
//			rpTradePaymentRecord.setMerchantNo(rpTradePaymentOrder.getMerchantNo());// 商户编号
//			rpTradePaymentRecord.setOrderIp("127.0.0.1");// 下单IP
//			rpTradePaymentRecord.setOrderRefererUrl("");// 下单前页面
//			rpTradePaymentRecord.setReturnUrl("http://www.roncoo.com");// 页面通知地址
//			rpTradePaymentRecord.setNotifyUrl("http://www.roncoo.com");// 后台通知地址
//			rpTradePaymentRecord.setPayWayCode(rpTradePaymentOrder.getPayWayCode());// 支付通道编码
//			rpTradePaymentRecord.setPayWayName(rpTradePaymentOrder.getPayWayName());// 支付通道名称
//			rpTradePaymentRecord.setTrxType(TrxTypeEnum.EXPENSE.name());// 交易类型
//			rpTradePaymentRecord.setOrderFrom(OrderFromEnum.USER_EXPENSE.name());// 订单来源
//			rpTradePaymentRecord.setOrderAmount(rpTradePaymentOrder.getOrderAmount());// 订单金额
//			rpTradePaymentRecord.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态
//			rpTradePaymentRecord.setPayTypeCode(PayTypeEnum.SCANPAY.name());// 支付类型
//			rpTradePaymentRecord.setPayTypeName(PayTypeEnum.SCANPAY.getDesc());// 支付方式
//			rpTradePaymentRecord.setFundIntoType("PLAT_RECEIVES");// 资金流入方向
//
//			rpTradePaymentRecord.setFeeRate(BigDecimal.valueOf(0.8));// 费率
//			rpTradePaymentRecord.setPlatCost(BigDecimal.valueOf(0.06));// 平台成本
//			rpTradePaymentRecord.setPlatIncome(BigDecimal.valueOf(0.08));// 平台收入
//			rpTradePaymentRecord.setPlatProfit(BigDecimal.valueOf(0.02));// 平台利润
//			rpTradePaymentRecordDao.insert(rpTradePaymentRecord);
//
//			// 发送通知
//			String messageId = StringUtil.get32UUID();
//			Map<String, String> notifyMap = new HashMap<String, String>();
//			notifyMap.put("payWayCode", PayWayEnum.WEIXIN.name());
//			notifyMap.put("messageId", messageId);
//			notifyMap.put("result_code", "SUCCESS");
//			notifyMap.put("time_end", "20160711143625");
//			notifyMap.put("out_trade_no", bankOrderNo);
//			notifyMap.put("transaction_id", StringUtil.get32UUID());
//
//			String messageBody = JSONObject.toJSONString(notifyMap);
//			RpTransactionMessage rpTransactionMessage = new RpTransactionMessage(messageId, messageBody, NotifyDestinationNameEnum.BANK_NOTIFY.name());
//			rpTransactionMessageService.saveAndSendMessage(rpTransactionMessage);
//
//		}
//	}
}