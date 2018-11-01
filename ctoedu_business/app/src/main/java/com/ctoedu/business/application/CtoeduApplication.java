package com.ctoedu.business.application;

import android.app.Application;

import com.ctoedu.business.share.ShareManager;
import com.ctoedu.sdk.core.AdSDKManager;

import cn.jpush.android.api.JPushInterface;

/**
 * 1、整个程序入口
 * 2、初始化工作
 * 3、为整个应用的其他模块提供上下文
 */
public class CtoeduApplication extends Application {

    private static CtoeduApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initShareSDK();
        //initJPush();
        //initAdSDK();
    }

    public static CtoeduApplication getInstance() {
        return mApplication;
    }

    public void initShareSDK() {
        ShareManager.initSDK(this);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initAdSDK() {
        AdSDKManager.init(this);
    }
}
