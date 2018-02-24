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
import com.roncoo.pay.service.user.entity.RpUserPayConfig;
import com.roncoo.pay.service.user.exceptions.PayBizException;

import java.util.List;

/**
 * @类功能说明： 用户支付配置service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院：www.roncoo.com）
 * @作者：zh
 * @创建时间：2016-5-18 上午11:14:10
 * @版本：V1.0
 */
public interface RpUserPayConfigService{
	
	/**
	 * 保存
	 */
	void saveData(RpUserPayConfig rpUserPayConfig) throws PayBizException;

	/**
	 * 更新
	 */
	void updateData(RpUserPayConfig rpUserPayConfig) throws PayBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpUserPayConfig getDataById(String id) throws PayBizException;
	

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpUserPayConfig rpUserPayConfig) throws PayBizException;

	/**
	 * 根据商户编号获取已生效的支付配置
	 * @param userNo
	 * @return
	 */
	RpUserPayConfig getByUserNo(String userNo) throws PayBizException;
	
	/**
	 * 根据商户编号获取支付配置
	 * @param userNo
	 * @param auditStatus
	 * @return
	 */
	RpUserPayConfig getByUserNo(String userNo, String auditStatus) throws PayBizException;
	
	/**
	 * 根据支付产品获取已生效数据
	 */
	List<RpUserPayConfig> listByProductCode(String productCode) throws PayBizException;
	
	/**
	 * 根据支付产品获取数据
	 */
	List<RpUserPayConfig> listByProductCode(String productCode, String auditStatus) throws PayBizException;
	
	/**
	 * 创建用户支付配置
	 */
	void createUserPayConfig(String userNo, String userName, String productCode, String productName, Integer riskDay, String fundIntoType,
			String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner, String ali_sellerId, String ali_key)  throws PayBizException;
	
	/**
	 * 删除支付产品
	 * @param userNo
	 */
	void deleteUserPayConfig(String userNo) throws PayBizException;
	
	/**
	 * 修改用户支付配置
	 */
	void updateUserPayConfig(String userNo, String productCode, String productName, Integer riskDay, String fundIntoType,
			String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner, String ali_sellerId, String ali_key)  throws PayBizException;

	/**
	 * 审核
	 * @param userNo
	 * @param auditStatus
	 */
	void audit(String userNo, String auditStatus) throws PayBizException;
	
	/**
	 * 根据商户key获取已生效的支付配置
	 * @param payKey
	 * @return
	 */
	RpUserPayConfig getByPayKey(String payKey) throws PayBizException;
	
}