package com.rr.pm.biz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.rr.pm.R;
import com.rr.pm.base.BaseActivity;
import com.rr.pm.util.DateUtils;
import com.rr.pm.util.FileProviderUtils;
import com.rr.pm.util.ToastUtils;

import java.io.File;
import java.util.Date;

/**
 * Android 7.0 FileProvider适配
 *
 * @author lary.huang
 * @version v 1.4.8 2017/8/8 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class CaptureActivity extends BaseActivity {
    private static final int REQUEST_CODE_TAKE_PHOTO = 0x100;
    private static final int CAMERA_PERSSION = 0x111;
    private Button mBtnPhoto;
    private ImageView mImgPhoto;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_capture;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBtnPhoto = (Button) findViewById(R.id.btn_photograph);
        mImgPhoto = (ImageView) findViewById(R.id.img_capture);

        mBtnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoPession();
            }
        });
    }

    private void takePhotoPession() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flagCamera = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            int flagWriteStorage = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (flagCamera != PackageManager.PERMISSION_GRANTED || flagWriteStorage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERSSION);
            } else {
                takePhoto();
            }
        } else {
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERSSION) {
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                ToastUtils.show(this, "权限获取失败!");
            }
        }
    }

    private String mPhotoPath;

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            String fileName = DateUtils.format(new Date(), "yyyyMMdd-HHmmss") + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            mPhotoPath = file.getAbsolutePath();
            /**
             * 方式一:
             * Uri fileUri = null;
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             fileUri = FileProvider.getUriForFile(this, "com.rr.pm.fileprovider", file);
             } else {
             fileUri = Uri.fromFile(file);
             }*/

            //方式二
            Uri fileUri = FileProviderUtils.getFileUri(this, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            mImgPhoto.setImageBitmap(BitmapFactory.decodeFile(mPhotoPath));
        }
    }
}
