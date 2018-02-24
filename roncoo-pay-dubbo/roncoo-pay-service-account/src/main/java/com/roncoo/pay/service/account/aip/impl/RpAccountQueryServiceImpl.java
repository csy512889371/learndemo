/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.account.aip.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.service.account.api.RpAccountQueryService;
import com.roncoo.pay.service.account.dao.RpAccountDao;
import com.roncoo.pay.service.account.dao.RpAccountHistoryDao;
import com.roncoo.pay.service.account.entity.RpAccount;
import com.roncoo.pay.service.account.entity.RpAccountHistory;
import com.roncoo.pay.service.account.exceptions.AccountBizException;
import com.roncoo.pay.service.account.vo.DailyCollectAccountHistoryVo;

/**
 * @类功能说明： 账户查询service实现类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
@Service("rpAccountQueryService")
public class RpAccountQueryServiceImpl implements RpAccountQueryService {
	@Autowired
	private RpAccountDao rpAccountDao;
	@Autowired
	private RpAccountHistoryDao rpAccountHistoryDao;

	private static final Logger LOG = LoggerFactory.getLogger(RpAccountQueryServiceImpl.class);

	/**
	 * 根据账户编号获取账户信息
	 * 
	 * @param accountNo
	 *            账户编号
	 * @return
	 */
	public RpAccount getAccountByAccountNo(String accountNo) {
		LOG.info("根据账户编号查询账户信息");
		RpAccount account = this.rpAccountDao.getByAccountNo(accountNo);
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(account.getEditTime())) {
			account.setTodayExpend(BigDecimal.ZERO);
			account.setTodayIncome(BigDecimal.ZERO);
			account.setEditTime(new Date());
			rpAccountDao.update(account);
		}
		return account;
	}

	/**
	 * 根据用户编号编号获取账户信息
	 * 
	 * @param userNO
	 *            用户编号
	 * @return
	 */
	public RpAccount getAccountByUserNo(String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		LOG.info("根据用户编号查询账户信息");
		RpAccount account = this.rpAccountDao.getBy(map);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT;
		}
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(account.getEditTime())) {
			account.setTodayExpend(BigDecimal.ZERO);
			account.setTodayIncome(BigDecimal.ZERO);
			account.setEditTime(new Date());
			rpAccountDao.update(account);
		}
		return account;
	}

	/**
	 * 分页查询账户历史单用户
	 */
	public PageBean queryAccountHistoryListPage(PageParam pageParam, String accountNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);
		return rpAccountDao.listPage(pageParam, params);
	}

	/**
	 * 分页查询账户历史单角色
	 */
	public PageBean queryAccountHistoryListPageByRole(PageParam pageParam, Map<String, Object> params){
		String accountType = (String) params.get("accountType");
		if (StringUtils.isBlank(accountType)) {
			throw AccountBizException.ACCOUNT_TYPE_IS_NULL;
		}
		return rpAccountDao.listPage(pageParam, params);

	}

	/**
	 * 获取账户历史单角色
	 * 
	 * @param accountNo
	 *            账户编号
	 * @param requestNo
	 *            请求号
	 * @param trxType
	 *            业务类型
	 * @return AccountHistory
	 */
	public RpAccountHistory getAccountHistoryByAccountNo_requestNo_trxType(String accountNo, String requestNo, Integer trxType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountNo", accountNo);
		map.put("requestNo", requestNo);
		map.put("trxType", trxType);
		return rpAccountHistoryDao.getBy(map);
	}

	/**
	 * 日汇总账户待结算金额 .
	 * 
	 * @param accountNo
	 *            账户编号
	 * @param statDate
	 *            统计日期
	 * @param riskDay
	 *            风险预测期
	 * @param fundDirection
	 *            资金流向
	 * @return
	 */
	public List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(String accountNo, String statDate, Integer riskDay, Integer fundDirection) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);
		params.put("statDate", statDate);
		params.put("riskDay", riskDay);
		params.put("fundDirection", fundDirection);
		return rpAccountHistoryDao.listDailyCollectAccountHistoryVo(params);

	}

	/**
	 * 根据参数分页查询账户.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param params
	 *            查询参数，可以为null.
	 * @return AccountList.
	 * @throws BizException
	 */
	public PageBean queryAccountListPage(PageParam pageParam, Map<String, Object> params) {

		return rpAccountDao.listPage(pageParam, params);
	}

	/**
	 * 根据参数分页查询账户历史.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param params
	 *            查询参数，可以为null.
	 * @return AccountHistoryList.
	 * @throws BizException
	 */
	public PageBean queryAccountHistoryListPage(PageParam pageParam, Map<String, Object> params) {

		return rpAccountHistoryDao.listPage(pageParam, params);
	}
	
    /**
	 * 获取所有账户
	 * @return
	 */
    @Override
    public List<RpAccount> listAll(){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return rpAccountDao.listBy(paramMap);
	}

}