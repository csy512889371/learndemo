package com.ctoedu.common.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * Date:2016年11月4日 下午9:59:45
 * Version:1.0
 */
public class ImagesUtils {

    private static final String[] IMAGES_SUFFIXES = {
            "bmp", "jpg", "jpeg", "gif", "png", "tiff"
    };

    /**
     * 是否是图片附件
     *
     * @param filename
     * @return
     */
    public static boolean isImage(String filename) {
        if (filename == null || filename.trim().length() == 0) return false;
        return ArrayUtils.contains(IMAGES_SUFFIXES, FilenameUtils.getExtension(filename).toLowerCase());
    }

}
