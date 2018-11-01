package com.ctoedu.business.service.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.ctoedu.business.R;

import java.io.File;


public class UpdateService extends Service {
    /**
     * 服务器固定地址
     */
    private static final String APK_URL_TITLE = "http://www.imooc.com/mobile/mukewang.apk";
    /**
     * 文件存放路经
     */
    private String filePath;
    /**
     * 文件下载地址
     */
    private String apkUrl;

    private NotificationManager notificationManager;
    private Notification mNotification;

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        filePath = Environment.getExternalStorageDirectory() + "/imooc/imooc.apk";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        apkUrl = APK_URL_TITLE;
        notifyUser(getString(R.string.update_download_start), getString(R.string.update_download_start), 0);
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        UpdateManager.getInstance().startDownload(apkUrl, filePath, new UpdateDownloadListener() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onProgressChanged(int progress, String downloadUrl) {
                notifyUser(getString(R.string.update_download_processing),
                    getString(R.string.update_download_processing), progress);
            }

            @Override
            public void onPrepared(long contentLength, String downloadUrl) {
            }

            @Override
            public void onPaused(int progress, int completeSize, String downloadUrl) {
                notifyUser(getString(R.string.update_download_failed),
                    getString(R.string.update_download_failed_msg), 0);
                deleteApkFile();
                stopSelf();// 停掉服务自身
            }

            @Override
            public void onFinished(int completeSize, String downloadUrl) {
                notifyUser(getString(R.string.update_download_finish), getString(R.string.update_download_finish),
                    100);
                stopSelf();// 停掉服务自身
                startActivity(getInstallApkIntent());
            }

            @Override
            public void onFailure() {
                notifyUser(getString(R.string.update_download_failed),
                    getString(R.string.update_download_failed_msg), 0);
                deleteApkFile();
                stopSelf();// 停掉服务自身
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void notifyUser(String tickerMsg, String message, int progress) {

        notifyThatExceedLv21(tickerMsg, message, progress);
    }

    private void notifyThatExceedLv21(String tickerMsg, String message, int progress) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.bg_message_imooc);
        notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.bg_message_imooc));
        notification.setContentTitle(getString(R.string.app_name));
        if (progress > 0 && progress < 100) {
            notification.setProgress(100, progress, false);
        } else {
            /**
             * 0,0,false,可以将进度条影藏
             */
            notification.setProgress(0, 0, false);
            notification.setContentText(message);
        }
        notification.setAutoCancel(true);
        notification.setWhen(System.currentTimeMillis());
        notification.setTicker(tickerMsg);
        notification.setContentIntent(progress >= 100 ? getContentIntent() : PendingIntent.getActivity(this, 0,
            new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
        mNotification = notification.build();
        notificationManager.notify(0, mNotification);
    }

    private PendingIntent getContentIntent() {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, getInstallApkIntent(),
            PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

    /**
     * 下载完成，安装
     */
    private Intent getInstallApkIntent() {
        File apkfile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 删除无用apk文件
     */
    private boolean deleteApkFile() {
        File apkFile = new File(filePath);
        if (apkFile.exists() && apkFile.isFile()) {
            return apkFile.delete();
        }
        return false;
    }
}