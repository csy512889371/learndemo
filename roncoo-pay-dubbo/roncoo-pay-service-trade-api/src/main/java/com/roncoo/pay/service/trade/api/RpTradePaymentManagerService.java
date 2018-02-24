/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.roncoo.pay.service.trade.exceptions.TradeBizException;
import com.roncoo.pay.service.trade.vo.OrderPayResultVo;
import com.roncoo.pay.service.trade.vo.RpPayGateWayPageShowVo;
import com.roncoo.pay.service.trade.vo.ScanPayResultVo;

/**
 * @功能说明:   RoncooPay订单管理服务接口,所有与接口相关,需要做数据修改,事务管理的类,由该接口管理
 * @创建者: Peter
 * @创建时间: 16/5/20  上午11:33
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public interface RpTradePaymentManagerService {

    /**
     * 初始化直连扫码支付数据,直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单不存在,创建支付订单
     * 2:创建支付记录
     * 3:根据相应渠道方法
     * 4:调转到相应支付渠道扫码界面
     * @param payKey    商户支付Key
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice    订单金额(元)
     * @param payWayCode    支付方式
     * @param orderIp   下单IP
     * @param orderPeriod   订单有效期(分钟)
     * @param returnUrl 支付结果页面通知地址
     * @param notifyUrl 支付结果后台通知地址
     * @param remark    支付备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     */
    public ScanPayResultVo initDirectScanPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, Integer orderPeriod, String returnUrl
            , String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) throws TradeBizException;


    /**
     * 完成扫码支付(支付宝即时到账支付，微信扫码支付)
     * @param payWayCode
     * @param notifyMap
     * @return
     */
    public void completeScanPay(String payWayCode ,Map<String, String> notifyMap) throws TradeBizException;

    /**
     *
     * @param payWayCode
     * @param resultMap
     * @return
     */
    public OrderPayResultVo completeScanPayByResult(String payWayCode , Map<String , String> resultMap) throws TradeBizException;
    
    
    /**
     * 模拟测试接口.
     * @param payKey
     * @param productName
     * @param orderNo
     * @param orderDate
     * @param orderTime
     * @param orderPrice
     * @param payWayCode
     * @param orderIp
     * @param orderPeriod
     * @param returnUrl
     * @param notifyUrl
     * @param remark
     * @param field1
     * @param field2
     * @param field3
     * @param field4
     * @param field5
     * @return
     */
    public OrderPayResultVo completeTestPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, Integer orderPeriod, String returnUrl
            , String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) throws TradeBizException;

    /**
     * 初始化非直连扫码支付数据,非直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单不存在,创建支付订单
     * 2:获取商户支付配置,跳转到支付网关,选择支付方式
     * @param payKey  商户支付KEY
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice    订单金额(元)
     * @param orderIp   下单IP
     * @param orderPeriod   订单有效期(分钟)
     * @param returnUrl 支付结果页面通知地址
     * @param notifyUrl 支付结果后台通知地址
     * @param remark    支付备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     */
    public RpPayGateWayPageShowVo initNonDirectScanPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String orderIp, Integer orderPeriod, String returnUrl
            , String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) throws TradeBizException;


    /**
     * 非直连扫码支付,选择支付方式后,去支付
     * @param payKey
     * @param orderNo
     * @param payWayCode
     * @return
     */
    public ScanPayResultVo toNonDirectScanPay(String payKey , String orderNo , String payWayCode) throws TradeBizException;
    
    /**
     * 回调通知验证签名
     * @param payWayCode
     * @param notifyMap
     * @return
     */
    public void verifyNotify(String payWayCode ,Map<String, String> notifyMap) throws TradeBizException;


    public String getMerchantNotifyMessage(String payWayCode, Map<String, String> resultMap) throws TradeBizException;
}
