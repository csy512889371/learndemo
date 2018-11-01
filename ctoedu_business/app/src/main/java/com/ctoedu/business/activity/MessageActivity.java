package com.ctoedu.business.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ctoedu.business.R;
import com.ctoedu.business.activity.base.BaseActivity;
import com.ctoedu.business.adapter.SystemMsgAdapter;
import com.ctoedu.business.module.mina.MinaMessage;
import com.ctoedu.business.util.Util;
import com.ctoedu.sdk.activity.AdBrowserActivity;

import java.util.ArrayList;

/**
 * 显示接收到的系统消息
 */
public class MessageActivity extends BaseActivity implements OnClickListener {

    public static final String DATA_LIST = "data_list";
    public static final String TYPE = "type";
    public static final int UNREAD = 1;
    public static final int ZAN = 2;
    public static final int IMOOC = 3;
    /**
     * UI
     */
    private TextView mTitleView;
    private ImageView mBackView;
    private ListView mListView;

    /**
     * Data
     */
    private int msgType;
    private ArrayList<MinaMessage> mLists;
    private SystemMsgAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_layout);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        msgType = intent.getIntExtra(TYPE, 3);
        mLists = (ArrayList<MinaMessage>) intent.getSerializableExtra(DATA_LIST);
    }

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mBackView.setOnClickListener(this);
        mTitleView = (TextView) findViewById(R.id.title_view);
        switch (msgType) {
            case UNREAD:
                mTitleView.setText(getString(R.string.liuyan_message));
                break;
            case ZAN:
                mTitleView.setText(getString(R.string.receive_zan_message));
                break;
            case IMOOC:
                mTitleView.setText(getString(R.string.xitong_message));
                break;
        }
        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new SystemMsgAdapter(this, mLists);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MinaMessage mina = (MinaMessage) mAdapter.getItem(position);
                //是系统消息
                if (mina.type == 3) {
                    Intent intent = new Intent(MessageActivity.this, AdBrowserActivity.class);
                    intent.putExtra(AdBrowserActivity.KEY_URL, mina.contentUrl);
                    startActivity(intent);
                } else {
                    //用户的消息
                    try {
                        /**
                         * 找不到此Activity则表明，没有安装QQ
                         */
                        Intent intent = new Intent(Intent.ACTION_VIEW, Util.createQQUrl(mina.qq));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_view:
                finish();
                break;
        }
    }
}
