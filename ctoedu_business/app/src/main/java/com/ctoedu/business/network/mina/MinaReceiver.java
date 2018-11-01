package com.ctoedu.business.network.mina;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MinaReceiver extends BroadcastReceiver {

    private MinaListener mListener;

    public MinaReceiver(MinaListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mListener != null) {
            mListener.doHandleMessage(intent);
        }
    }

    /**
     * 真正的广告事件处理回调
     */
    public interface MinaListener {
        void doHandleMessage(Intent intent);
    }
}
