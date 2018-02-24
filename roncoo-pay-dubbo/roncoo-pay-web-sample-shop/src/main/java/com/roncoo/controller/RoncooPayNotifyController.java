/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b>功能说明:龙果支付控制类
 * </b>
 * @author  Peter
 * <a href="http://www.roncoo.com">龙果学院(www.roncoo.com)</a>
 */
@Controller
@RequestMapping(value = "/roncooPayNotify")
public class RoncooPayNotifyController  extends BaseController{
	
	private static final Logger LOG = LoggerFactory.getLogger(RoncooPayNotifyController.class);
	
    @RequestMapping("/notify")
    public String scanPay(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse ){
        Map requestMap = getParamMap_NullStr(httpServletRequest.getParameterMap());
        LOG.info("银行返回结果：{}",requestMap);
        
        try {
			httpServletResponse.getWriter().print("success");
		} catch (IOException e) {
			LOG.error("回写失败：",e);
		}
        return null ;
    }
    
}
