package com.ctoedu.sdk.okhttp;

import com.ctoedu.sdk.module.AdInstance;
import com.ctoedu.sdk.okhttp.listener.DisposeDataHandle;
import com.ctoedu.sdk.okhttp.listener.DisposeDataListener;
import com.ctoedu.sdk.okhttp.request.CommonRequest;

/**
 * sdk请求发送中心
 */
public class RequestCenter {

    /**
     * 发送广告请求
     */
    public static void sendImageAdRequest(String url, DisposeDataListener listener) {

        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, null),
                new DisposeDataHandle(listener, AdInstance.class));
    }
}