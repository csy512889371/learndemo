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
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/5/24  下午3:01
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0
 */
public class WeixinConfigUtil {

    private static final Log LOG = LogFactory.getLog(WeixinConfigUtil.class);

    /**
     * 通过静态代码块读取上传文件的验证格式配置文件,静态代码块只执行一次(单例)
     */
    private static Properties properties = new Properties();

    private WeixinConfigUtil() {

    }

    // 通过类装载器装载进来
    static {
        try {
            // 从类路径下读取属性文件
            properties.load(WeixinConfigUtil.class.getClassLoader()
                    .getResourceAsStream("weixinpay_config.properties"));
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    /**
     * 函数功能说明 ：读取配置项 Administrator 2012-12-14 修改者名字 ： 修改日期 ： 修改内容 ：
     *
     * @参数：
     * @return void
     * @throws
     */
    public static String readConfig(String key) {
        return (String) properties.get(key);
    }
}
