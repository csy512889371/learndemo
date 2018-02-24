/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.common.core.ex.log4j;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class ExPatternLayout extends PatternLayout {

    public ExPatternLayout(String pattern) {
        super(pattern);
    }

    public ExPatternLayout() {
        super();
    }

    /**
     * 重写createPatternParser方法，返回PatternParser的子类
     */
    @Override
    protected PatternParser createPatternParser(String pattern) {
        return new ExPatternParser(pattern);
    }

}
