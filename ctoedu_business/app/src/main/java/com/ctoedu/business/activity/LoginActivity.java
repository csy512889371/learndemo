package com.ctoedu.business.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.ctoedu.business.R;
import com.ctoedu.business.activity.base.BaseActivity;
import com.ctoedu.business.jpush.PushMessageActivity;
import com.ctoedu.business.manager.DialogManager;
import com.ctoedu.business.manager.UserManager;
import com.ctoedu.business.module.PushMessage;
import com.ctoedu.business.module.user.User;
import com.ctoedu.business.module.user.UserContent;
import com.ctoedu.business.network.http.RequestCenter;
import com.ctoedu.business.network.mina.MinaService;
import com.ctoedu.business.share.ShareManager;
import com.ctoedu.business.view.associatemail.MailBoxAssociateTokenizer;
import com.ctoedu.business.view.associatemail.MailBoxAssociateView;
import com.ctoedu.sdk.okhttp.listener.DisposeDataListener;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import com.ctoedu.business.share.ShareManager.PlatofrmType;

/**
 * LoginActivity
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    //自定义登陆广播Action
    public static final String LOGIN_ACTION = "com.ctoedu.action.LOGIN_ACTION";
    /**
     * UI
     */
    private MailBoxAssociateView mUserNameAssociateView;
    private EditText mPasswordView;
    private TextView mLoginView;
    private ImageView mQQLoginView; //用来实现QQ登陆

    /**
     * data
     */
    private PushMessage mPushMessage; // 推送过来的消息
    private boolean fromPush; // 是否从推送到此页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        //attachSlidr();
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent.hasExtra("pushMessage")) {
            mPushMessage = (PushMessage) intent.getSerializableExtra("pushMessage");
        }
        fromPush = intent.getBooleanExtra("fromPush", false);
    }

    private void initView() {
        changeStatusBarColor(R.color.white);
        mUserNameAssociateView = (MailBoxAssociateView) findViewById(R.id.associate_email_input);
        mPasswordView = (EditText) findViewById(R.id.login_input_password);
        mLoginView = (TextView) findViewById(R.id.login_button);
        mLoginView.setOnClickListener(this);

        mUserNameAssociateView = (MailBoxAssociateView) findViewById(R.id.associate_email_input);
        String[] recommendMailBox = getResources().getStringArray(R.array.recommend_mailbox);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_associate_mail_list,
                R.id.tv_recommend_mail, recommendMailBox);
        mUserNameAssociateView.setAdapter(adapter);
        mUserNameAssociateView.setTokenizer(new MailBoxAssociateTokenizer());

        mQQLoginView = (ImageView) findViewById(R.id.iv_login_logo);
        mQQLoginView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
                login();
                break;
            case R.id.iv_login_logo:
                //获取第三方用户授权信息
                DialogManager.getInstnce().showProgressDialog(this);

                ShareManager.getInstance().getUserMsg(PlatofrmType.WeChat, new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        DialogManager.getInstnce().dismissProgressDialog();
                        /**
                         * 走到这里以后的处理流程就与第三方平台没有什么关系了，
                         * 完全是自己的业务流程了。
                         */
                        UserContent userContent = new UserContent();
                        userContent.userId = platform.getDb().getUserId();
                        userContent.name = platform.getDb().getUserName();
                        userContent.photoUrl = platform.getDb().getUserIcon();
                        userContent.platform = platform.getDb().getPlatformNname();
                        User user = new User();
                        user.data = userContent;
                        //保存用户信息并通知其他模块登陆成功
                        UserManager.getInstance().setUser(user);
                        connectToSever();
                        sendLoginBroadcast();
                        /**
                         * 还应该将用户信息存入数据库，这样可以保证用户打开应用后总是登陆状态
                         * 只有用户手动退出登陆时候，将用户数据从数据库中删除。
                         */
                        insertUserInfoIntoDB();

                        finish();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        DialogManager.getInstnce().dismissProgressDialog();
                        Log.e(TAG, "onError: " + throwable.getMessage());
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        DialogManager.getInstnce().dismissProgressDialog();
                        Log.e(TAG, "onCancel: " + i);
                    }
                });
                break;
        }
    }

    /**
     * 用户信息存入数据库，以使让用户一打开应用就是一个登陆过的状态
     */
    private void insertUserInfoIntoDB() {
    }

    /**
     * 发送登陆请求
     */
    private void login() {
        String userName = mUserNameAssociateView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            return;
        }

        if (TextUtils.isEmpty(password)) {
            return;
        }

        DialogManager.getInstnce().showProgressDialog(this);

        RequestCenter.login(userName, password, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DialogManager.getInstnce().dismissProgressDialog();

                /**
                 * 这部分可以封装起来，封装为到一个登陆流程类中
                 */
                User user = (User) responseObj;
                UserManager.getInstance().setUser(user);//保存当前用户单例对象
                connectToSever();
                sendLoginBroadcast();
                /**
                 * 还应该将用户信息存入数据库，这样可以保证用户打开应用后总是登陆状态
                 * 只有用户手动退出登陆时候，将用户数据从数据库中删除。
                 */
                insertUserInfoIntoDB();

                if (fromPush) {
                    Intent intent = new Intent(LoginActivity.this, PushMessageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("pushMessage", mPushMessage);
                    startActivity(intent);
                }
                finish();//销毁当前登陆页面
            }

            @Override
            public void onFailure(Object reasonObj) {
                DialogManager.getInstnce().dismissProgressDialog();
            }
        });
    }

    /**
     * 启动长连接
     */
    private void connectToSever() {
        startService(new Intent(LoginActivity.this, MinaService.class));
    }

    /**
     * 向整个应用发送登陆广播事件
     */
    private void sendLoginBroadcast() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOGIN_ACTION));
    }
}
