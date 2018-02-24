/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.util.StringUtils;
import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.service.trade.dao.RpTradePaymentOrderDao;
import com.roncoo.pay.service.trade.entity.RpTradePaymentOrder;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/5/19  上午9:56
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
@Repository("rpTradePaymentOrderDao")
public class RpTradePaymentOrderDaoImpl extends BaseDaoImpl<RpTradePaymentOrder> implements RpTradePaymentOrderDao{
	

    /**
     * 根据商户编号及商户订单号获取支付订单信息
     * @param merchantNo    商户编号
     * @param merchantOrderNo   商户订单号
     * @return
     */
    @Override
    public RpTradePaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",merchantNo);
        paramMap.put("merchantOrderNo",merchantOrderNo);
        return super.getBy(paramMap);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(RpTradePaymentOrder record) {
        return 0;
    }

    @Override
    public RpTradePaymentOrder selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(RpTradePaymentOrder record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(RpTradePaymentOrder record) {
        return 0;
    }
}
