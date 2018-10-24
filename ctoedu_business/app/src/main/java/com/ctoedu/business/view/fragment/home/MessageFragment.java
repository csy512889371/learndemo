package com.ctoedu.business.view.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ctoedu.business.R;
import com.ctoedu.business.module.mina.MinaModel;
import com.ctoedu.business.network.mina.MinaReceiver;
import com.ctoedu.business.view.fragment.BaseFragment;


public class MessageFragment extends BaseFragment implements View.OnClickListener {

    /**
     * UI
     */

    private View mContentView;
    private RelativeLayout mMessageLayout;
    private RelativeLayout mZanLayout;
    private RelativeLayout mImoocLayout;
    private TextView mTipView;
    private TextView mTipZanView;
    private TextView mTipMsgView;

    /**
     * 负责处理接收到的mina消息
     */
    private MinaReceiver mReceiver;
    private MinaModel mData;


    public MessageFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_message_layout, null, false);
        initView();
        return mContentView;
    }

    private void initView() {
        mMessageLayout = (RelativeLayout) mContentView.findViewById(R.id.message_layout);
        mImoocLayout = (RelativeLayout) mContentView.findViewById(R.id.imooc_layout);
        mZanLayout = (RelativeLayout) mContentView.findViewById(R.id.zan_layout);
        mTipView = (TextView) mContentView.findViewById(R.id.tip_view);
        mTipZanView = (TextView) mContentView.findViewById(R.id.zan_tip_view);
        mTipMsgView = (TextView) mContentView.findViewById(R.id.unread_tip_view);

        mImoocLayout.setOnClickListener(this);
        mZanLayout.setOnClickListener(this);
        mMessageLayout.setOnClickListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    private void gotoMessageActivity(int type) {


    }

    private void registerBroadcast() {


    }

    private void unregisterBroadcast() {

    }

    //真正的处理mina消息
    private void handleMessage(Intent intent) {


    }
}
