package com.ctoedu.business.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ctoedu.business.R;
import com.ctoedu.business.activity.base.BaseActivity;
import com.ctoedu.business.adapter.CourseCommentAdapter;
import com.ctoedu.business.manager.UserManager;
import com.ctoedu.business.module.course.BaseCourseModel;
import com.ctoedu.business.module.course.CourseCommentValue;
import com.ctoedu.business.module.user.User;
import com.ctoedu.business.network.http.RequestCenter;
import com.ctoedu.business.util.Util;
import com.ctoedu.business.view.course.CourseDetailFooterView;
import com.ctoedu.business.view.course.CourseDetailHeaderView;
import com.ctoedu.sdk.okhttp.listener.DisposeDataListener;


/**
 * 课程详情Activity, 展示课程详情,这个页面要用signalTop模式
 */
public class CourseDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static String COURSE_ID = "courseID";

    /**
     * UI
     */
    private ImageView mBackView;
    private ListView mListView;
    private ImageView mLoadingView;
    private RelativeLayout mBottomLayout;
    private ImageView mJianPanView;
    private EditText mInputEditView;
    private TextView mSendView;
    private CourseDetailHeaderView headerView;
    private CourseDetailFooterView footerView;
    private CourseCommentAdapter mAdapter;
    /**
     * Data
     */
    private String mCourseID;
    private BaseCourseModel mData;
    private String tempHint = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_layout);
        initData();
        initView();
        requestDeatil();
    }

    /**
     * 复用Activity时走的生命周期回调
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
        initView();
        requestDeatil();
    }

    //初始化数据
    private void initData() {
        Intent intent = getIntent();
        mCourseID = intent.getStringExtra(COURSE_ID);
    }

    //初始化数据
    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mBackView.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.comment_list_view);
        //mListView.setOnItemClickListener(this);
        mListView.setVisibility(View.GONE);
        mLoadingView = (ImageView) findViewById(R.id.loading_view);
        mLoadingView.setVisibility(View.VISIBLE);
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();

        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mJianPanView = (ImageView) findViewById(R.id.jianpan_view);
        mJianPanView.setOnClickListener(this);
        mInputEditView = (EditText) findViewById(R.id.comment_edit_view);
        mSendView = (TextView) findViewById(R.id.send_view);
        mSendView.setOnClickListener(this);
        mBottomLayout.setVisibility(View.GONE);

        intoEmptyState();
    }

    private void requestDeatil() {

        RequestCenter.requestCourseDetail(mCourseID, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mData = (BaseCourseModel) responseObj;
                updateUI();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    /**
     * 根据数据填充UI
     */
    private void updateUI() {
        mLoadingView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        mAdapter = new CourseCommentAdapter(this, mData.data.body);
        mListView.setAdapter(mAdapter);

        //防止headerView多次加载
        if (headerView != null) {
            mListView.removeHeaderView(headerView);
        }
        headerView = new CourseDetailHeaderView(this, mData.data.head);
        mListView.addHeaderView(headerView);
        if (footerView != null) {
            mListView.removeFooterView(footerView);
        }
        footerView = new CourseDetailFooterView(this, mData.data.footer);
        mListView.addFooterView(footerView);

        mBottomLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int cursor = position - mListView.getHeaderViewsCount();
        if (cursor >= 0 && cursor < mAdapter.getCommentCount()) {
            if (UserManager.getInstance().hasLogined()) {
                CourseCommentValue value = (CourseCommentValue) mAdapter.getItem(
                        position - mListView.getHeaderViewsCount());
                if (value.userId.equals(UserManager.getInstance().getUser().data.userId)) {
                    //自己的评论不能回复
                    intoEmptyState();
                    Toast.makeText(this, "不能回复自己!", Toast.LENGTH_SHORT).show();
                } else {
                    //不是自己的评论，可以回复
                    tempHint = getString(R.string.comment_hint_head).concat(value.name).
                            concat(getString(R.string.comment_hint_footer));
                    intoEditState(tempHint);
                }
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
    }

    /**
     * EditText进入编辑状态
     */
    private void intoEditState(String hint) {
        mInputEditView.requestFocus();
        mInputEditView.setHint(hint);
        Util.showSoftInputMethod(this, mInputEditView);
    }

    public void intoEmptyState() {
        tempHint = "";
        mInputEditView.setText("");
        mInputEditView.setHint(getString(R.string.input_comment));
        Util.hideSoftInputMethod(this, mInputEditView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_view:
                finish();
                break;
            case R.id.send_view:
                String comment = mInputEditView.getText().toString().trim();
                if (UserManager.getInstance().hasLogined()) {
                    if (!TextUtils.isEmpty(comment)) {
                        mAdapter.addComment(assembleCommentValue(comment));
                        intoEmptyState();
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;
            case R.id.jianpan_view:
                mInputEditView.requestFocus();
                Util.showSoftInputMethod(this, mInputEditView);
                break;
        }
    }

    /**
     * 组装CommentValue对象
     *
     * @return
     */
    private CourseCommentValue assembleCommentValue(String comment) {
        User user = UserManager.getInstance().getUser();
        CourseCommentValue value = new CourseCommentValue();
        value.name = user.data.name;
        value.logo = user.data.photoUrl;
        value.userId = user.data.userId;
        value.type = 1;
        value.text = tempHint + comment;
        return value;
    }
}
