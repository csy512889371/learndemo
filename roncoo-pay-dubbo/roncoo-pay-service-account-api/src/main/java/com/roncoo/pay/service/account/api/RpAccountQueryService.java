/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.account.api;

import java.util.List;
import java.util.Map;

import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.account.entity.RpAccount;
import com.roncoo.pay.service.account.entity.RpAccountHistory;
import com.roncoo.pay.service.account.exceptions.AccountBizException;
import com.roncoo.pay.service.account.vo.DailyCollectAccountHistoryVo;

/**
 * @类功能说明： 账户查询service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
public interface RpAccountQueryService {

	/**
	 * 根据账户编号获取账户信息
	 * 
	 * @param accountNo
	 *            账户编号
	 * @return
	 */
	RpAccount getAccountByAccountNo(String accountNo) throws AccountBizException;

	/**
	 * 根据用户编号编号获取账户信息
	 * 
	 * @param userNO
	 *            用户编号
	 * @return
	 */
	RpAccount getAccountByUserNo(String userNo) throws AccountBizException;

	// /////////////////////账户历史/////////////////////////////

	/**
	 * 根据账户编号分页查询账户历史单商户.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param accountNo
	 *            账户编号.
	 * @return AccountHistoryList.
	 * @throws BizException
	 */
	PageBean queryAccountHistoryListPage(PageParam pageParam, String accountNo) throws AccountBizException;

	/**
	 * 根据账户编号分页查询账户历史单角色.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param params
	 *            参数
	 * @return AccountHistoryList.
	 * @throws BizException
	 */
	PageBean queryAccountHistoryListPageByRole(PageParam pageParam, Map<String, Object> params) throws AccountBizException;
	
	
	/**
	 * 获取账户历史（参数没有可以传null）
	 * 
	 * @param accountNo
	 *            账户编号
	 * @param requestNo
	 *            请求号
	 * @param trxType
	 *            业务类型
	 * @return AccountHistory
	 */
	RpAccountHistory getAccountHistoryByAccountNo_requestNo_trxType(String accountNo, String requestNo, Integer trxType) throws AccountBizException;

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
	 * @throws BizException
	 */
	List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(String accountNo, String statDate, Integer riskDay, Integer fundDirection) throws AccountBizException;

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
	PageBean queryAccountListPage(PageParam pageParam, Map<String, Object> params) throws AccountBizException;
	
	
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
	PageBean queryAccountHistoryListPage(PageParam pageParam, Map<String, Object> params) throws AccountBizException;
	
	/**
	 * 获取所有账户
	 * @return
	 */
	List<RpAccount> listAll() throws AccountBizException;
}