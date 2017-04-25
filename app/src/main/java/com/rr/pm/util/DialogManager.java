package com.rr.pm.util;

import android.content.Context;
import android.graphics.Color;

import com.rr.pm.base.BaseActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Dialog管理工具
 *
 * @author huangyang
 * @version v 1.4.8 2017/3/9 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class DialogManager {
    private static SweetAlertDialog sweetAlertDialog;

    /**
     * loading dialog显示状态 true,false
     *
     * @return
     */
    public static boolean isShow() {
        if (sweetAlertDialog == null) {
            return false;
        }
        return sweetAlertDialog.isShowing();
    }

    /**
     * loading dialog
     *
     * @param context
     * @param titleText
     */
    public static void showProgressDialog(Context context, String titleText) {
        if (context == null) return;
        beforeShowDialog();
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog.setTitleText(titleText);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }

    /**
     * 选择dialog 是 否
     *
     * @param context
     * @param onConfirmClickListener
     * @param onCancelClickListener
     */
    public static void showDialogWithSureAndCancel(Context context
            , String title
            , String contentText
            , SweetAlertDialog.OnSweetClickListener onConfirmClickListener
            , SweetAlertDialog.OnSweetClickListener onCancelClickListener) {
        if (context == null) return;
        beforeShowDialog();
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText(title)
                .setContentText(contentText)
                .setConfirmText("是")
                .setCancelText("否")
                .showCancelButton(true)
                .setConfirmClickListener(onConfirmClickListener)
                .setCancelClickListener(onCancelClickListener);
        sweetAlertDialog.show();
    }

    /**
     * 弹出警告提示框
     *
     * @param context
     * @param title
     * @param contentText
     * @param confirmText
     * @param onSweetClickListener
     */
    public static void showWarningDialog(Context context
            , String title
            , String contentText
            , String confirmText
            , SweetAlertDialog.OnSweetClickListener onSweetClickListener) {
        if (context == null) return;
        beforeShowDialog();
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText(title)
                .setContentText(contentText)
                .setConfirmText(confirmText)
                .setConfirmClickListener(onSweetClickListener);
        sweetAlertDialog.show();
    }

    /**
     * 弹出警告提示框
     *
     * @param context
     * @param title
     * @param contentText
     * @param confirmText
     */
    public static void showWarningDialog(Context context
            , String title
            , String contentText
            , String confirmText) {
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText(title)
                .setContentText(contentText)
                .setConfirmText(confirmText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        hiddenProgressDialog();
                    }
                });
        sweetAlertDialog.show();
    }

    /**
     * 在Dialog弹出之前执行
     */
    private static void beforeShowDialog() {
        if (sweetAlertDialog != null) {
            if (sweetAlertDialog.isShowing()) {
                sweetAlertDialog.dismiss();
            }
            sweetAlertDialog = null;
        }
    }

    /**
     * 关闭loading dialog
     */
    public static void hiddenProgressDialog() {
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismiss();
            sweetAlertDialog = null;
        }
    }

    /**
     * 弹出loading dialog
     *
     * @param activity
     */
    public static void loadingShow(BaseActivity activity) {
        loadingShow(activity, null);
    }

    /**
     * 弹出loading dialog
     *
     * @param activity
     * @param titleText
     */
    public static void loadingShow(final BaseActivity activity, final String titleText) {
        if (activity == null) return;
        if (activity.isFinishing() || activity.isDestroyed()) return;
        final String showTitleText = StringUtils.isEmpty(titleText) ?
                "加载中..." : titleText;
        if ("main".equals(Thread.currentThread().getName())) {
            showProgressDialog(activity, showTitleText);
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgressDialog(activity, showTitleText);
                }
            });
        }
    }

    /**
     * 隐藏loadingdialog
     *
     * @param activity
     */
    public static void loadingDismiss(BaseActivity activity) {
        if (activity == null) return;
        if (activity.isFinishing() || activity.isDestroyed()) return;
        if ("main".equals(Thread.currentThread().getName())) {
            hiddenProgressDialog();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hiddenProgressDialog();
                }
            });
        }
    }
}