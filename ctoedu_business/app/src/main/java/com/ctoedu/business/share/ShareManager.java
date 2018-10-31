package com.ctoedu.business.share;

import android.content.Context;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author vision
 * @function 分享功能统一入口，负责发送数据到指定平台,可以优化为一个单例模式
 */

public class ShareManager {

    private static ShareManager mShareManager = null;
    /**
     * 要分享到的平台
     */
    private Platform mCurrentPlatform;

    /**
     * 线程安全的单例模式
     */
    public static ShareManager getInstance() {
        if (mShareManager == null) {
            synchronized (ShareManager.class) {
                if (mShareManager == null) {
                    mShareManager = new ShareManager();
                }
            }
        }
        return mShareManager;
    }

    private ShareManager() {
    }

    /**
     * 第一个执行的方法,最好在程序入口入执行
     *
     * @param context
     */
    public static void initSDK(Context context) {
        ShareSDK.initSDK(context);
    }

    /**
     * 分享数据到不同平台
     */
    public void shareData(ShareData shareData, PlatformActionListener listener) {
        switch (shareData.mPlatformType) {
            case QQ:
                mCurrentPlatform = ShareSDK.getPlatform(QQ.NAME);
                break;
            case QZone:
                mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME);
                break;
            case WeChat:
                mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME);
                break;
            case WechatMoments:
                mCurrentPlatform = ShareSDK.getPlatform(WechatMoments.NAME);
                break;
            default:
                break;
        }
        mCurrentPlatform.setPlatformActionListener(listener); //由应用层去处理回调,分享平台不关心。
        mCurrentPlatform.share(shareData.mShareParams);
    }

    /**
     * 第三方用户授权信息获取
     *                                           将获取到的用户信息发送到服务器                                是
     * 登陆流程：通过Aouth认证拿到第三方平台用户信息--------------------------------> 服务器判断是否绑定过本地帐号------->  返回本地系统帐号信息登陆应用
     *
     *                                                                                                    否
     *                                                                                                  ------->  直接用第三方信息登陆应用(应用内可以提供绑定功能)
     * 注意：1.第三方帐号与本地帐号的绑定关系是一一对应的(即一个QQ号绑定了本地帐号以后,同一用户的微信号也不能再绑定)。
     *      2.绑定的过程其实就是一个使用第三方平台用户信息再自己服务器的一个再注册功能。
     *
     * @param type     第三方类型
     * @param listener 事件回调处理
     */
    public void getUserMsg(PlatofrmType type, PlatformActionListener listener) {
        switch (type) {
            case QQ:
            case QZone:
                /**
                 * 没有微信那么严格，测试环境下也可以正常登陆
                 */
                mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME);
                break;
            case WeChat:
                /**
                 * 微信第三方登陆条件：1.应用必须通过微信平台审核 2.开通了登陆功能(300一年) 3.测试的时候需要以签名包测试
                 * 必须同时满足以上三个条件才能登陆成功。
                 */
                mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME);
                break;
        }
        mCurrentPlatform.setPlatformActionListener(listener); //用户信息回调接口
        mCurrentPlatform.showUser(null); // 请求对应用户信息
    }

    /**
     * 删除授权
     */
    public void removeAccount() {
        if (mCurrentPlatform != null) {
            mCurrentPlatform.getDb().removeAccount();
        }
    }

    /**
     * @author 应用程序需要的平台
     */
    public enum PlatofrmType {
        QQ, QZone, WeChat, WechatMoments;
    }
}