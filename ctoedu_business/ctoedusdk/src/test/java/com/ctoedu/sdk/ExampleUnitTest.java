package com.ctoedu.sdk;

import com.ctoedu.sdk.okhttp.CommonOkHttpClient;
import com.ctoedu.sdk.okhttp.listener.DisposeDataHandle;
import com.ctoedu.sdk.okhttp.listener.DisposeDataListener;
import com.ctoedu.sdk.okhttp.request.CommonRequest;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testCommonHttp() throws InterruptedException {

        /*CommonOkHttpClient.get(CommonRequest.createGetRequest("http://172.16.1.179:3000/mock/7/info", null),
                new DisposeDataHandle(
                        new DisposeDataListener() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                System.out.println(responseObj);
                            }

                            @Override
                            public void onFailure(Object reasonObj) {
                                System.out.println(reasonObj);
                            }
                        }));*/
    }

    @Test
    public void testOkHttp() throws InterruptedException {
        //创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //创建一个Request
        Request request = new Request.Builder().url("http://localhost:3000/mock/7/info").build();

        //new call
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("success");
            }
        });


        Thread.sleep(500000L);
        System.out.println("hhh");
    }
}