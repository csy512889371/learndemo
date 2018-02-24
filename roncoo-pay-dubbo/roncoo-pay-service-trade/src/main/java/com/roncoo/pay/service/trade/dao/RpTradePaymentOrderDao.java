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
import com.roncoo.pay.service.trade.entity.RpTradePaymentOrder;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/5/18  下午4:57
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public interface RpTradePaymentOrderDao  extends BaseDao<RpTradePaymentOrder>{

    /**
     * 根据商户编号及商户订单号获取支付订单信息
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    RpTradePaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo);

    int deleteByPrimaryKey(String id);

    int insertSelective(RpTradePaymentOrder record);

    RpTradePaymentOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RpTradePaymentOrder record);

    int updateByPrimaryKey(RpTradePaymentOrder record);
    
   

}
