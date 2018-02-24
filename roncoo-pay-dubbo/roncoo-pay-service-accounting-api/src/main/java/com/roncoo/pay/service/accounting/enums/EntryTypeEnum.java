/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.accounting.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会计分录类型
 * @author WuShuicheng.
 */
public enum EntryTypeEnum {
	
		/*2：内部转账业务相关*/
		ACCOUNT_TRANSFER("内部转账",2001),
		
		/*3：充值业务相关*/
		ACCOUNT_DEPOSIT("账户充值",3001),
		
		/*4：退款业务相关*/
		NET_B2C_REFUND("B2C网银退款",4001), 
		NET_B2B_REFUND("B2B网银退款",4002),
		DEPOSIT_REFUND("充值失败退款",4003),
		FAST_REFUND("快捷支付退款",4004), 
		ACCOUNT_BALANCE_REFUND("余额支付退款",4005), 

		POS_REFUND("退货",4008),
		POS_RECHARGE("POS充值",4013),
	
		/*5：提现业务相关*/
		SETTLEMENT("商户结算",5001), 
		ATM("会员提现",5002),
		REMIT("打款",5003),
	
		/*6：支付业务相关*/
		NET_B2C_PAY("B2C网银支付",6001),
		NET_B2B_PAY("B2B网银支付",6002),
		FAST_PAY("快捷支付",6004),
		ACCOUNT_BALANCE_PAY("余额支付",6005),
		
		POS_PAY("消费",6006),
		
		SPLIT_PAY("分账支付",6011),
		SPLIT_REFUND("分账退款",6012), 
		
		FROZEN("冻结",6013),
		UNFROZEN("解冻",6014),
		
		AGENT_SPLIT_GROFIT("代理商分润支付",6015),
		AGENT_SPLIT_GROFIT_REFUND("代理商分润退款",6016), 
	
		/*7：内部挂账业务相关*/
		ACCOUNTING_HANGING("挂账" ,7001),
		
		DAILY_OFFSET_BALANCE("日终轧差记账",7002),

		MANUAL_ACCOUNTING("手工记账",7011),
	
		/*8：内部销账业务相关*/
		ACCOUNTING_INTO("进款对账",8002),
	 	ACCOUNTING_OUT("出款对账", 8003),
	 	ACCOUNTING_COST("成本记账", 8004),
	 	
	 	FINANCE_SUM("会计汇总专用",8008),	
		
	 	/*9：内部流转业务相关*/
		FUND_TRANSFER("资金调拨",9001),
		
		/*11：调账*/
		MERCHANT_RECON("商户认账",1101),
		BANK_MORE_PLAT_RECON("银行长款平台认账",1102),
		BANK_LESS_PLAT_RECON("银行短款平台认账",1103),
		BANK_MORE_NOT_MATCH_BANK_RECON("银行长款金额不符银行认账",1104),
		CASH_PAY_RECON("现金支付入账",1105);
		
	
	/** 枚举值 */
	private int value;  
	/** 描述 */
	private String desc;

	private EntryTypeEnum(String desc,int value) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static EntryTypeEnum getEnum(int value){
		EntryTypeEnum resultEnum=null;
		EntryTypeEnum[] enumAry=EntryTypeEnum.values();
		for(int i=0;i<enumAry.length;i++){
			if(enumAry[i].getValue()==value){
				resultEnum=enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		EntryTypeEnum[] ary = EntryTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		EntryTypeEnum[] ary = EntryTypeEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 取枚举的json字符串
	 * @return
	 */
	public static String getJsonStr(){
		EntryTypeEnum[] enums = EntryTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (EntryTypeEnum senum : enums) {
			if(!"[".equals(jsonStr.toString())){
				jsonStr.append(",");
			}
			jsonStr.append("{id:'")
					.append(senum)
					.append("',desc:'")
					.append(senum.getDesc())
					.append("',value:'")
					.append(senum.getValue())
					.append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
	

}
