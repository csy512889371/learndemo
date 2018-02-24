/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.accounting.aip.impl; 

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.accounting.api.RpAccountingVoucherService;
import com.roncoo.pay.service.accounting.dao.RpAccountingVoucherDao;
import com.roncoo.pay.service.accounting.entity.RpAccountingVoucher;


/**
 * 会计原始凭证服务.
 * @author WuShuicheng.
 *
 */
@Component("rpAccountingVoucherService")
public class RpAccountingVoucherServiceImpl implements RpAccountingVoucherService {
	
	private static final Log LOG = LogFactory.getLog(RpAccountingVoucherServiceImpl.class);
	
	@Autowired
	private RpAccountingVoucherDao rpAccountingVoucherDao;
	

	/**
	 * 创建会计分录原始凭证信息.
	 * @param entryType 会计分录类型.
	 * @param voucherNo 原始凭证号 （交易记录的唯一凭证号）.
	 * @param payerAccountNo 付款方账户编号.
	 * @param receiverAccountNo 收款方账户编号.
	 * @param payerChangeAmount 付款方帐户变动金额.
	 * @param receiverChangeAmount 收款方帐户变动金额.
	 * @param income 平台收入.
	 * @param cost 平台成本.
	 * @param profit 平台利润.
	 * @param bankChangeAmount 平台银行帐户变动金额.
	 * @param requestNo 请求号 (会计系统自动生成).
	 * @param bankChannelCode 银行渠道编号.
	 * @param bankAccount 银行账户.
	 * @param fromSystem 来源系统.
	 * @param remark 备注.
	 * @param bankOrderNo 银行订单号.
	 * @param payerAccountType 付款方账户类型.
	 * @param payAmount 支付金额.
	 * @param receiverAccountType 收款方账户类型.
	 * @param payerFee 付款方手续费.
	 * @param receiverFee 收款方手续费.
	 */
	public void createAccountingVoucher(int entryType, String voucherNo, String payerAccountNo, String receiverAccountNo,
			double payerChangeAmount, double receiverChangeAmount, double income, double cost, double profit, double bankChangeAmount,
			String requestNo, String bankChannelCode, String bankAccount, int fromSystem, String remark, String bankOrderNo,
			int payerAccountType, double payAmount, int receiverAccountType, double payerFee, double receiverFee) {
		
		RpAccountingVoucher rpAccountingVoucher = rpAccountingVoucherDao.getDataByVoucherNoFromSystem(entryType, voucherNo, fromSystem);
		if(rpAccountingVoucher != null){
			LOG.info("data is exist,voucherNo="+voucherNo);
			return ;
		}
		
		if (StringUtils.isBlank(requestNo)) {
			requestNo = getRequestNo(entryType);
		}

		rpAccountingVoucherDao.createAccountingVoucher(entryType, voucherNo, payerAccountNo, receiverAccountNo, payerChangeAmount,
				receiverChangeAmount, income, cost, profit, bankChangeAmount, requestNo, bankChannelCode, bankAccount, fromSystem, remark,
				bankOrderNo, payerAccountType, payAmount, receiverAccountType, payerFee, receiverFee);
	}
	
	/**
	 * 生成请求号: 会计分录类型+日期+随机数字.
	 * @param entryType 会计分录类型
	 * @return requestNo.
	 */
	public String getRequestNo(int entryType) {
		String requestNo = rpAccountingVoucherDao.buildAccountingVoucherNo(entryType);
		return requestNo;
	}
	
	/***
	 * 根据条件查询单挑数据
	 * @param map
	 * @return
	 */
	public RpAccountingVoucher getBy(Map<String, Object> map) {
		RpAccountingVoucher note = rpAccountingVoucherDao.getBy(map);

		return note;
	}

	/***
	 * 根据条件查询分录请求表数据
	 * @param searchMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getMapBy(Map<String, Object> searchMap) {
		return rpAccountingVoucherDao.getMapBy(searchMap);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return rpAccountingVoucherDao.listPage(pageParam, paramMap);
	}
	
	/**
	 * 查出分录请求表
	 * @param requestNo
	 */
	public RpAccountingVoucher getAccountingVoucherByRequestNo(String requestNo){
		RpAccountingVoucher entity = rpAccountingVoucherDao.findByRequestNo(requestNo);

		return entity;
	}
	
	/**
	 * 修改分录请求
	 * @param param
	 */
	public void updateAccountingVoucher(RpAccountingVoucher entity){
		rpAccountingVoucherDao.update(entity);
	}
	
	
	
}
 