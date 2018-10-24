package com.ctoedu.business.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateProductService extends Service {
    /**
     * 常量
     */
    private static final int UPDATE_FLAG = 0x01;
    private static final int PRODUCT_FLAG = 0x02;

    /**
     * 请求更新时间戳
     */
    private long updateTime;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
