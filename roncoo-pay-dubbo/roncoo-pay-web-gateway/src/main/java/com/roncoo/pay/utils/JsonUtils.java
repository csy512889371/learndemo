/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * @功能说明: JsonUtils工具类,用来通过流的方式将Json数据写回前端
 * @创建者: Peter
 * @创建时间: 16/5/20  上午10:45
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class JsonUtils {

    private static final Log LOG = LogFactory.getLog(JsonUtils.class);

    /**
     * 构造函数私有化
     */
    private JsonUtils (){}

    /**
     * 将请求中的Json流转换成Json对象
     * @param httpServletRequest
     * @return
     */
    public static JSONObject requestJson(HttpServletRequest httpServletRequest){
        StringBuffer buffer = new StringBuffer();
        String line = null;
        JSONObject jsonObject = null;
        try {
            BufferedReader reader = httpServletRequest.getReader();
            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch(Exception e) {
            LOG.error(e);
        }
        return jsonObject;
    }


    /**
     * 将Map内的参数,转换成Json实体,并写出
     * @param response
     * @param object
     * @throws IOException
     */
    public static void responseJson(HttpServletResponse response,
                                    Object object) throws IOException {


        Object toJSON = JSONObject.toJSON(object);
        try {
            response.getWriter().write(toJSON.toString());
        } catch (IOException e) {
            LOG.error(e);
        }
    }

}
