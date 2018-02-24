/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.common.core.dwz;

/**
 * 
 * @类功能说明：封装DWZ框架ajax请求及响应的参数. .
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院:www.roncoo.com）
 * @作者：Along.shen
 * @创建时间：2016年5月31日,下午6:16:22.
 * @版本：V1.0
 *
 */
public final class DWZ {

	/**
	 * Ajax请求成功,statusCode 为200.
	 */
	public static final String SUCCESS = "200";
	/**
	 * Ajax请求失败,statusCode 为300.
	 */
	public static final String ERROR = "300";
	/**
	 * Ajax请求超时,statusCode 为301.
	 */
	public static final String TIMEOUT = "301";

	// //////////////////////////////////////////

	/**
	 * callbackType ajax请求回调类型. <br/>
	 * callbackType如果是closeCurrent就会关闭当前tab选项,
	 */
	public static final String CLOSE = "closeCurrent";

	/**
	 * 只有callbackType="forward"时需要forwardUrl值,以重定向到另一个URL.
	 */
	public static final String FORWARD = "forward";
	
	public static final String AJAX_DONE = "common/ajaxDone";
	
	public static final String SUCCESS_MSG = "操作成功";

	/**
	 * 私有构造方法,单例模式.
	 */
	private DWZ() {

	}
}
