/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.point.aip.impl;

import com.roncoo.pay.common.core.enums.PublicStatusEnum;
import com.roncoo.pay.common.core.exception.BizException;
import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.service.point.api.RpPointAccountQueryService;
import com.roncoo.pay.service.point.dao.RpPointAccountDao;
import com.roncoo.pay.service.point.entity.RpPointAccount;
import com.roncoo.pay.service.point.exceptions.PointBizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Service("rpPointAccountQueryService")
public class RpPointAccountQueryServiceImpl implements RpPointAccountQueryService {
	@Autowired
	private RpPointAccountDao rpPointAccountDao;

	private static final Logger LOG = LoggerFactory.getLogger(RpPointAccountQueryServiceImpl.class);


	/**
	 * 根据用户编号编号获取账户信息
	 * 
	 * @param userNo
	 *            用户编号
	 * @return
	 */
	public RpPointAccount getAccountByUserNo(String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		LOG.info("根据用户编号查询账户信息");
		RpPointAccount account = this.rpPointAccountDao.getBy(map);
		if (account == null) {
			throw PointBizException.ACCOUNT_NOT_EXIT;
		}
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(account.getEditTime())) {
			account.setEditTime(new Date());
			rpPointAccountDao.update(account);
		}
		return account;
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

		return rpPointAccountDao.listPage(pageParam, params);
	}
	
	
    /**
	 * 获取所有账户
	 * @return
	 */
    @Override
    public List<RpPointAccount> listAll(){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return rpPointAccountDao.listBy(paramMap);
	}

}