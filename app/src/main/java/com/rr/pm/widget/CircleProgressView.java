package com.rr.pm.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgressView extends View {
    //最大圆形直径
    private float maxCircleLength = 500;
    //圆形位置
    private float mCircleXY;
    //圆形半径
    private float mRadius;
    //扇形滑动区域
    private float mSweepValue = 25;
    private String mSweepText = "CircleProgressView";
    private RectF mRectF;
    private Paint mCirclePaint, mArcPaint, mTextPaint;

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mCircleXY = maxCircleLength / 2;
        mRadius = (float) (maxCircleLength * 0.5 / 2);
        mRectF = new RectF((float) (maxCircleLength * 0.1), (float) (maxCircleLength * 0.1), (float) (maxCircleLength * 0.9), (float) (maxCircleLength * 0.9));
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.GRAY);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

        mArcPaint = new Paint();
        mArcPaint.setColor(Color.GRAY);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(60);
        mArcPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        //绘制弧线
        canvas.drawArc(mRectF, 270, mSweepValue, false, mArcPaint);
        //绘制文字
        canvas.drawText(mSweepText, 0, mSweepText.length(), mCircleXY, mCircleXY + (mSweepText.length() / 4), mTextPaint);
        super.onDraw(canvas);
    }

    public void setSweepValue(float sweepValue) {
        if (sweepValue != 0) {
            mSweepValue = sweepValue;
        } else {
            mSweepValue = 25;
        }
        this.invalidate();
    }
}