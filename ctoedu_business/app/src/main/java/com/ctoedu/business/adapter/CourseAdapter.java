package com.ctoedu.business.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ctoedu.business.R;
import com.ctoedu.business.activity.PhotoViewActivity;
import com.ctoedu.business.module.recommand.RecommandBodyValue;
import com.ctoedu.business.util.ImageLoaderManager;
import com.ctoedu.business.util.Util;
import com.ctoedu.sdk.adutil.Utils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Course 课程 适配器
 */
public class CourseAdapter extends BaseAdapter {
    /**
     * ListView 不同类型的Item标识
     * 1: 单一图片的Item
     * 2: 多图片类型的Item
     * 3: ViewPager 类型的Item
     * 4: 视频类型的Item
     */
    private static final int CARD_COUNT = 4;

    /**
     * 视频类型的Item
     */
    private static final int VIDOE_TYPE = 0x00;

    /**
     * 单一图片的Item
     */
    private static final int CARD_SINGLE_PIC = 0x02;

    /**
     * 多图片类型的Item
     */
    private static final int CARD_MULTI_PIC = 0x01;

    /**
     * ViewPager 类型的Item
     */
    private static final int CARD_VIEW_PAGER = 0x03;

    private LayoutInflater mInflate;
    private Context mContext;
    private ArrayList<RecommandBodyValue> mData;
    private ViewHolder mViewHolder;

    /**
     * 异步图片加载工具
     */
    private ImageLoaderManager mImagerLoader;

    /**
     * 构造方法
     *
     * @param context context
     * @param data    数据
     */
    public CourseAdapter(Context context, ArrayList<RecommandBodyValue> data) {
        mContext = context;
        mData = data;
        mInflate = LayoutInflater.from(mContext);
        mImagerLoader = ImageLoaderManager.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return CARD_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        RecommandBodyValue value = (RecommandBodyValue) getItem(position);
        return value.type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1. 获取数据的type类型
        int type = getItemViewType(position);
        final RecommandBodyValue value = (RecommandBodyValue) getItem(position);
        //为空时表明当前没有使用的缓存View
        if (convertView == null) {
            switch (type) {
                case CARD_SINGLE_PIC:
                    mViewHolder = new ViewHolder();
                    convertView = mInflate.inflate(R.layout.item_product_card_two_layout, parent, false);
                    mViewHolder.mLogoView = convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mProductView = (ImageView) convertView.findViewById(R.id.product_photo_view);
                    mViewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
                    mViewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
                    mViewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
                    break;
                case CARD_MULTI_PIC:
                    mViewHolder = new ViewHolder();
                    convertView = mInflate.inflate(R.layout.item_product_card_one_layout, parent, false);
                    //初始化 viewHolder 中所用到的组件
                    mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
                    mViewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
                    mViewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
                    mViewHolder.mProductLayout = (LinearLayout) convertView.findViewById(R.id.product_photo_layout);
                    break;
                case CARD_VIEW_PAGER:
                    mViewHolder = new ViewHolder();
                    convertView = mInflate.inflate(R.layout.item_product_card_three_layout, null, false);
                    mViewHolder.mViewPager = convertView.findViewById(R.id.pager);

                    //将@分割的数据处理成ArrayList
                    ArrayList<RecommandBodyValue> recommandList = Util.handleData(value);

                    // 设置间隔
                    mViewHolder.mViewPager.setPageMargin(Utils.dip2px(mContext, 12));
                    mViewHolder.mViewPager.setAdapter(new HotSalePagerAdapter(mContext, recommandList));
                    //一开始让pagerView 处在一个比较中间的Item 这样可以双向滑动
                    mViewHolder.mViewPager.setCurrentItem(recommandList.size() * 100);
                    break;
            }
            convertView.setTag(mViewHolder);
        }//有可用的ConvertView
        else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //填充数据
        switch (type) {
            case CARD_SINGLE_PIC:
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(mContext.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mPriceView.setText(value.price);
                mViewHolder.mFromView.setText(value.from);
                mViewHolder.mZanView.setText(mContext.getString(R.string.dian_zan).concat(value.zan));
                //为单个ImageView加载远程图片
                mImagerLoader.displayImage(mViewHolder.mProductView, value.url.get(0));
                break;
            case CARD_MULTI_PIC:
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(mContext.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mPriceView.setText(value.price);
                mViewHolder.mFromView.setText(value.from);
                mViewHolder.mZanView.setText(mContext.getString(R.string.dian_zan).concat(value.zan));
                mViewHolder.mProductLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PhotoViewActivity.class);
                        intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST, value.url);
                        mContext.startActivity(intent);
                    }
                });
                mViewHolder.mProductLayout.removeAllViews();

                //动态添加多个imageview
                for (String url : value.url) {
                    mViewHolder.mProductLayout.addView(createImageView(url));
                }
                break;
            case CARD_VIEW_PAGER:
                break;
        }
        return convertView;
    }

    /**
     * 自动播放方法
     */
    public void updateAdInScrollView() {

    }

    /**
     * 动态添加ImageView
     */
    private ImageView createImageView(String url) {
        ImageView photoView = new ImageView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(Utils.dip2px(mContext, 100),
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.leftMargin = Utils.dip2px(mContext, 5);
        photoView.setLayoutParams(params);
        mImagerLoader.displayImage(photoView, url);
        return photoView;
    }

    /**
     * 用来缓存已经创建好的Item
     */
    private static class ViewHolder {
        //所有Card共有属性
        private CircleImageView mLogoView;
        private TextView mTitleView;
        private TextView mInfoView;
        private TextView mFooterView;
        //Video Card特有属性
        private RelativeLayout mVieoContentLayout;
        private ImageView mShareView;

        //Video Card外所有Card具有属性
        private TextView mPriceView;
        private TextView mFromView;
        private TextView mZanView;
        //Card One特有属性
        private LinearLayout mProductLayout;
        //Card Two特有属性
        private ImageView mProductView;
        //Card Three特有属性
        private ViewPager mViewPager;
    }
}
