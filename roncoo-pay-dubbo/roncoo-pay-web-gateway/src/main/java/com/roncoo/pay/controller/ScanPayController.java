/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.common.core.utils.WeixinConfigUtil;
import com.roncoo.pay.controller.common.BaseController;
import com.roncoo.pay.service.trade.api.RpTradePaymentManagerService;
import com.roncoo.pay.service.trade.api.RpTradePaymentQueryService;
import com.roncoo.pay.service.trade.exceptions.TradeBizException;
import com.roncoo.pay.service.trade.utils.MerchantApiUtil;
import com.roncoo.pay.service.trade.vo.OrderPayResultVo;
import com.roncoo.pay.service.trade.vo.RpPayGateWayPageShowVo;
import com.roncoo.pay.service.trade.vo.ScanPayResultVo;
import com.roncoo.pay.service.user.api.RpPayWayService;
import com.roncoo.pay.service.user.api.RpUserPayConfigService;
import com.roncoo.pay.service.user.entity.RpUserPayConfig;
import com.roncoo.pay.service.user.exceptions.UserBizException;
import com.roncoo.pay.utils.JsonUtils;

/**
 * @功能说明:   扫码支付
 * @创建者: Peter
 * @创建时间: 16/5/19  下午3:06
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
@Controller
@RequestMapping(value = "/scanPay")
public class ScanPayController extends BaseController {

	private static final Log LOG = LogFactory.getLog(ScanPayController.class);

    @Autowired
    private RpTradePaymentManagerService rpTradePaymentManagerService;

    @Autowired
    private RpTradePaymentQueryService rpTradePaymentQueryService;

    @Autowired
    private RpUserPayConfigService rpUserPayConfigService;

    @Autowired
    private RpPayWayService rpPayWayService;

    /**
     * 扫码支付,预支付页面
     * 用户进行扫码支付时,商户后台调用该接口
     * 支付平台根据商户传入的参数是否包含支付通道,决定需要跳转的页面
     * 1:传入支付通道参数,跳转到相应的支付通道扫码页面
     * 2:未传入支付通道参数,跳转到
     * @return
     */
    @RequestMapping("/initPay")
    public String initPay(Model model){
        Map<String , Object> paramMap = new HashMap<String , Object>();

        //获取商户传入参数
        String payKey = getString_UrlDecode_UTF8("payKey"); // 企业支付KEY
        paramMap.put("payKey",payKey);
        String productName = getString_UrlDecode_UTF8("productName"); // 商品名称
        paramMap.put("productName",productName);
        String orderNo = getString_UrlDecode_UTF8("orderNo"); // 订单编号
        paramMap.put("orderNo",orderNo);
        String orderPriceStr = getString_UrlDecode_UTF8("orderPrice"); // 订单金额 , 单位:元
        paramMap.put("orderPrice",orderPriceStr);
        String payWayCode = getString_UrlDecode_UTF8("payWayCode"); // 支付方式编码 支付宝: ALIPAY  微信:WEIXIN
        paramMap.put("payWayCode",payWayCode);
        String orderIp = getString_UrlDecode_UTF8("orderIp"); // 下单IP
        paramMap.put("orderIp",orderIp);
        String orderDateStr = getString_UrlDecode_UTF8("orderDate"); // 订单日期
        paramMap.put("orderDate",orderDateStr);
        String orderTimeStr = getString_UrlDecode_UTF8("orderTime"); // 订单日期
        paramMap.put("orderTime",orderTimeStr);
        String orderPeriodStr = getString_UrlDecode_UTF8("orderPeriod"); // 订单有效期
        paramMap.put("orderPeriod",orderPeriodStr);
        String returnUrl = getString_UrlDecode_UTF8("returnUrl"); // 页面通知返回url
        paramMap.put("returnUrl",returnUrl);
        String notifyUrl = getString_UrlDecode_UTF8("notifyUrl"); // 后台消息通知Url
        paramMap.put("notifyUrl",notifyUrl);
        String remark = getString_UrlDecode_UTF8("remark"); // 支付备注
        paramMap.put("remark",remark);
        String sign = getString_UrlDecode_UTF8("sign"); // 签名

        String field1 = getString_UrlDecode_UTF8("field1"); // 扩展字段1
        paramMap.put("field1",field1);
        String field2 = getString_UrlDecode_UTF8("field2"); // 扩展字段2
        paramMap.put("field2",field2);
        String field3 = getString_UrlDecode_UTF8("field3"); // 扩展字段3
        paramMap.put("field3",field3);
        String field4 = getString_UrlDecode_UTF8("field4"); // 扩展字段4
        paramMap.put("field4",field4);
        String field5 = getString_UrlDecode_UTF8("field5"); // 扩展字段5
        paramMap.put("field5",field5);
        
        LOG.info("===>initPay paramMap:" + paramMap.toString());

        Date orderDate = DateUtils.parseDate(orderDateStr,"yyyyMMdd");
        Date orderTime = DateUtils.parseDate(orderTimeStr,"yyyyMMddHHmmss");
        Integer orderPeriod = Integer.valueOf(orderPeriodStr);

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null){
        	 LOG.info("===>用户支付配置有误" );
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        if (!MerchantApiUtil.isRightSign(paramMap,rpUserPayConfig.getPaySecret(),sign)){
        	LOG.info("===>订单签名异常" );
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单签名异常");
        }

        BigDecimal orderPrice = BigDecimal.valueOf(Double.valueOf(orderPriceStr));
        if (StringUtil.isEmpty(payWayCode)){//非直连方式
            RpPayGateWayPageShowVo payGateWayPageShowVo = rpTradePaymentManagerService.initNonDirectScanPay(payKey, productName, orderNo, orderDate, orderTime, orderPrice, orderIp, orderPeriod, returnUrl
                    , notifyUrl, remark, field1, field2, field3, field4, field5);

            model.addAttribute("payGateWayPageShowVo",payGateWayPageShowVo);//支付网关展示数据
            return "gateway";

        }else{//直连方式

            if (PayWayEnum.TEST_PAY.name().equals(payWayCode)){
                return toTestPay(model ,payKey, productName, orderNo, orderDate, orderTime, orderPrice, payWayCode, orderIp, orderPeriod, returnUrl
                        , notifyUrl, remark, field1, field2, field3, field4, field5);
            }else {
            	
            	// TEST_PAY_HTTP_CLIENT
                ScanPayResultVo scanPayResultVo = rpTradePaymentManagerService.initDirectScanPay(payKey, productName, orderNo, orderDate, orderTime, orderPrice, payWayCode, orderIp, orderPeriod, returnUrl
                        , notifyUrl, remark, field1, field2, field3, field4, field5);

                model.addAttribute("codeUrl",scanPayResultVo.getCodeUrl());//支付二维码

                if (PayWayEnum.WEIXIN.name().equals(scanPayResultVo.getPayWayCode())){
                    model.addAttribute("queryUrl", WeixinConfigUtil.readConfig("order_query_url") + "?orderNO=" + orderNo + "&payKey=" + payKey);
                    model.addAttribute("productName",productName);//产品名称
                    model.addAttribute("orderPrice",orderPrice);//订单价格
                    return "weixinPayScanPay";
                }else if (PayWayEnum.ALIPAY.name().equals(scanPayResultVo.getPayWayCode())){
                    return "alipayDirectPay";
                }else if(PayWayEnum.TEST_PAY_HTTP_CLIENT.name().equals(scanPayResultVo.getPayWayCode())){
                	return "weixinPayScanPay";
                }
               
            }
        }

        return "gateway";
    }

    @RequestMapping("/toPay/{orderNo}/{payWay}/{payKey}")
    public String toPay(@PathVariable("payKey")String payKey , @PathVariable("orderNo")String orderNo , @PathVariable("payWay")String payWay ,Model model){

        ScanPayResultVo scanPayResultVo = rpTradePaymentManagerService.toNonDirectScanPay(payKey, orderNo, payWay);

        model.addAttribute("codeUrl",scanPayResultVo.getCodeUrl());//支付二维码

        if (PayWayEnum.WEIXIN.name().equals(scanPayResultVo.getPayWayCode())){
            model.addAttribute("queryUrl", WeixinConfigUtil.readConfig("order_query_url") + "?orderNO=" + orderNo + "&payKey=" + payKey);
            model.addAttribute("productName",scanPayResultVo.getProductName());//产品名称
            model.addAttribute("orderPrice",scanPayResultVo.getOrderAmount());//订单价格
            return "weixinPayScanPay";
        }else if (PayWayEnum.ALIPAY.name().equals(scanPayResultVo.getPayWayCode())){
            return "alipayDirectPay";
        }

        return null;
    }

    /**
     *  支付结果查询接口
     * @param httpServletResponse
     */
    @RequestMapping("orderQuery")
    public void orderQuery(HttpServletResponse httpServletResponse)throws IOException{

        String payKey = getString_UrlDecode_UTF8("payKey"); // 企业支付KEY
        String orderNO = getString_UrlDecode_UTF8("orderNO"); // 订单号

        OrderPayResultVo payResult = rpTradePaymentQueryService.getPayResult(payKey, orderNO);

        JsonUtils.responseJson(httpServletResponse,payResult);

    }



    private String toTestPay(Model model,String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, Integer orderPeriod, String returnUrl
            , String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        //获取商户传入参数
        paramMap.put("payKey", payKey);
        paramMap.put("productName", productName);
        paramMap.put("orderNo", orderNo);
        String orderPriceStr = getString_UrlDecode_UTF8("orderPrice"); // 订单金额 , 单位:元
        paramMap.put("orderPrice", orderPriceStr);
        paramMap.put("payWayCode", payWayCode);
        paramMap.put("orderIp", orderIp);
        String orderDateStr = getString_UrlDecode_UTF8("orderDate"); // 订单日期
        paramMap.put("orderDate", orderDateStr);
        String orderTimeStr = getString_UrlDecode_UTF8("orderTime"); // 订单日期
        paramMap.put("orderTime", orderTimeStr);
        String orderPeriodStr = getString_UrlDecode_UTF8("orderPeriod"); // 订单有效期
        paramMap.put("orderPeriod", orderPeriodStr);
        paramMap.put("returnUrl", returnUrl);
        paramMap.put("notifyUrl", notifyUrl);
        paramMap.put("remark", remark);
        String sign = getString_UrlDecode_UTF8("sign"); // 签名

        paramMap.put("field1", field1);
        paramMap.put("field2", field2);
        paramMap.put("field3", field3);
        paramMap.put("field4", field4);
        paramMap.put("field5", field5);

        LOG.info("===>initPay paramMap:" + paramMap.toString());

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        if (!MerchantApiUtil.isRightSign(paramMap, rpUserPayConfig.getPaySecret(), sign)) {
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单签名异常");
        }

        OrderPayResultVo scanPayByResult = rpTradePaymentManagerService.completeTestPay(payKey, productName, orderNo, orderDate, orderTime, orderPrice, payWayCode, orderIp, orderPeriod, returnUrl
                , notifyUrl, remark, field1, field2, field3, field4, field5);

        model.addAttribute("scanPayByResult",scanPayByResult);

        return "PayResult";
    }

}
