package com.rr.pm.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.icu.util.Calendar;
import android.icu.util.Measure;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rr.pm.R;
import com.rr.pm.util.DisplayUtils;

/**
 * @author lary.huang
 * @version v 1.4.8 2017/6/8 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class ClockView extends View {
    //外圆半径
    private float mRadius;
    //边距
    private float mPadding;
    //文字大小
    private float mTextSize;
    //时针宽度
    private float mHourPointWidth;
    //分针宽度
    private float mMinutePointWidth;
    //秒针宽度
    private float mSecondPointWidth;
    //指针圆角
    private int mPointRadius;
    //指针末尾的长度
    private float mPointEndLength;

    //长线的颜色
    private int mColorLong;
    //短线颜色
    private int mColorShort;
    //时针的颜色
    private int mHourPointColor;
    //分针的颜色
    private int mMinutePointColor;
    //秒针颜色
    private int mSecondPointColor;

    //画笔
    private Paint mPaint;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttrs(attrs);
        initPaint();
    }

    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.WatchBoard);
        mPadding = array.getDimension(R.styleable.WatchBoard_wb_padding, DisplayUtils.dip2px(getContext(), 10));
        mTextSize = array.getDimension(R.styleable.WatchBoard_wb_text_size, DisplayUtils.sp2px(getContext(), 16));
        mHourPointWidth = array.getDimension(R.styleable.WatchBoard_wb_hour_pointer_width, DisplayUtils.dip2px(getContext(), 5));
        mMinutePointWidth = array.getDimension(R.styleable.WatchBoard_wb_minute_pointer_width, DisplayUtils.dip2px(getContext(), 3));
        mSecondPointWidth = array.getDimension(R.styleable.WatchBoard_wb_second_pointer_width, DisplayUtils.dip2px(getContext(), 2));
        mPointRadius = (int) array.getDimension(R.styleable.WatchBoard_wb_pointer_corner_radius, DisplayUtils.dip2px(getContext(), 10));
        mPointEndLength = array.getDimension(R.styleable.WatchBoard_wb_pointer_end_length, DisplayUtils.dip2px(getContext(), 10));

        mColorLong = array.getColor(R.styleable.WatchBoard_wb_scale_long_color, Color.argb(225, 0, 0, 0));
        mColorShort = array.getColor(R.styleable.WatchBoard_wb_scale_short_color, Color.argb(125, 0, 0, 0));
        mHourPointColor = array.getColor(R.styleable.WatchBoard_wb_hour_pointer_color, Color.BLACK);
        mMinutePointColor = array.getColor(R.styleable.WatchBoard_wb_minute_pointer_color, Color.BLACK);
        mSecondPointColor = array.getColor(R.styleable.WatchBoard_wb_second_pointer_color, Color.RED);
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 1000;//设定最小值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED
                || heightModel == MeasureSpec.AT_MOST || heightModel == MeasureSpec.UNSPECIFIED) {
            throw new RuntimeException("宽和高至少有一个确定的值,不能为wrap_content...");
        } else {
            if (widthMode == MeasureSpec.EXACTLY) {
                width = Math.min(widthSize, width);
            }
            if (heightModel == MeasureSpec.EXACTLY) {
                width = Math.min(heightSize, width);
            }
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = (Math.min(w, h - getPaddingLeft() - getPaddingRight())) / 2;
        mPointEndLength = mRadius / 6;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        //绘制外圆背景
        drawOutCircle(canvas);
        //绘制刻度
        drawScale(canvas);
        //绘制指针
        drawPoint(canvas);
        canvas.restore();
        //刷新
        postInvalidateDelayed(1000);
    }

    /**
     * 绘制外圆
     *
     * @param canvas
     */
    private void drawOutCircle(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, mRadius, mPaint);
    }

    /**
     * 绘制刻度
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        mPaint.setStrokeWidth(DisplayUtils.dip2px(getContext(), 1));
        int lineWidth = 0;
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(DisplayUtils.dip2px(getContext(), 1.5f));
                mPaint.setColor(mColorLong);
                lineWidth = 40;
                mPaint.setTextSize(mTextSize);
                String text = String.valueOf((i / 5) == 0 ? 12 : (i / 5));
                Rect textBound = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), textBound);
                mPaint.setColor(Color.BLACK);
                canvas.save();
                canvas.translate(0, -mRadius + DisplayUtils.dip2px(getContext(), 5) + lineWidth + mPadding + (textBound.bottom - textBound.top) / 2);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.rotate(-6 * i);
                canvas.drawText(text, -(textBound.right + textBound.left) / 2, -(textBound.bottom + textBound.top) / 2, mPaint);
                canvas.restore();
            } else {
                //非整点
                lineWidth = 30;
                mPaint.setColor(mColorShort);
                mPaint.setStrokeWidth(DisplayUtils.dip2px(getContext(), 1));
            }
            canvas.drawLine(0, -mRadius + mPadding, 0, -mRadius + mPadding + lineWidth, mPaint);
            canvas.rotate(6);
        }
    }

    /**
     * 绘制指针
     *
     * @param canvas
     */
    @TargetApi(Build.VERSION_CODES.N)
    private void drawPoint(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        //时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //分
        int minute = calendar.get(Calendar.MINUTE);
        //秒
        int second = calendar.get(Calendar.SECOND);
        //时针转过的角度
        int angleHour = (hour % 12) * 360 / 12;
        //分针转过的角度
        int angleMinute = minute * 360 / 60;
        //秒针转过的角度
        int angleSecond = second * 360 / 60;
        //绘制时针
        canvas.save();
        canvas.rotate(angleHour);
        RectF rectFHour = new RectF(-mHourPointWidth / 2, -mRadius * 3 / 5, mHourPointWidth / 2, mPointEndLength);
        mPaint.setColor(mHourPointColor);
        mPaint.setStrokeWidth(mHourPointWidth);
        canvas.drawRoundRect(rectFHour, mPointRadius, mPointRadius, mPaint);
        canvas.restore();
        //绘制分针
        canvas.save();
        canvas.rotate(angleMinute);
        RectF rectFMinute = new RectF(-mMinutePointWidth / 2, -mRadius * 3.5f / 5, mMinutePointWidth / 2, mPointEndLength);
        mPaint.setColor(mMinutePointColor);
        mPaint.setStrokeWidth(mMinutePointWidth);
        canvas.drawRoundRect(rectFMinute, mPointRadius, mPointRadius, mPaint);
        canvas.restore();
        //绘制秒针
        canvas.save();
        canvas.rotate(angleSecond);
        RectF rectFSecond = new RectF(-mSecondPointWidth / 2, -mRadius + 15, mSecondPointWidth / 2, mPointEndLength);
        mPaint.setColor(mSecondPointColor);
        mPaint.setStrokeWidth(mSecondPointWidth);
        canvas.drawRoundRect(rectFSecond, mPointRadius, mPointRadius, mPaint);
        canvas.restore();
        //绘制校园
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mSecondPointColor);
        canvas.drawCircle(0, 0, mSecondPointWidth * 4, mPaint);
        canvas.restore();
    }
}
