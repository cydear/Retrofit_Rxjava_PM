package com.rr.pm.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rr.pm.R;

/**
 * 乡邻贷影像资料Item视图
 *
 * @author lary.huang
 * @version v 1.4.8 2017/9/14 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class MedicalImageItemView extends RelativeLayout {
    private ImageView mIvCamera, mIvPic, mIvCancel;
    private OnItemImageUploadLisenter onItemImageUploadLisenter;
    private MedicalImageItemView target;

    public MedicalImageItemView(Context context) {
        super(context);
        initView(context);
    }

    public MedicalImageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MedicalImageItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        target = this;
        LayoutInflater.from(context).inflate(R.layout.item_image_upload, this, true);
        mIvPic = (ImageView) findViewById(R.id.iv_pic);
        mIvCamera = (ImageView) findViewById(R.id.iv_camera);
        mIvCancel = (ImageView) findViewById(R.id.iv_cancel);

        mIvCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemImageUploadLisenter != null) {
                    onItemImageUploadLisenter.onSelectImageListener(target);
                }
            }
        });

        mIvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemImageUploadLisenter != null) {
                    onItemImageUploadLisenter.onDeleteImageLisetner(target);
                }
            }
        });
    }

    public void setOnItemImageUploadLisenter(OnItemImageUploadLisenter onItemImageUploadLisenter) {
        this.onItemImageUploadLisenter = onItemImageUploadLisenter;
    }

    /**
     * 选择图片后显示的容器
     *
     * @param bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        this.mIvPic.setImageBitmap(bitmap);
    }

    public void showCameraButton() {
        this.mIvCamera.setVisibility(VISIBLE);
    }

    public void hiddenCameraButton() {
        this.mIvCamera.setVisibility(GONE);
    }

    public void showCancelButton() {
        this.mIvCancel.setVisibility(VISIBLE);
    }

    public void hiddenCancelButton() {
        this.mIvCancel.setVisibility(GONE);
    }

    public interface OnItemImageUploadLisenter {
        void onSelectImageListener(ViewGroup target);

        void onDeleteImageLisetner(ViewGroup target);
    }
}
