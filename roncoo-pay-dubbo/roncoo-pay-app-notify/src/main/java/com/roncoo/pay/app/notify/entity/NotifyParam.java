/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.app.notify.entity;

import java.util.Map;

/**
 * @功能说明:
 * @创建者: Peter
 * @创建时间: 16/6/2  下午5:35
 * @公司名称:广州市领课网络科技有限公司 龙果学院(www.roncoo.com)
 * @版本:V1.0 
 */
public class NotifyParam {

    private Map<Integer, Integer> notifyParams;// 通知时间次数map
    private String successValue;// 通知后用于判断是否成功的返回值。由HttpResponse获取

    public Map<Integer, Integer> getNotifyParams() {
        return notifyParams;
    }

    public void setNotifyParams(Map<Integer, Integer> notifyParams) {
        this.notifyParams = notifyParams;
    }

    public String getSuccessValue() {
        return successValue;
    }

    public void setSuccessValue(String successValue) {
        this.successValue = successValue;
    }

    public Integer getMaxNotifyTime() {
        return notifyParams == null ? 0 : notifyParams.size();
    }

}
