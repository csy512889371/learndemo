/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.point.api;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.point.entity.RpPointAccount;
import com.roncoo.pay.service.point.exceptions.PointBizException;
import org.mengyun.tcctransaction.api.TransactionContext;

/**
 * @类功能说明： 账户service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
public interface RpPointAccountService{
	
	/**
	 * 保存
	 */
	void saveData(RpPointAccount rpPointAccount) throws PointBizException;

	/**
	 * 更新
	 */
	void updateData(RpPointAccount rpPointAccount) throws PointBizException;

	void creditToPointAccountTcc(TransactionContext transactionContext, String userNo, Integer pointAmount, String requestNo,String bankTrxNo, String trxType, String remark) throws PointBizException;

	void creditToPointAccount(String userNo, Integer pointAmount, String requestNo,String bankTrxNo, String trxType, String remark) throws PointBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpPointAccount getDataById(String id) throws PointBizException;
	

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpPointAccount rpPointAccount) throws PointBizException;
	
}