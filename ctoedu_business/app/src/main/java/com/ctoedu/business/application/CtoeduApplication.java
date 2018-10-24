package com.ctoedu.business.application;

import android.app.Application;

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
    }

    public static CtoeduApplication getInstance() {
        return mApplication;
    }
}
