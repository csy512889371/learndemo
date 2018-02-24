/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.controller.trade;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.roncoo.pay.common.core.enums.PayTypeEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.enums.TrxTypeEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.trade.api.RpTradePaymentQueryService;
import com.roncoo.pay.service.trade.enums.TradeStatusEnum;
import com.roncoo.pay.service.trade.vo.PaymentOrderQueryVo;
import com.roncoo.pay.service.user.enums.FundInfoTypeEnum;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/7  上午12:29
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private RpTradePaymentQueryService rpTradePaymentQueryService;

    @RequestMapping(value = "/listPaymentOrder", method ={RequestMethod.POST,RequestMethod.GET})
    public String listPaymentOrder(HttpServletRequest request,PaymentOrderQueryVo paymentOrderQueryVo,PageParam pageParam, Model model) {
        PageBean pageBean = rpTradePaymentQueryService.listPaymentOrderPage(pageParam, paymentOrderQueryVo);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpTradePaymentOrder",paymentOrderQueryVo);//查询条件

        model.addAttribute("statusEnums", TradeStatusEnum.toMap());//状态
        model.addAttribute("payWayNameEnums", PayWayEnum.toMap());//支付方式
        model.addAttribute("payTypeNameEnums", PayTypeEnum.toMap());//支付类型
        model.addAttribute("fundIntoTypeEnums", FundInfoTypeEnum.toMap());//支付类型

        return "trade/listPaymentOrder";
    }


    @RequestMapping(value = "/listPaymentRecord", method ={RequestMethod.POST,RequestMethod.GET})
    public String listPaymentRecord(HttpServletRequest request,PaymentOrderQueryVo paymentOrderQueryVo,PageParam pageParam, Model model) {
        PageBean pageBean = rpTradePaymentQueryService.listPaymentRecordPage(pageParam, paymentOrderQueryVo);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpUserInfo",paymentOrderQueryVo);

        model.addAttribute("statusEnums", TradeStatusEnum.toMap());//状态
        model.addAttribute("payWayNameEnums", PayWayEnum.toMap());//支付方式
        model.addAttribute("payTypeNameEnums", PayTypeEnum.toMap());//支付类型
        model.addAttribute("fundIntoTypeEnums", FundInfoTypeEnum.toMap());//支付类型
        model.addAttribute("trxTypeEnums", TrxTypeEnum.toMap());//支付类型
        return "trade/listPaymentRecord";
    }
}
