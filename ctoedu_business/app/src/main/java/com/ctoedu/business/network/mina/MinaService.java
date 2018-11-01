package com.ctoedu.business.network.mina;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;

/**
 * 通过Mina与后台服务器保持一个长连接，
 * 用来接收服务器主动发送的消息
 */
public class MinaService extends Service {

    private ConnectThread mConection;

    @Override
    public void onCreate() {
        super.onCreate();

        mConection = new ConnectThread("mina", getApplicationContext());
        mConection.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mConection.disConnection();
        mConection = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class ConnectThread extends HandlerThread {

        boolean isConnect;
        ConnectionManager connectionManager;

        ConnectThread(String name, Context context) {
            super(name);
            ConnectionConfig config = new ConnectionConfig.Builder(context)
                    .setIP("192.168.1.103")
                    .setPort(9124)
                    .setReadBufferSize(10240)
                    .builder();
            connectionManager = new ConnectionManager(config);
        }

        @Override
        protected void onLooperPrepared() {

            for (; ; ) {
                isConnect = connectionManager.connect();
                if (isConnect) {
                    break;
                }
                try {
                    //每隔三秒去连接一次直到成功
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void disConnection() {
            connectionManager.disConnection();
        }
    }
}
