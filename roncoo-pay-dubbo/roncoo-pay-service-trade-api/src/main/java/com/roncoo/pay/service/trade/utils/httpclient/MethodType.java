/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.utils.httpclient;
/**
 * 
 *  把httpclient 发送请求的 方法封装成枚举类型 
 *  这样可以避免字符串出错的情况
 *   GET 代表法get 请求 
 *   POST代表发post 请求 
 *   等等
 */
public enum MethodType {
	
		GET, POST, DELETE, PUT, TRACE, OPTION
	
}
