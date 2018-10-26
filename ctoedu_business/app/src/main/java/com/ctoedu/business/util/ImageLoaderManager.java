package com.ctoedu.business.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.ctoedu.business.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 初始化UniverImageLoader, 并用来加载网络图片.
 */

public class ImageLoaderManager {

    /**
     * 最多线程数
     */
    private static final int THREAD_COUNT = 4;

    /**
     * 图片加载优先级
     */
    private static final int PRIORITY = 2;

    /**
     * 内存图片缓存大小 2M
     */
    private static final int MEMORY_CACHE_SIZE = 2 * 1024 * 1024;

    /**
     * 磁盘图片缓存大小 50M
     */
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;

    /**
     * 连接超时时间
     */
    private static final int CONNECTION_TIME_OUT = 5 * 1000;

    /**
     * 读取超时时间
     */
    private static final int READ_TIME_OUT = 30 * 1000;

    private static ImageLoaderManager mInstance = null;
    private static ImageLoader mLoader = null;


    public static ImageLoaderManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ImageLoaderManager.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 私有构造方法完成初始化工作
     *
     * @param context
     */
    private ImageLoaderManager(Context context) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPoolSize(THREAD_COUNT)// 配置图片下载线程的最大数量
                .threadPriority(Thread.NORM_PRIORITY - PRIORITY)
                .denyCacheImageMultipleSizesInMemory()//防止缓存多尺寸的图片到我们的内存中
                //.memoryCache(new UsingFreqLimitedMemoryCache(MEMORY_CACHE_SIZE))
                .memoryCache(new WeakMemoryCache())//使用弱引用内存缓存
                .diskCacheSize(DISK_CACHE_SIZE)//分配硬盘缓存大小
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)//图片下载顺序
                .defaultDisplayImageOptions(getDefaultOptions())//默认的图片加载options
                .imageDownloader(new BaseImageDownloader(context, CONNECTION_TIME_OUT, READ_TIME_OUT))//设置图片下载器
                .writeDebugLogs()//debug环境会输出日志
                .build();

        ImageLoader.getInstance().init(config);
        mLoader = ImageLoader.getInstance();
    }

    //load the image
    public void displayImage(ImageView imageView, String path, ImageLoadingListener listener) {
        if (mLoader != null) {
            mLoader.displayImage(path, imageView, listener);
        }
    }

    public void displayImage(ImageView imageView, String path) {
        displayImage(imageView, path, null);
    }

    /**
     * 默认的图片显示Options,可设置图片的缓存策略，编解码方式等，非常重要
     *
     * @return
     */
    private DisplayImageOptions getDefaultOptions() {

        DisplayImageOptions options = new
                DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.default_user_avatar)//图片地址为空的时候显示的图片
                .showImageOnFail(R.drawable.default_user_avatar)//图片下载失败的时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中, 重要，否则图片不会缓存到内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中, 重要，否则图片不会缓存到硬盘中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .decodingOptions(new BitmapFactory.Options())//设置图片的解码配置
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .build();
        return options;
    }
}