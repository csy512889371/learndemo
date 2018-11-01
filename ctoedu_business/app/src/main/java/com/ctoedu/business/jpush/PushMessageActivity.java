package com.ctoedu.business.jpush;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.ctoedu.business.R;
import com.ctoedu.business.activity.base.BaseActivity;
import com.ctoedu.business.module.PushMessage;
import com.ctoedu.sdk.activity.AdBrowserActivity;


/**
 * 显示推送的消息界面
 */
public class PushMessageActivity extends BaseActivity {

    /**
     * UI
     */
    private TextView mTypeView;
    private TextView mTypeValueView;
    private TextView mContentView;
    private TextView mContentValueView;

    /**
     * data
     */
    private PushMessage mPushMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush_layout);
        initData();
        initView();
    }

    //初始化推送过来的数据
    private void initData() {
        Intent intent = getIntent();
        mPushMessage = (PushMessage) intent.getSerializableExtra("pushMessage");
    }

    private void initView() {
        mTypeView = (TextView) findViewById(R.id.message_type_view);
        mTypeValueView = (TextView) findViewById(R.id.message_type_value_view);
        mContentView = (TextView) findViewById(R.id.message_content_view);
        mContentValueView = (TextView) findViewById(R.id.message_content_value_view);

        mTypeValueView.setText(mPushMessage.messageType);
        mContentValueView.setText(mPushMessage.messageContent);
        if (!TextUtils.isEmpty(mPushMessage.messageUrl)) {
            //跳转到web页面
            gotoWebView();
        }
    }

    private void gotoWebView() {
        Intent intent = new Intent(this, AdBrowserActivity.class);
        intent.putExtra(AdBrowserActivity.KEY_URL, mPushMessage.messageUrl);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
