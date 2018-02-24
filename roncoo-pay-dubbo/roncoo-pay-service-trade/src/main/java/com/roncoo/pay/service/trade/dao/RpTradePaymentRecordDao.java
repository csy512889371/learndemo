/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.dao;

import com.roncoo.pay.common.core.dao.BaseDao;
import com.roncoo.pay.service.trade.entity.RpTradePaymentRecord;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/5/18  下午5:01
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public interface RpTradePaymentRecordDao extends BaseDao<RpTradePaymentRecord>{
	
	/** 获取支付流水号 **/
	String buildTrxNo();
	
	/** 获取银行订单号 **/
	String buildBankOrderNo();
	
	
    int deleteByPrimaryKey(String id);

    int insertSelective(RpTradePaymentRecord record);

    RpTradePaymentRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RpTradePaymentRecord record);

    /**
     * 根据银行订单号获取支付信息
     * @param bankOrderNo
     * @return
     */
    RpTradePaymentRecord getByBankOrderNo(String bankOrderNo);

    /**
     * 根据商户编号及商户订单号获取支付结果
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    RpTradePaymentRecord getByMerchantNoAndMerchantOrderNo(String merchantNo , String merchantOrderNo);

    /**
	 * 根据支付流水号查询支付记录
	 * 
	 * @param trxNo
	 * @return
	 */
	RpTradePaymentRecord getByTrxNo(String trxNo);

}
