package com.rr.pm.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * FileProvider操作类
 *
 * @author lary.huang
 * @version v 1.4.8 2017/8/8 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class FileProviderUtils {
    /**
     * 兼容android7.0
     *
     * @param context
     * @param file
     * @return
     */
    public static Uri getFileUri(Context context, File file) {
        if (context == null) {
            throw new NullPointerException("context can not be null");
        }
        if (file == null) {
            throw new NullPointerException("file can not be null");
        }
        String authority = context.getPackageName() + ".fileprovider";
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(context, authority, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    /**
     * 安装应用时 兼容android 7.0 及以下设备
     *
     * @param context
     * @param intent
     * @param file
     * @param type
     * @param writeAble
     */
    public static void setDataAndType(Context context, Intent intent, File file, String type, boolean writeAble) {
        if (context == null) {
            throw new NullPointerException("context can not be null");
        }
        if (file == null) {
            throw new NullPointerException("file can not be null");
        }
        if (intent == null) {
            throw new NullPointerException("intent can not be null");
        }
        if (type == null) {
            throw new NullPointerException("type can not be null");
        }

        Uri fileUri = getFileUri(context, file);
        intent.setDataAndType(fileUri, type);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
    }
}
