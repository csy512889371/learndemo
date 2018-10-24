package com.ctoedu.sdk.okhttp.listener;

/**
 * 监听下载进度
 */
public interface DisposeDownloadListener extends DisposeDataListener {
    public void onProgress(int progrss);
}
