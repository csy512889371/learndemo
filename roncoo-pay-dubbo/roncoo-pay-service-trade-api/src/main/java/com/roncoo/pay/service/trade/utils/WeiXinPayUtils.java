/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.service.trade.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.roncoo.pay.common.core.utils.StringUtil;
import com.roncoo.pay.service.trade.entity.weixinpay.WeiXinPrePay;
import com.roncoo.pay.service.trade.enums.weixinpay.WeiXinTradeTypeEnum;

/**
 * @功能说明:   微信支付工具类
 * @创建者: Peter
 * @创建时间: 16/5/23  下午6:38
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class WeiXinPayUtils {

    private static final Logger LOG = LoggerFactory.getLogger(WeiXinPayUtils.class);

    /**
     * 发送xml数据,获取返回结果
     * @param requestUrl
     * @param requestMethod
     * @param xmlStr
     * @return
     */
    public static Map<String, Object> httpXmlRequest(String requestUrl, String requestMethod, String xmlStr) {
        // 将解析结果存储在HashMap中
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpsURLConnection urlCon = (HttpsURLConnection) (new URL(requestUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            // 设置请求方式（GET/POST）
            urlCon.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                urlCon.connect();
            }

            urlCon.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes().length));
            urlCon.setUseCaches(false);
            // 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            if (null != xmlStr) {
                OutputStream outputStream = urlCon.getOutputStream();
                outputStream.write(xmlStr.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            }
            InputStream inputStream = urlCon.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStreamReader);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            urlCon.disconnect();
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return map;
    }

    /**
     *  生成预支付XML
     * @param weiXinPrePay
     * @param partnerKey
     * @return
     */
    public static String getPrePayXml(WeiXinPrePay weiXinPrePay, String partnerKey){

        getPrePaySign(weiXinPrePay, partnerKey);//生成预支付请求签名

        StringBuilder sb = new StringBuilder();
        sb.append("<xml><appid>").append(weiXinPrePay.getAppid()).append("</appid>");
        sb.append("<body>").append(weiXinPrePay.getBody()).append("</body>");
        sb.append("<device_info>").append(weiXinPrePay.getDeviceInfo()).append("</device_info>");
        sb.append("<mch_id>").append(weiXinPrePay.getMchId()).append("</mch_id>");
        sb.append("<nonce_str>").append(weiXinPrePay.getNonceStr()).append("</nonce_str>");
        sb.append("<notify_url>").append(weiXinPrePay.getNotifyUrl()).append("</notify_url>");
        if (WeiXinTradeTypeEnum.NATIVE.name().equals(weiXinPrePay.getTradeType())){
            sb.append("<product_id>").append(weiXinPrePay.getProductId()).append("</product_id>");
        }else if (WeiXinTradeTypeEnum.JSAPI.name().equals(weiXinPrePay.getTradeType())){
            sb.append("<openid>").append(weiXinPrePay.getOpenid()).append("</openid>");
        }
        sb.append("<out_trade_no>").append(weiXinPrePay.getOutTradeNo()).append("</out_trade_no>");
        sb.append("<spbill_create_ip>").append(weiXinPrePay.getSpbillCreateIp()).append("</spbill_create_ip>");
        sb.append("<time_start>").append(weiXinPrePay.getTimeStart()).append("</time_start>");
        sb.append("<time_expire>").append(weiXinPrePay.getTimeExpire()).append("</time_expire>");
        sb.append("<total_fee>").append(weiXinPrePay.getTotalFee()).append("</total_fee>");
        sb.append("<trade_type>").append(weiXinPrePay.getTradeType().name()).append("</trade_type>");
        sb.append("<sign>").append(weiXinPrePay.getSign()).append("</sign>");
        sb.append("</xml>");

        return sb.toString();
    }

    /**
     * 微信拼接签名参数
     * @param appid 公众账号ID
     * @param mch_id    商户号
     * @param device_info   设备号
     * @param trade_type    交易类型
     * @param prePay    预支付返回Map
     * @param partnerKey    签名EY
     * @return
     */
    public static String geWeiXintPrePaySign(String appid , String mch_id , String device_info , String trade_type , Map<String, Object> prePay , String partnerKey){
        Map<String, Object> preParams = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(prePay.get("return_code"))) {
            preParams.put("return_code", prePay.get("return_code"));
        }
        if (!StringUtil.isEmpty(prePay.get("return_msg"))) {
            preParams.put("return_msg", prePay.get("return_msg"));
        }
        if (!StringUtil.isEmpty(prePay.get("appid"))) {
            preParams.put("appid", appid);
        }
        if (!StringUtil.isEmpty(prePay.get("mch_id"))) {
            preParams.put("mch_id", mch_id);
        }
        if (!StringUtil.isEmpty(prePay.get("device_info"))) {
            preParams.put("device_info", device_info);
        }
        if (!StringUtil.isEmpty(prePay.get("nonce_str"))) {
            preParams.put("nonce_str", prePay.get("nonce_str"));
        }
        if (!StringUtil.isEmpty(prePay.get("result_code"))) {
            preParams.put("result_code", prePay.get("result_code"));
        }
        if ("FAIL".equals(prePay.get("result_code"))) {
            if (!StringUtil.isEmpty(prePay.get("err_code"))) {
                preParams.put("err_code", prePay.get("err_code"));
            }
            if (!StringUtil.isEmpty(prePay.get("err_code_des"))) {
                preParams.put("err_code_des", prePay.get("err_code_des"));
            }
        }
        if (!StringUtil.isEmpty(prePay.get("trade_type"))) {
            preParams.put("trade_type", trade_type);
        }
        if (!StringUtil.isEmpty(prePay.get("prepay_id"))) {
            preParams.put("prepay_id", prePay.get("prepay_id"));
        }
        if (!StringUtil.isEmpty(prePay.get("code_url"))) {
            preParams.put("code_url", prePay.get("code_url"));
        }
        String argPreSign = getStringByMap(preParams) + "&key=" + partnerKey;
        String preSign = MD5Util.encode(argPreSign).toUpperCase();
        return preSign;
    }



    public static boolean notifySign(Map<String, String> result , String sign , String  partnerKey) {
        String argNotifySign = getStringByStringMap(result) + "&key=" + partnerKey;
        String notifySign = MD5Util.encode(argNotifySign).toUpperCase();
        if (notifySign.equals(sign)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 获取预支付请求签名
     * @param weiXinPrePay
     * @param partnerKey
     * @return
     */
    private static void getPrePaySign(WeiXinPrePay weiXinPrePay,String partnerKey){

        Map<String, Object> prePayMap = new HashMap<String, Object>();
        prePayMap.put("appid", weiXinPrePay.getAppid());// 公众账号ID
        prePayMap.put("mch_id", weiXinPrePay.getMchId()); // 商户号
        prePayMap.put("device_info", weiXinPrePay.getDeviceInfo());
        prePayMap.put("nonce_str", weiXinPrePay.getNonceStr()); // 随机字符串
        prePayMap.put("body", weiXinPrePay.getBody()); // 商品描述
        prePayMap.put("out_trade_no", weiXinPrePay.getOutTradeNo()); // 商户订单号
        prePayMap.put("total_fee", weiXinPrePay.getTotalFee()); // 总金额
        prePayMap.put("spbill_create_ip", weiXinPrePay.getSpbillCreateIp()); // 终端IP
        prePayMap.put("time_start", weiXinPrePay.getTimeStart()); // 开始时间
        prePayMap.put("time_expire", weiXinPrePay.getTimeExpire()); // 截止时间
        prePayMap.put("notify_url", weiXinPrePay.getNotifyUrl()); // 接收财付通通知的URL
        prePayMap.put("trade_type", weiXinPrePay.getTradeType().name()); // 交易类型
        if (WeiXinTradeTypeEnum.NATIVE.name().equals(weiXinPrePay.getTradeType())){
            prePayMap.put("product_id", weiXinPrePay.getProductId()); //商品ID
        }else if (WeiXinTradeTypeEnum.JSAPI.name().equals(weiXinPrePay.getTradeType())){
            prePayMap.put("openid", weiXinPrePay.getOpenid()); // openid
        }

        String argPreSign = getStringByMap(prePayMap) + "&key=" + partnerKey;
        String preSign = MD5Util.encode(argPreSign).toUpperCase();
        weiXinPrePay.setSign(preSign);
    }


    /**
     * 根据Map获取排序拼接后的字符串
     * @param map
     * @return
     */
    public static String getStringByMap(Map<String, Object> map) {
        SortedMap<String, Object> smap = new TreeMap<String, Object>(map);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            sb.append(m.getKey()).append("=").append(m.getValue()).append("&");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public static String getStringByStringMap(Map<String, String> map) {
        SortedMap<String, Object> smap = new TreeMap<String, Object>(map);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            sb.append(m.getKey()).append("=").append(m.getValue()).append("&");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }


    /**
     * 解析微信发来的请求（XML）
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(InputStream inputStream) throws Exception {

        if (inputStream == null){
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();// 将解析结果存储在HashMap中
        SAXReader reader = new SAXReader();// 读取输入流
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();// 得到xml根元素
        List<Element> elementList = root.elements();// 得到根元素的所有子节点
        for (Element e : elementList) {        // 遍历所有子节点
            map.put(e.getName(), e.getText());
        }

        inputStream.close();        // 释放资源
        inputStream = null;

        return map;
    }
}
