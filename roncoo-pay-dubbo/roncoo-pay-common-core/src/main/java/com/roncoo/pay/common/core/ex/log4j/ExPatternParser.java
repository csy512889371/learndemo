/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.common.core.ex.log4j;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

/**
 * <b>功能说明:
 * </b>
 *
 * @author Peter
 *         <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
public class ExPatternParser extends PatternParser{

    public ExPatternParser(String pattern) {
        super(pattern);
    }

    /**
     * 重写finalizeConverter，对特定的占位符进行处理，T表示线程ID占位符
     */
    @Override
    protected void finalizeConverter(char c) {
        if (c == 'T') {
            this.addConverter(new ExPatternConverter(this.formattingInfo));
        } else {
            super.finalizeConverter(c);
        }
    }

    private static class ExPatternConverter extends PatternConverter {

        public ExPatternConverter(FormattingInfo fi) {
            super(fi);
        }

        /**
         * 当需要显示线程ID的时候，返回当前调用线程的ID
         */
        @Override
        protected String convert(LoggingEvent event) {
            return String.valueOf(Thread.currentThread().getId());
        }

    }

}
