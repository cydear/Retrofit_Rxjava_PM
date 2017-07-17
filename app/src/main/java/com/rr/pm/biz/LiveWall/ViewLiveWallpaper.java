package com.rr.pm.biz.LiveWall;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * thx for https://github.com/songixan/Wallpaper
 *
 * @author lary.huang
 * @version v 1.4.8 2017/7/16 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class ViewLiveWallpaper extends WallpaperService {
    public static final String VIDEO_PARAMS_CONTROL_ACTION = "com.rr.pm.ViewLiveWallpaper";
    public static final String KEY_ACTION = "key";
    public static final int ACTION_VIOCE_SILENCE = 110;
    public static final int ACTION_VOICE_NORMAL = 111;

    @Override
    public Engine onCreateEngine() {
        return new ViewEngine();
    }

    public static void voiceSilence(Context context) {
        Intent intent = new Intent(VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(KEY_ACTION, ACTION_VIOCE_SILENCE);
        context.sendBroadcast(intent);
    }

    public static void voiceNormal(Context context) {
        Intent intent = new Intent(VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(KEY_ACTION, ACTION_VOICE_NORMAL);
        context.sendBroadcast(intent);
    }

    public static void setToWallpaper(Context context) {
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(context, ViewLiveWallpaper.class));
        context.startActivity(intent);
    }

    class ViewEngine extends Engine {

        private MediaPlayer mPlayer;
        private BroadcastReceiver mReceiver;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            IntentFilter filter = new IntentFilter(VIDEO_PARAMS_CONTROL_ACTION);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int action = intent.getIntExtra(KEY_ACTION, -1);
                    switch (action) {
                        case ACTION_VOICE_NORMAL:
                            mPlayer.setVolume(1.0f, 1.0f);
                            break;
                        case ACTION_VIOCE_SILENCE:
                            mPlayer.setVolume(0, 0);
                            break;
                    }
                }
            };

            registerReceiver(mReceiver, filter);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                mPlayer.start();
            } else {
                mPlayer.pause();
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mPlayer = new MediaPlayer();
            mPlayer.setSurface(holder.getSurface());
            AssetManager am = getApplication().getAssets();
            try {
                AssetFileDescriptor fileDescriptor = am.openFd("test1.mp4");
                mPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                mPlayer.setLooping(true);
                mPlayer.setVolume(0, 0);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mPlayer.release();
            mPlayer = null;
        }

        @Override
        public void onDestroy() {
            unregisterReceiver(mReceiver);
            super.onDestroy();
        }
    }
}
