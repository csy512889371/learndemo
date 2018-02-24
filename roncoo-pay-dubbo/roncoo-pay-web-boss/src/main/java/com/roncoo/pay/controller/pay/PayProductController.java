/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.controller.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.roncoo.pay.common.core.dwz.DWZ;
import com.roncoo.pay.common.core.dwz.DwzAjax;
import com.roncoo.pay.common.core.enums.PublicEnum;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.user.api.RpPayProductService;
import com.roncoo.pay.service.user.entity.RpPayProduct;

/**
 * @类功能说明： 支付产品管理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
@Controller
@RequestMapping("/pay/product")
public class PayProductController{
	
	@Autowired
	private RpPayProductService rpPayProductService;
	


	/**
	 * 函数功能说明 ： 查询分页
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
	public String list(RpPayProduct rpPayProduct, PageParam pageParam, Model model) {
		PageBean pageBean = rpPayProductService.listPage(pageParam, rpPayProduct);
		model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpPayProduct", rpPayProduct);
		return "pay/product/list";
	}
	
	/**
	 * 函数功能说明 ：跳转添加
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		
		return "pay/product/add";
	}
	
	/**
	 * 函数功能说明 ： 保存
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Model model, RpPayProduct rpPayProduct,DwzAjax dwz) {
		rpPayProductService.createPayProduct(rpPayProduct.getProductCode(), rpPayProduct.getProductName());
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
	public String delete(Model model, DwzAjax dwz, @RequestParam("productCode") String productCode) {
		rpPayProductService.deletePayProduct(productCode);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
	
	
	/**
	 * 函数功能说明 ： 查找带回
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/lookupList", method ={RequestMethod.POST,RequestMethod.GET})
	public String lookupList(RpPayProduct rpPayProduct, PageParam pageParam, Model model) {
		//查询已生效数据
		rpPayProduct.setAuditStatus(PublicEnum.YES.name());
		PageBean pageBean = rpPayProductService.listPage(pageParam, rpPayProduct);
		model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpPayProduct", rpPayProduct);
		return "pay/product/lookupList";
	}
	
	/**
	 * 函数功能说明 ： 审核
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/audit", method ={RequestMethod.POST,RequestMethod.GET})
	public String audit(Model model, DwzAjax dwz, @RequestParam("productCode") String productCode
			, @RequestParam("auditStatus") String auditStatus) {
		rpPayProductService.audit(productCode, auditStatus);
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage(DWZ.SUCCESS_MSG);
		model.addAttribute("dwz", dwz);
		return DWZ.AJAX_DONE;
	}
}
