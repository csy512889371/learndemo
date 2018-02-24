/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.user.api;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.user.entity.RpUserPayInfo;
import com.roncoo.pay.service.user.exceptions.UserBizException;

/**
 * @类功能说明： 用户第三方支付信息service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
public interface RpUserPayInfoService{
	
	/**
	 * 保存
	 */
	void saveData(RpUserPayInfo rpUserPayInfo) throws UserBizException;

	/**
	 * 更新
	 */
	void updateData(RpUserPayInfo rpUserPayInfo) throws UserBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpUserPayInfo getDataById(String id) throws UserBizException;
	

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpUserPayInfo rpUserPayInfo) throws UserBizException;

	/**
	 * 通过商户编号获取商户支付配置信息
	 * @param userNo
	 * @param payWayCode
	 * @return
	 */
	public RpUserPayInfo getByUserNo(String userNo, String payWayCode) throws UserBizException;
	
}