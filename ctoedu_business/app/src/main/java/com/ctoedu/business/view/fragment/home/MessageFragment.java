package com.ctoedu.business.view.fragment.home;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ctoedu.business.R;
import com.ctoedu.business.activity.LoginActivity;
import com.ctoedu.business.activity.MessageActivity;
import com.ctoedu.business.manager.UserManager;
import com.ctoedu.business.module.mina.MinaModel;
import com.ctoedu.business.network.mina.ConnectionManager;
import com.ctoedu.business.network.mina.MinaReceiver;
import com.ctoedu.business.view.fragment.BaseFragment;
import com.ctoedu.sdk.adutil.ResponseEntityToModule;

/**
 * 消息Fragment
 */
public class MessageFragment extends BaseFragment implements OnClickListener {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mReceiver = new MinaReceiver(new MinaReceiver.MinaListener() {
            @Override
            public void doHandleMessage(Intent intent) {
                handleMessage(intent);
            }
        });
        registerBroadcast();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_message_layout, null, false);
        initView();
        return mContentView;
    }

    private void initView() {
        mMessageLayout = (RelativeLayout) mContentView.findViewById(R.id.message_layout);
        mImoocLayout = (RelativeLayout) mContentView.findViewById(R.id.ctoedu_layout);
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
        unregisterBroadcast();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zan_layout:
                gotoMessageActivity(2);
                break;
            case R.id.message_layout:
                gotoMessageActivity(1);
                break;
            case R.id.ctoedu_layout:
                gotoMessageActivity(3);
                break;
        }
    }

    private void gotoMessageActivity(int type) {
        if (UserManager.getInstance().hasLogined()) {
            Intent intent = new Intent(mContext, MessageActivity.class);
            switch (type) {
                case 1:
                    intent.putExtra(MessageActivity.DATA_LIST, mData.data.msgValues);
                    mTipMsgView.setVisibility(View.GONE);
                    break;
                case 2:
                    intent.putExtra(MessageActivity.DATA_LIST, mData.data.zanValues);
                    mTipZanView.setVisibility(View.GONE);
                    break;
                case 3:
                    intent.putExtra(MessageActivity.DATA_LIST, mData.data.systemValues);
                    mTipView.setVisibility(View.GONE);
                    break;
            }
            intent.putExtra(MessageActivity.TYPE, type);
            startActivity(intent);
        } else {
            //去登陆
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    private void registerBroadcast() {

        IntentFilter filter =
                new IntentFilter(ConnectionManager.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(mReceiver, filter);
    }

    private void unregisterBroadcast() {
        LocalBroadcastManager.getInstance(mContext)
                .unregisterReceiver(mReceiver);
    }

    //真正的处理mina消息
    private void handleMessage(Intent intent) {
        String message = intent.getStringExtra(ConnectionManager.MESSAGE);
        Log.e("MessageFragment", message);
        mData = (MinaModel) ResponseEntityToModule.
                parseJsonToModule(message, MinaModel.class);
        //要改成三个界面都更新
        if (mData != null) {
            if (mData.data.msgValues != null && mData.data.msgValues.size() > 0) {
                mTipMsgView.setVisibility(View.VISIBLE);
                mTipMsgView.setText(String.valueOf(mData.data.msgValues.size()));
            } else {
                mTipMsgView.setVisibility(View.GONE);
            }

            if (mData.data.zanValues != null && mData.data.zanValues.size() > 0) {
                mTipZanView.setVisibility(View.VISIBLE);
                mTipZanView.setText(String.valueOf(mData.data.zanValues.size()));
            } else {
                mTipZanView.setVisibility(View.GONE);
            }

            if (mData.data.systemValues != null && mData.data.systemValues.size() > 0) {
                mTipView.setVisibility(View.VISIBLE);
                mTipView.setText(String.valueOf(mData.data.systemValues.size()));
            } else {
                mTipView.setVisibility(View.GONE);
            }
        }
    }
}
