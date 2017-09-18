package com.rr.pm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.rr.pm.R;
import com.rr.pm.util.DisplayUtils;
import com.rr.pm.util.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 自定义指示器
 *
 * @author lary.huang
 * @version v 1.4.8 2017/8/10 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class CircleIndicatorView extends View implements ViewPager.OnPageChangeListener {
    private static final String LETTER[] = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    //指示器个数
    private int count = 0;
    //绘制指示器的画笔
    private Paint circlePaint;
    //绘制文本画笔
    private Paint textPaint;
    //指示器的半径
    private int indicatorRadius;
    //指示器的边框宽度
    private int indicatorStrokWidth;
    //指示器间距
    private int indicatorSpace;
    //指示器文字的颜色
    private int indicatorTextColor;
    //指示器被选中后的颜色
    private int indicatorSelectColor;
    //指示器的颜色
    private int indicatorColor;
    //指示器模式
    private FillMode fillMode;
    //选中的item
    private int selectPosition;
    //ViewPager
    private ViewPager viewPager;
    private ScheduledExecutorService executorService;


    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicatorView);
        indicatorRadius = (int) typedArray.getDimension(R.styleable.CircleIndicatorView_indicatorRadius, DisplayUtils.px2dip(context, 50));
        indicatorStrokWidth = (int) typedArray.getDimension(R.styleable.CircleIndicatorView_indicatorBorderWidth, 0);
        indicatorSpace = (int) typedArray.getDimension(R.styleable.CircleIndicatorView_indicatorSpace, DisplayUtils.px2dip(context, 50));
        indicatorTextColor = typedArray.getColor(R.styleable.CircleIndicatorView_indicatorTextColor, Color.parseColor("#2f96ff"));
        indicatorSelectColor = typedArray.getColor(R.styleable.CircleIndicatorView_indicatorSelectColor, Color.parseColor("#FFFFFF"));
        indicatorColor = typedArray.getColor(R.styleable.CircleIndicatorView_indicatorColor, Color.parseColor("#dcdcdc"));
        int mode = typedArray.getInt(R.styleable.CircleIndicatorView_fill_mode, 2);
        if (mode == 0) {
            fillMode = FillMode.LETTER;
        } else if (mode == 1) {
            fillMode = FillMode.NUMBER;
        } else if (mode == 2) {
            fillMode = FillMode.NONE;
        }

        typedArray.recycle();

        //初始化绘制指示器画笔
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStrokeWidth(indicatorStrokWidth);
        circlePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30);
        textPaint.setColor(indicatorTextColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (indicatorRadius + indicatorStrokWidth) * 2 * count + indicatorSpace * (count - 1);
        int height = (indicatorRadius + indicatorStrokWidth) * 2;
        setMeasuredDimension(width, height);
        //计算每一个圆心的坐标
        caculateIndicator();
    }

    private List<Indicator> indicators = new ArrayList<Indicator>();

    /**
     * 计算指示器的坐标集合
     */
    private void caculateIndicator() {
        indicators.clear();
        int x = 0;
        for (int i = 0; i < count; i++) {
            Indicator indicator = new Indicator();
            if (i == 0) {
                x = indicatorRadius + indicatorStrokWidth;
            } else {
                x += (indicatorRadius + indicatorStrokWidth) * 2 + indicatorSpace;
            }
            indicator.x = x;
            indicator.y = getMeasuredHeight() / 2;
            indicators.add(indicator);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < indicators.size(); i++) {
            circlePaint.setColor(indicatorColor);
            if (selectPosition == i) {
                circlePaint.setColor(indicatorSelectColor);
            }
            Indicator indicator = indicators.get(i);
            canvas.drawCircle(indicator.x, indicator.y, indicatorRadius, circlePaint);

            if (fillMode != FillMode.NONE) {
                String value = (fillMode == FillMode.LETTER) ? LETTER[i] : String.valueOf(i + 1);

                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                float fontHeight = fontMetrics.bottom - fontMetrics.top;
                float textBaseY = getHeight() - (getHeight() - fontHeight) / 2 - fontMetrics.bottom;

                float fontWidth = textPaint.measureText(value);
                float textBaseX = indicator.x - fontWidth / 2;

                canvas.drawText(value, textBaseX, textBaseY, textPaint);
            }
        }
    }

    /**
     * 设置指示器数目
     *
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
        invalidate();
    }

    /**
     * 设置指示器半径
     *
     * @param indicatorRadius
     */
    public void setIndicatorRadius(int indicatorRadius) {
        this.indicatorRadius = indicatorRadius;
        invalidate();
    }

    /**
     * 设置指示器边框宽度
     *
     * @param indicatorStrokWidth
     */
    public void setIndicatorStrokWidth(int indicatorStrokWidth) {
        this.indicatorStrokWidth = indicatorStrokWidth;
        invalidate();
    }

    /**
     * 设置指示器之间的边距
     *
     * @param indicatorSpace
     */
    public void setIndicatorSpace(int indicatorSpace) {
        this.indicatorSpace = indicatorSpace;
        invalidate();
    }

    /**
     * 设置指示器文字颜色
     *
     * @param indicatorTextColor
     */
    public void setIndicatorTextColor(int indicatorTextColor) {
        this.indicatorTextColor = indicatorTextColor;
        invalidate();
    }

    /**
     * 设置指示器被选中后的颜色
     *
     * @param indicatorSelectColor
     */
    public void setIndicatorSelectColor(int indicatorSelectColor) {
        this.indicatorSelectColor = indicatorSelectColor;
        invalidate();
    }

    /**
     * 设置指示器正常显示的颜色
     *
     * @param indicatorColor
     */
    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    /**
     * 设置指示器模式
     *
     * @param fillMode
     */
    public void setFillMode(FillMode fillMode) {
        this.fillMode = fillMode;
        invalidate();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.selectPosition = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;

        this.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                for (int i = 0; i < indicators.size(); i++) {
                    Indicator indicator = indicators.get(i);
                    int leftMin = indicator.x - indicatorRadius - indicatorStrokWidth;
                    int leftMax = indicator.x + indicatorRadius + indicatorStrokWidth;
                    int topMin = indicator.y - indicatorRadius - indicatorStrokWidth;
                    int topMax = indicator.y + indicatorRadius + indicatorStrokWidth;

                    if (x > leftMin && x < leftMax && y > topMin && y < topMax) {
                        this.viewPager.setCurrentItem(i, false);
                        break;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void startAutoScroll() {
        if (executorService == null) {
            executorService = Executors.newSingleThreadScheduledExecutor();
        }
        executorService.scheduleWithFixedDelay(new Task(), 2, 2, TimeUnit.SECONDS);
    }

    class Task implements Runnable {

        @Override
        public void run() {
            mHandler.obtainMessage().sendToTarget();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtils.d("==>1" + selectPosition);
            selectPosition = (selectPosition + 1) % viewPager.getChildCount();
            LogUtils.d("==>2" + selectPosition);
            viewPager.setCurrentItem(selectPosition);
        }
    };

    /**
     * 指示器对象
     */
    class Indicator {
        int x;
        int y;
    }

    /**
     * 指示器模式
     */
    enum FillMode {
        LETTER, NUMBER, NONE;
    }
}
