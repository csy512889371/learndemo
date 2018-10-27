package com.ctoedu.business.network.http;

import com.ctoedu.business.module.course.BaseCourseModel;
import com.ctoedu.business.module.recommand.BaseRecommandModel;
import com.ctoedu.business.module.update.UpdateModel;
import com.ctoedu.business.module.user.User;
import com.ctoedu.sdk.okhttp.CommonOkHttpClient;
import com.ctoedu.sdk.okhttp.listener.DisposeDataHandle;
import com.ctoedu.sdk.okhttp.listener.DisposeDataListener;
import com.ctoedu.sdk.okhttp.listener.DisposeDownloadListener;
import com.ctoedu.sdk.okhttp.request.CommonRequest;
import com.ctoedu.sdk.okhttp.request.RequestParams;

public class RequestCenter {

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 用户登陆请求
     *
     * @param listener 监听器
     * @param userName 用户名
     * @param passwd   密码
     */
    public static void login(String userName, String passwd, DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("mb", userName);
        params.put("pwd", passwd);
        RequestCenter.postRequest(HttpConstants.LOGIN, params, listener, User.class);
    }

    /**
     * 应用版本号请求
     *
     * @param listener 监听器
     */
    public static void checkVersion(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.CHECK_UPDATE, null, listener, UpdateModel.class);
    }

    /**
     * 首页请求: 推荐列表
     *
     * @param listener 监听器
     */
    public static void requestRecommandData(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND, null, listener, BaseRecommandModel.class);
    }

    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(listener, path));
    }

    /**
     * 请求课程详情
     *
     * @param listener 监听器
     */
    public static void requestCourseDetail(String courseId, DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("courseId", courseId);
        RequestCenter.postRequest(HttpConstants.COURSE_DETAIL, params, listener, BaseCourseModel.class);
    }
}