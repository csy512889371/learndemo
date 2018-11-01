package com.ctoedu.business.jpush;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ctoedu.business.activity.HomeActivity;
import com.ctoedu.business.activity.LoginActivity;
import com.ctoedu.business.manager.UserManager;
import com.ctoedu.business.module.PushMessage;
import com.ctoedu.sdk.adutil.ResponseEntityToModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 接收极光服务广播出来的推送消息，实现跳转逻辑
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = JPushReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

            /**
             * 此处可以通过写一个方法，决定出要跳转到那些页面，一些细节的处理，可以通过是不是从推送过来的，去多一个分支去处理。
             * 1.应用未启动,------>启动主页----->不需要登陆信息类型，直接跳转到消息展示页面
             *                         ----->需要登陆信息类型，由于应用都未启动，肯定不存在已经登陆这种情况------>跳转到登陆页面
             *                                                                                                 ----->登陆完毕，跳转到信息展示页面。
             *                                                                                                 ----->取消登陆，返回首页。
             * 2.如果应用已经启动，------>不需要登陆的信息类型，直接跳转到信息展示页面。
             *                 ------>需要登陆的信息类型------>已经登陆----->直接跳转到信息展示页面。
             *                                      ------>未登陆------->则跳转到登陆页面
             *                                                                      ----->登陆完毕，跳转到信息展示页面。
             *                                                                      ----->取消登陆，回到首页。
             *
             * 3.startActivities(Intent[]);在推送中的妙用,注意startActivities在生命周期上的一个细节,
             * 前面的Activity是不会真正创建的，直到要到对应的页面
             * 4.如果为了复用，可以将极光推送封装到一个Manager类中,为外部提供init, setTag, setAlias,
             * setNotificationCustom等一系列常用的方法。
             */
            PushMessage pushMessage = (PushMessage) ResponseEntityToModule
                    .parseJsonToModule(bundle.getString(JPushInterface.EXTRA_EXTRA), PushMessage.class);
            /**
             * 如果应用已经启动(无论前台还是后台)
             */
            if (getCurrentTask(context)) {
                Intent pushIntent = new Intent();
                pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pushIntent.putExtra("pushMessage", pushMessage);
                /**
                 * 需要登陆且当前没有登陆才去登陆页面
                 */
                if (pushMessage.messageType != null && pushMessage.messageType.equals("2")
                        && !UserManager.getInstance().hasLogined()) {
                    pushIntent.setClass(context, LoginActivity.class);
                    pushIntent.putExtra("fromPush", true);
                } else {
                    /**
                     * 不需要登陆或者已经登陆的Case,直接跳转到内容显示页面
                     */
                    pushIntent.setClass(context, PushMessageActivity.class);
                }
                context.startActivity(pushIntent);
                /**
                 * 应用没有启动。。。
                 */
            } else {
                /**
                 * 这里只分了两种类型，如果消息类型很多的话，用switch--case去匹配
                 */
                Intent mainIntent = new Intent(context, HomeActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (pushMessage.messageType != null
                        && pushMessage.messageType.equals("2")) {
                    Intent loginIntent = new Intent();
                    loginIntent.setClass(context, LoginActivity.class);
                    loginIntent.putExtra("fromPush", true);
                    loginIntent.putExtra("pushMessage", pushMessage);
                    context.startActivities(new Intent[]{mainIntent, loginIntent});
                } else {
                    Intent pushIntent = new Intent(context, PushMessageActivity.class);
                    pushIntent.putExtra("pushMessage", pushMessage);
                    context.startActivities(new Intent[]{mainIntent, pushIntent});
                }
            }
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {

                    /**
                     * 先将JSON字符串转化为对象，再取其中的字段
                     */
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" + myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    /**
     * 这个是真正的获取指定包名的应用程序是否在运行(无论前台还是后台)
     *
     * @return
     */
    private boolean getCurrentTask(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> appProcessInfos = activityManager.getRunningTasks(50);
        for (RunningTaskInfo process : appProcessInfos) {

            if (process.baseActivity.getPackageName().equals(context.getPackageName())
                    || process.topActivity.getPackageName().equals(context.getPackageName())) {

                return true;
            }
        }
        return false;
    }
}