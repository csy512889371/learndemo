/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.user.api;

import java.util.List;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.service.user.entity.RpPayWay;
import com.roncoo.pay.service.user.exceptions.PayBizException;

/**
 * @类功能说明： 支付方式service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
public interface RpPayWayService{
	
	/**
	 * 保存
	 */
	void saveData(RpPayWay rpPayWay) throws PayBizException;

	/**
	 * 更新
	 */
	void updateData(RpPayWay rpPayWay) throws PayBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpPayWay getDataById(String id) throws PayBizException;
	
	/**
	 * 根据支付方式、渠道编码获取数据
	 * @param rpTypeCode
	 * @return
	 */
	RpPayWay getByPayWayTypeCode(String payProductCode, String payWayCode, String rpTypeCode) throws PayBizException;
	

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpPayWay rpPayWay) throws PayBizException;
	
	/**
	 * 绑定支付费率
	 * @param payWayCode
	 * @param payTypeCode
	 * @param payRate
	 */
	void createPayWay(String payProductCode, String payWayCode, String payTypeCode, Double payRate)  throws PayBizException;
	
	/**
	 * 根据支付产品获取支付方式
	 * @param payProductCode
	 */
	List<RpPayWay> listByProductCode(String payProductCode) throws PayBizException;
	
	/**
	 * 获取所有支付方式
	 */
	List<RpPayWay> listAll() throws PayBizException;
	
}