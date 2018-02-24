/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.enums.weixinpay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @功能说明:   微信支付,交易类型枚举类
 * @创建者: Peter
 * @创建时间: 16/5/24  上午10:37
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public enum WeiXinTradeTypeEnum {

    /** JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
     MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口 **/
    JSAPI("公众号支付"),NATIVE("原生扫码支付"),APP("app支付"),MICROPAY("刷卡支付");

    /** 描述 */
    private String desc;

    private WeiXinTradeTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
        WeiXinTradeTypeEnum[] ary = WeiXinTradeTypeEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = ary[num].name();
            map.put("desc", ary[num].getDesc());
            enumMap.put(key, map);
        }
        return enumMap;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List toList() {
        WeiXinTradeTypeEnum[] ary = WeiXinTradeTypeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            list.add(map);
        }
        return list;
    }

    public static WeiXinTradeTypeEnum getEnum(String name) {
        WeiXinTradeTypeEnum[] arry = WeiXinTradeTypeEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].name().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }

    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
        WeiXinTradeTypeEnum[] enums = WeiXinTradeTypeEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (WeiXinTradeTypeEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
