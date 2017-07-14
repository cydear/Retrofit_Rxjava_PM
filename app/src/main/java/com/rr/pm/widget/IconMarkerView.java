package com.rr.pm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rr.pm.R;

/**
 * 自定义icon 附加自定义角标
 *
 * @author lary.huang
 * @version v 1.4.8 2017/5/12 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class IconMarkerView extends View {

    private Context context;
    private float maxTextLen;
    private int width, height;
    private int DEFAULT_ICON_W = 300, DEFUALT_ICON_H = 300;
    private String[] titles;
    private Paint mPaintIcon, mPaintText, mPaintBg;

    public IconMarkerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IconMarkerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_icon_style);
        String title = typedArray.getString(R.styleable.custom_icon_style_corner_title);
        if (title != null && title.length() > 0) {
            titles = title.split("\\|");
        }
        typedArray.recycle();
        //绘制icon
        mPaintIcon = new Paint();
        mPaintIcon.setAntiAlias(true);
        mPaintIcon.setColor(Color.BLUE);
        mPaintIcon.setStyle(Paint.Style.STROKE);
        //绘制文本背景
        mPaintBg = new Paint();
        mPaintBg.setAntiAlias(true);
        mPaintBg.setColor(Color.RED);
        mPaintBg.setStyle(Paint.Style.FILL);
        //绘制文本
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //文本最大宽度
        maxTextLen = getMaxTextLen();
        int width = DEFAULT_ICON_W;
        if (maxTextLen > DEFAULT_ICON_W / 3) {
            width = (int) (DEFAULT_ICON_W + (maxTextLen - DEFAULT_ICON_W / 3));
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制外边框
        canvas.drawRect(0, 0, DEFAULT_ICON_W, DEFUALT_ICON_H, mPaintIcon);
        //绘制文字背景
        canvas.save();
        canvas.drawRect(DEFAULT_ICON_W / 3 * 2, 0, DEFAULT_ICON_W / 3 * 2 + mPaintText.measureText(titles[0]) + 20, 100, mPaintBg);
        canvas.restore();
        //绘制文字
        canvas.drawText(titles[0], DEFAULT_ICON_W / 3 * 2 + 10, 50, mPaintText);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    /**
     * get max text length
     */
    private float getMaxTextLen() {
        if (titles == null) {
            return 0;
        }
        float max = mPaintText.measureText(titles[0]);
        for (int i = 1; i < titles.length; i++) {
            float temp = mPaintText.measureText(titles[i]);
            if (temp > maxTextLen) {
                max = temp;
            }
        }
        return max;
    }
}
