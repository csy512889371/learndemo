/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.controller.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.account.api.RpAccountHistoryService;
import com.roncoo.pay.service.account.api.RpAccountService;
import com.roncoo.pay.service.account.entity.RpAccount;
import com.roncoo.pay.service.account.entity.RpAccountHistory;
import com.roncoo.pay.service.user.api.RpUserInfoService;
import com.roncoo.pay.service.user.entity.RpUserInfo;

/**
 * @类功能说明： 账户信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private RpAccountService rpAccountService;
	@Autowired
	private RpUserInfoService rpUserInfoService;
	@Autowired
	private RpAccountHistoryService rpAccountHistoryService;

	/**
	 * 函数功能说明 ： 查询账户信息
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
	public String list(RpAccount rpAccount,PageParam pageParam, Model model) {
		PageBean pageBean = rpAccountService.listPage(pageParam, rpAccount);
		List<Object> recordList = pageBean.getRecordList();
		for(Object obj : recordList){
			RpAccount account = (RpAccount)obj;
			RpUserInfo userInfo = rpUserInfoService.getDataByMerchentNo(account.getUserNo());
			account.setUserName(userInfo.getUserName());
		}
		model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpAccount",rpAccount);
		return "account/list";
	}

	/**
	 * 函数功能说明 ： 查询账户历史信息
	 * 
	 * @参数： @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/historyList", method ={RequestMethod.POST,RequestMethod.GET})
	public String historyList(RpAccountHistory rpAccountHistory,PageParam pageParam, Model model) {
		PageBean pageBean = rpAccountHistoryService.listPage(pageParam, rpAccountHistory);
		List<Object> recordList = pageBean.getRecordList();
		for(Object obj : recordList){
			RpAccountHistory history = (RpAccountHistory)obj;
			RpUserInfo userInfo = rpUserInfoService.getDataByMerchentNo(history.getUserNo());
			history.setUserName(userInfo.getUserName());
		}
		model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpAccountHistory",rpAccountHistory);
		return "account/historyList";
	}
}
