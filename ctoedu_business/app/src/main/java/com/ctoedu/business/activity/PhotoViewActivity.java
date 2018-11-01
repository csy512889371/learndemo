package com.ctoedu.business.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctoedu.business.R;
import com.ctoedu.business.activity.base.BaseActivity;
import com.ctoedu.business.adapter.PhotoPagerAdapter;
import com.ctoedu.business.share.ShareDialog;
import com.ctoedu.business.util.Util;
import com.ctoedu.sdk.adutil.Utils;

import java.util.ArrayList;

import cn.sharesdk.framework.Platform;


/**
 * @function 显示产品大图页面
 */
public class PhotoViewActivity extends BaseActivity implements View.OnClickListener {

    public static final String PHOTO_LIST = "photo_list";

    /**
     * UI
     */
    private ViewPager mPager;
    private TextView mIndictorView;
    private ImageView mShareView;
    /**
     * Data
     */
    private PhotoPagerAdapter mAdapter;
    private ArrayList<String> mPhotoLists;
    private int mLenght;
    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_layout);
        initData();
        initView();
    }

    //初始化要显示的图片地址列表
    private void initData() {
        Intent intent = getIntent();
        mPhotoLists = intent.getStringArrayListExtra(PHOTO_LIST);
        mLenght = mPhotoLists.size();
    }

    private void initView() {
        mIndictorView = (TextView) findViewById(R.id.indictor_view);
        mIndictorView.setText("1/" + mLenght);
        mShareView = (ImageView) findViewById(R.id.share_view);
        mShareView.setOnClickListener(this);
        mPager = (ViewPager) findViewById(R.id.photo_pager);
        mAdapter = new PhotoPagerAdapter(this, mPhotoLists, false);
        mPager.setPageMargin(Utils.dip2px(this, 30));
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mIndictorView.setText(String.valueOf((position + 1)).concat("/").
                        concat(String.valueOf(mLenght)));
                currentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Util.hideSoftInputMethod(this, mIndictorView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_view:
                ShareDialog dialog = new ShareDialog(this, true);
                dialog.setShareType(Platform.SHARE_IMAGE);
                dialog.setShareTitle(getString(R.string.ctoedu));
                dialog.setShareTitleUrl(getString(R.string.ctoedu_site));
                dialog.setShareText(getString(R.string.ctoedu));
                dialog.setShareSite(getString(R.string.ctoedu));
                dialog.setShareTitle(getString(R.string.ctoedu));
                dialog.setImagePhoto(mPhotoLists.get(currentPos));
                dialog.setUrl(mPhotoLists.get(currentPos));
                dialog.setResourceUrl(mPhotoLists.get(currentPos));
                dialog.show();
                break;
        }
    }
}
