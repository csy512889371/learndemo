/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.common.core.dwz;


/**
 * 
 * @类功能说明：封装DWZ框架ajax请求及响应的参数. .
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院:www.roncoo.com）
 * @作者：Along.shen
 * @创建时间：2016年5月31日,下午6:16:02.
 * @版本：V1.0
 *
 */
public class DwzAjax {
    /**
     * Ajax请求的执行状态码.<br/>
     * statusCode:{ok:200, error:300, timeout:301}.<br/>
     * 200：成功，300：错误，301:请求超时.
     */
    private String statusCode;
    /**
     * Ajax提示消息.<br/>
     * message.
     */
    private String message;
    /**
     * navTabId. 服务器传回navTabId可以把那个navTab标记为reloadFlag=1.<br/>
     * 下次切换到那个navTab时会重新载入内容.
     */
    private String navTabId;
    /**
     * callbackType ajax请求回调类型. <br/>
     * callbackType如果是closeCurrent就会关闭当前tab选项,
     * 只有callbackType="forward"时需要forwardUrl值,以重定向到另一个URL.
     */
    private String callbackType;
    /**
     * 重定向URL. <br/>
     * forwardUrl.
     */
    private String forwardUrl;

    /**
     * @return the statusCode
     */
    public final String getStatusCode() {
        return statusCode;
    }

    /**
     * @param argStatusCode
     *            the statusCode to set
     */
    public final void setStatusCode(final String argStatusCode) {
        this.statusCode = argStatusCode;
    }

    /**
     * @return the message
     */
    public final String getMessage() {
        return message;
    }

    /**
     * @param argMessage
     *            the message to set
     */
    public final void setMessage(final String argMessage) {
        this.message = argMessage;
    }

    /**
     * @return the navTabId
     */
    public final String getNavTabId() {
        return navTabId;
    }

    /**
     * @param argNavTabId
     *            the navTabId to set
     */
    public final void setNavTabId(final String argNavTabId) {
        this.navTabId = argNavTabId;
    }

    /**
     * @return the callbackType
     */
    public final String getCallbackType() {
        return callbackType;
    }

    /**
     * @param argCallbackType
     *            the callbackType to set
     */
    public final void setCallbackType(final String argCallbackType) {
        this.callbackType = argCallbackType;
    }

    /**
     * @return the forwardUrl
     */
    public final String getForwardUrl() {
        return forwardUrl;
    }

    /**
     * @param argForwardUrl
     *            the forwardUrl to set
     */
    public final void setForwardUrl(final String argForwardUrl) {
        this.forwardUrl = argForwardUrl;
    }

}
