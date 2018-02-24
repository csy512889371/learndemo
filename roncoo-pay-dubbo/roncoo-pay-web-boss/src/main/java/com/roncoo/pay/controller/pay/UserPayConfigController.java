/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.controller.pay;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.roncoo.pay.common.core.dwz.DWZ;
import com.roncoo.pay.common.core.dwz.DwzAjax;
import com.roncoo.pay.common.core.enums.BankAccountTypeEnum;
import com.roncoo.pay.common.core.enums.PayWayEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.user.api.RpUserInfoService;
import com.roncoo.pay.service.user.api.RpUserPayConfigService;
import com.roncoo.pay.service.user.api.RpUserPayInfoService;
import com.roncoo.pay.service.user.entity.RpUserInfo;
import com.roncoo.pay.service.user.entity.RpUserPayConfig;
import com.roncoo.pay.service.user.entity.RpUserPayInfo;
import com.roncoo.pay.service.user.enums.BankCodeEnum;
import com.roncoo.pay.service.user.enums.CardTypeEnum;
import com.roncoo.pay.service.user.enums.FundInfoTypeEnum;

/**
 * @类功能说明： 用户支付设置管理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-28 上午11:14:10
 * @版本：V1.0
 */
@Controller
@RequestMapping("/pay/config")
public class UserPayConfigController {
	
	
	@Autowired
	private RpUserPayConfigService rpUserPayConfigService;
	@Autowired
	private RpUserInfoService rpUserInfoService;
	@Autowired
	private RpUserPayInfoService rpUserPayInfoService;


	/**
	 * 函数功能说明 ： 查询分页数据
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
	public String list(RpUserPayConfig rpUserPayConfig, PageParam pageParam, Model model) {
		PageBean pageBean = rpUserPayConfigService.listPage(pageParam, rpUserPayConfig);
		model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpUserPayConfig", rpUserPayConfig);
		return "pay/config/list";
	}
	
	/**
	 * 函数功能说明 ：跳转添加
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(Model model) {
		model.addAttribute("FundInfoTypeEnums", FundInfoTypeEnum.toList());
		return "pay/config/add";
	}
	
	/**
	 * 函数功能说明 ： 保存
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, Model model, RpUserPayConfig rpUserPayConfig, DwzAjax dwz) {
		String userNo = request.getParameter("user.userNo");
		String userName = request.getParameter("user.userName");
		String productCode = request.getParameter("product.productCode");
		String productName = request.getParameter("product.productName");
		String we_appId = request.getParameter("we_appId");
		String we_merchantId = request.getParameter("we_merchantId");
		String we_partnerKey = request.getParameter("we_partnerKey");
		String ali_partner = request.getParameter("ali_partner");
		String ali_key = request.getParameter("ali_key");
		String ali_sellerId = request.getParameter("ali_sellerId");
		rpUserPayConfigService.createUserPayConfig(userNo, userName, productCode, productName, 
				rpUserPayConfig.getRiskDay(), rpUserPayConfig.getFundIntoType(), rpUserPayConfig.getIsAutoSett(), we_appId
				, we_merchantId, we_partnerKey, ali_partner, ali_sellerId, ali_key);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}

	/**
	 * 函数功能说明 ：跳转编辑
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/editUI", method = RequestMethod.GET)
	public String editUI(Model model,@RequestParam("userNo") String userNo) {
		RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByUserNo(userNo,null);
		RpUserPayInfo wxUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
		RpUserPayInfo aliUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
		model.addAttribute("FundInfoTypeEnums", FundInfoTypeEnum.toList());
		model.addAttribute("rpUserPayConfig", rpUserPayConfig);
		model.addAttribute("wxUserPayInfo", wxUserPayInfo);
		model.addAttribute("aliUserPayInfo", aliUserPayInfo);
		return "pay/config/edit";
	}
	
	/**
	 * 函数功能说明 ： 更新
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request, Model model, RpUserPayConfig rpUserPayConfig,DwzAjax dwz) {
		String productCode = request.getParameter("product.productCode");
		String productName = request.getParameter("product.productName");
		String we_appId = request.getParameter("we_appId");
		String we_merchantId = request.getParameter("we_merchantId");
		String we_partnerKey = request.getParameter("we_partnerKey");
		String ali_partner = request.getParameter("ali_partner");
		String ali_key = request.getParameter("ali_key");
		String ali_sellerId = request.getParameter("ali_sellerId");
		rpUserPayConfigService.updateUserPayConfig(rpUserPayConfig.getUserNo(), productCode, productName, 
				rpUserPayConfig.getRiskDay(), rpUserPayConfig.getFundIntoType(), rpUserPayConfig.getIsAutoSett(),
				we_appId, we_merchantId, we_partnerKey, ali_partner, ali_sellerId, ali_key);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ： 删除
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/delete", method ={RequestMethod.POST,RequestMethod.GET})
	public String delete(Model model, DwzAjax dwz, @RequestParam("userNo") String userNo) {
		rpUserPayConfigService.deleteUserPayConfig(userNo);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	/**
	 * 函数功能说明 ： 审核
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/audit", method ={RequestMethod.POST,RequestMethod.GET})
	public String audit(Model model, DwzAjax dwz, @RequestParam("userNo") String userNo
			, @RequestParam("auditStatus") String auditStatus) {
		rpUserPayConfigService.audit(userNo, auditStatus);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
}
