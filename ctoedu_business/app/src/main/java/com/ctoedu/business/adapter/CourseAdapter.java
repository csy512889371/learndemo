package com.ctoedu.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ctoedu.business.module.recommand.RecommandBodyValue;
import com.ctoedu.business.util.ImageLoaderManager;

import java.util.ArrayList;


/**
 * Course 适配器
 */
public class CourseAdapter extends BaseAdapter {

    /**
     * Common
     */
    private static final int CARD_COUNT = 4;
    private static final int VIDOE_TYPE = 0x00;
    private static final int CARD_TYPE_ONE = 0x01;
    private static final int CARD_TYPE_TWO = 0x02;
    private static final int CARD_TYPE_THREE = 0x03;

    private LayoutInflater mInflate;
    private Context mContext;
    private ArrayList<RecommandBodyValue> mData;
    /*    private ViewHolder mViewHolder;
        private VideoAdContext mAdsdkContext;*/
    private ImageLoaderManager mImagerLoader;


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
