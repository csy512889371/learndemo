/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * <b>功能说明:龙果支付属性配置工具类
 * </b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class PayConfigUtil {

    private static final Log LOG = LogFactory.getLog(PayConfigUtil.class);

    /**
     * 通过静态代码块读取上传文件的验证格式配置文件,静态代码块只执行一次(单例)
     */
    private static Properties properties = new Properties();

    private PayConfigUtil() {

    }

    // 通过类装载器装载进来
    static {
        try {
            // 从类路径下读取属性文件
            properties.load(PayConfigUtil.class.getClassLoader()
                    .getResourceAsStream("pay_config.properties"));
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
