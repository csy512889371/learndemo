/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/1  上午11:37
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class MerchantApiUtil {


    /**
     * 获取参数签名
     * @param paramMap  签名参数
     * @param paySecret 签名密钥
     * @return
     */
    public static String  getSign (Map<String , Object> paramMap , String paySecret){
        SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            Object value = m.getValue();
            if (value != null && StringUtils.isNotBlank(String.valueOf(value))){
                stringBuffer.append(m.getKey()).append("=").append(value).append("&");
            }
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());

        String argPreSign = stringBuffer.append("&paySecret=").append(paySecret).toString();
        String signStr = MD5Util.encode(argPreSign).toUpperCase();

        return signStr;
    }

    /**
     * 获取参数拼接串
     * @param paramMap
     * @return
     */
    public static String  getParamStr(Map<String , Object> paramMap){
        SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            Object value = m.getValue();
            if (value != null && StringUtils.isNotBlank(String.valueOf(value))){
                stringBuffer.append(m.getKey()).append("=").append(value).append("&");
            }
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());

        return stringBuffer.toString();
    }

    /**
     * 验证商户签名
     * @param paramMap  签名参数
     * @param paySecret 签名私钥
     * @param signStr   原始签名密文
     * @return
     */
    public static boolean isRightSign(Map<String , Object> paramMap , String paySecret ,String signStr){

        if (StringUtils.isBlank(signStr)){
            return false;
        }

        String sign = getSign(paramMap, paySecret);
        if(signStr.equals(sign)){
            return true;
        }else{
            return false;
        }
    }


}