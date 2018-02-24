/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

import com.roncoo.utils.MerchantApiUtil;
import com.roncoo.utils.PayConfigUtil;
import com.roncoo.utils.httpclient.SimpleHttpUtils;

public class MainTest {

	public static void main(String[] args) {
		
		List<MerchantKey> listMerchantKey = new ArrayList<MerchantKey>();//添加商户key
		listMerchantKey.add(new MerchantKey("4c52295065654407b42797cda80dd07d", "6606353e0dab4f7b9a723f2d3e3276b7"));//商户1 roncoo
		listMerchantKey.add(new MerchantKey("abcf900288114d5fa7fde764966eb2ff", "d94d7c2d56eb4b06828cf746c8be17e6"));//商户2 Along
		listMerchantKey.add(new MerchantKey("8ba459f55ff04f39b0128a3cb4a48f2b", "3e2ca2eb1f024570b241d65eb4832c37"));//商户3 wusc
		listMerchantKey.add(new MerchantKey("93d1ea2f9f3b448994f39d6efc7746ef", "fad7ea79810c4af7a973c091aa7c6143"));//商户4 leslie
		listMerchantKey.add(new MerchantKey("ca6577dff6d647ac882dfb405ceda21e", "1b8da6c9b7544856955fcff6bf920f84"));//商户5 hpt
		
		//listMerchantKey.add(new MerchantKey("4c52295065654407b42797cda80dd07d", "6606353e0dab4f7b9a723f2d3e3276b7"));//商户1 roncoo
		//listMerchantKey.add(new MerchantKey("abcf900288114d5fa7fde764966eb2ff", "d94d7c2d56eb4b06828cf746c8be17e6"));//商户2 Along
		//listMerchantKey.add(new MerchantKey("8ba459f55ff04f39b0128a3cb4a48f2b", "3e2ca2eb1f024570b241d65eb4832c37"));//商户3 wusc
		//listMerchantKey.add(new MerchantKey("93d1ea2f9f3b448994f39d6efc7746ef", "fad7ea79810c4af7a973c091aa7c6143"));//商户4 leslie
		//listMerchantKey.add(new MerchantKey("ca6577dff6d647ac882dfb405ceda21e", "1b8da6c9b7544856955fcff6bf920f84"));//商户5 hpt
		
		for(MerchantKey merchantKey : listMerchantKey){
			final String payKey = merchantKey.getPayKey();
			final String paySecret = merchantKey.getPaySecret();
			new Thread(new Runnable() {
				@Override
				public void run() {
					testGateWayPay(payKey ,paySecret);
				}
			}).start();
		}
		
	}
	
	/**
	 * 模拟支付
	 */
	private static void testGateWayPay( String payKey , String paySecret){
				
		long threadID = Thread.currentThread().getId();
		for (int i = 0; i < 100; i++) { //每条线程生成数
			try {
				int random = RandomUtils.nextInt(10);
				long sleepNum  = 10l * random;
				Thread.sleep(sleepNum);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// 创建支付订单（如果失败，需要商户重新发起）
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String merchantOrderNo = "pt"+ threadID +sdf.format(new Date()) + 000 + i; // 生成商户订单号
			Map<String , Object> paramMap = getInitRequestMap(merchantOrderNo ,  payKey ,  paySecret); // 构建订单的请求参数
			String resultStr = SimpleHttpUtils.httpGet(PayConfigUtil.readConfig("scanPayUrl"), paramMap); // 请求网关创建订单
			System.out.println(resultStr);
			
			if(resultStr == null || "".equals(resultStr)){//模拟生成失败，跳出这次循环，继续下次操作。
				continue;
			}
			
			// 用户支付行为模拟（等待片刻）
			try {
				Thread.sleep(200L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//模拟构建银行扣款成功结果通知
			Map<String , Object> notifyMap = getNotifyRequestMap(merchantOrderNo);
			String notifyResultStr = SimpleHttpUtils.httpGet("http://192.168.1.162:8082/roncoo-pay-web-gateway/scanPayNotify/notify/TEST_PAY_HTTP_CLIENT", notifyMap);
			System.out.println(notifyResultStr);
		}
	}
	
	/**
	 * 模拟构建银行扣款成功结果通知
	 * @param bankOrderNo
	 * @return
	 */
	private static Map<String , Object> getNotifyRequestMap(String bankOrderNo){
		Map<String, Object> notifyMap = new HashMap<String, Object>();
		notifyMap.put("result_code", "SUCCESS");
		String timeEnd = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		notifyMap.put("time_end", timeEnd);
		notifyMap.put("out_trade_no", bankOrderNo);
		notifyMap.put("transaction_id", timeEnd);
		
		return notifyMap;
	}
	
	/**
	 * 构建订单的请求参数
	 * @param merchantOrderNo
	 * @param payKey
	 * @param paySecret
	 * @return
	 */
	private static Map<String , Object> getInitRequestMap(String merchantOrderNo , String payKey , String paySecret){
		Map<String , Object> paramMap = new HashMap<String , Object>();

        paramMap.put("orderPrice","10"); // 订单金额 , 单位:元
        paramMap.put("payWayCode","TEST_PAY_HTTP_CLIENT"); //模拟网关支付
        paramMap.put("orderNo",merchantOrderNo);   // 订单编号

        Date orderDate = new Date();//订单日期
        String orderDateStr = new SimpleDateFormat("yyyyMMdd").format(orderDate);// 订单日期
        paramMap.put("orderDate",orderDateStr);

        Date orderTime = new Date();//订单时间
        String orderTimeStr =  new SimpleDateFormat("yyyyMMddHHmmss").format(orderTime);// 订单时间
        paramMap.put("orderTime",orderTimeStr);

        paramMap.put("payKey",payKey);
        paramMap.put("productName","模拟支付网关支付产品");// 商品名称
        paramMap.put("orderIp","127.0.0.1");
        paramMap.put("orderPeriod","30"); // 订单有效期
        paramMap.put("returnUrl","http://192.168.1.162:8083/roncoo-pay-web-sample-shop/roncooPayNotify/notify"); // 页面通知返回url
        paramMap.put("notifyUrl","http://192.168.1.162:8083/roncoo-pay-web-sample-shop/roncooPayNotify/notify");// 后台消息通知Url
        paramMap.put("remark"," 支付备注"); // 支付备注

        ////////////扩展字段,选填,原值返回///////////
        String field1 = "扩展字段1"; // 扩展字段1
        paramMap.put("field1",field1);
        String field2 = "扩展字段2"; // 扩展字段2
        paramMap.put("field2",field2);
        String field3 = "扩展字段3"; // 扩展字段3
        paramMap.put("field3",field3);
        String field4 = "扩展字段4"; // 扩展字段4
        paramMap.put("field4",field4);
        String field5 = "扩展字段5"; // 扩展字段5
        paramMap.put("field5",field5);

        /////签名及生成请求API的方法///
        String sign = MerchantApiUtil.getSign(paramMap, paySecret);
        paramMap.put("sign",sign);
        
        return paramMap;
	}
}

class MerchantKey{
	private String payKey;
	
	private String paySecret;

	public String getPayKey() {
		return payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public String getPaySecret() {
		return paySecret;
	}

	public void setPaySecret(String paySecret) {
		this.paySecret = paySecret;
	}
	
	public MerchantKey(String payKey , String paySecret){
		this.payKey = payKey;
		this.paySecret = paySecret;
	}
}
