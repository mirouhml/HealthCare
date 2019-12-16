package com.example.phoenix.healthcare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class GlcRingProgressBar extends View {

/**
 * Created by hcc on 16/7/13 19:54
 * 100332338@qq.com
 * <p/>
 * 一个自定义的圆环进度条
 * 可适用于上传下载
 */


    //Brush object
    private Paint paint;

    //ViewWidth
    private int width;

    //ViewHeight
    private int height;

    //Default width and height
    private int result = 0;

    //default padding value
    private float padding = 0;

    //The color of the ring
    private int ringColor;

    //circle progress color
    private int ringProgressColor;

    //Text color
    private int textColor;

    //Font size
    private float textSize;

    //circle width
    private float ringWidth;

    //maximum
    private int max;

    //Progress value
    private int progress;

    //whether to display text
    private boolean textIsShow;

    //style of the ring progress bar
    private int style;

    //Hollow style
    public static final int STROKE = 0;

    //solid style
    public static final int FILL = 1;

    //progress callback interface
    private OnProgressListener mOnProgressListener;

    // ring center
    private int centre;

    // radius of the ring
    private int radius;


    public GlcRingProgressBar(Context context) {

        this(context, null);
    }


    public GlcRingProgressBar(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }


    public GlcRingProgressBar(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        //Initialize the brush
        paint = new Paint();

        //Initialize the default width and height
        result = dp2px(100);

        // Initialize the property
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RingProgressBar);

        ringColor = mTypedArray.getInteger(R.styleable.RingProgressBar_ringColor, getResources().getColor(R.color.ringColor));
        ringProgressColor = mTypedArray.getInteger(R.styleable.RingProgressBar_ringProgressColor, getResources().getColor(R.color.normal));
        textColor = getResources().getColor(R.color.text_color);
        textSize = 150;
        ringWidth = 10;
        max = 300;
        textIsShow = mTypedArray.getBoolean(R.styleable.RingProgressBar_textIsShow, true);
        style = mTypedArray.getInt(R.styleable.RingProgressBar_style, 0);


        mTypedArray.recycle();
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        centre = getWidth() / 2;
        radius = (int) (centre - ringWidth / 2 );

        // draw the outer circle
        drawCircle(canvas);
        // draw text content
        drawTextContent(canvas);
        // Draw a progress bar
        drawProgress(canvas);
    }


    /**
     * Draw outer ring
     */
    private void drawCircle(Canvas canvas) {
        //set Brush color
        paint.setColor(ringColor);
        //brush style
        paint.setStyle(Paint.Style.STROKE);
        //width of the stroke
        paint.setStrokeWidth(ringWidth);
        //antialiasing
        paint.setAntiAlias(true);
        //draw a circle
        canvas.drawCircle(centre, centre, radius, paint);
    }
    /**
     * drawing progress text
     */
    private void drawTextContent(Canvas canvas) {
        //set the width of the stroke
        paint.setStrokeWidth(0);
        setTextSize(135);
        paint.setTextSize(textSize);
        //set the style of the text
        paint.setTypeface(Typeface.DEFAULT);
        //set the progress value
        int percent = progress;
        String b = String.format("%03d", percent);
        //Get the width of the text for drawing text content
        float textWidth = paint.measureText(b);
        float x = centre-textWidth/2-25;
        float y = centre + textSize/2 -10;
        paint.setColor(getResources().getColor(R.color.text_color));
        //Draw text will be judged according to whether the setting shows whether the attribute of the text & is the style of Stroke
        if (textIsShow && percent != 0 && style == STROKE) {
            canvas.drawText(""+b , x , y , paint);
        }
        paint.setColor(Color.GRAY);
        setTextSize(65);
        paint.setTextSize(textSize);
        float x2=x + textWidth;
        float y2=y;
        canvas.drawText("mg/dL" , x2 , y2, paint);
        Bitmap heart= BitmapFactory.decodeResource(getResources(), R.drawable.ic_ic_action_blob);
        float y3=y2-heart.getHeight()-textSize/2+5;
        canvas.drawBitmap(heart, x2-25 , y3, paint);

    }


    /**
     * 绘制进度条
     */
    private void drawProgress(Canvas canvas) {
        //绘制进度 根据设置的样式进行绘制
        paint.setStrokeWidth(ringWidth);
        paint.setColor(ringProgressColor);

        //Stroke样式
        RectF strokeOval = new RectF(centre - radius, centre - radius, centre + radius,
                centre + radius);
        //FIll样式
        RectF fillOval = new RectF(centre - radius + ringWidth + padding,
                centre - radius + ringWidth + padding, centre + radius - ringWidth - padding,
                centre + radius - ringWidth - padding);

        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawArc(strokeOval, -90, 360 * progress / max, false, paint);
                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                if (progress != 0) {
                    canvas.drawArc(fillOval, -90, 360 * progress / max, true, paint);
                }
                break;
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高的mode和size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //测量宽度
        if (widthMode == MeasureSpec.AT_MOST) {
            width = result;
        } else {
            width = widthSize;
        }

        //测量高度
        if (heightMode == MeasureSpec.AT_MOST) {
            height = result;
        } else {
            height = heightSize;
        }

        //设置测量的宽高值
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        //确定View的宽高
        width = w;
        height = h;
    }


    /**
     * 获取当前的最大进度值
     */
    public synchronized int getMax() {

        return max;
    }


    /**
     * 设置最大进度值
     */
    public synchronized void setMax(int max) {

        if (max < 0) {
            throw new IllegalArgumentException("The max progress of 0");
        }
        this.max = max;
    }


    /**
     * 获取进度值
     */
    public synchronized int getProgress() {

        return progress;
    }


    /**
     * 设置进度值 根据进度值进行View的重绘刷新进度
     */
    public synchronized void setProgress(int progress) {

        if (progress < 0) {
            throw new IllegalArgumentException("The progress of 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }
        if (progress == max) {
            if (mOnProgressListener != null) {
                mOnProgressListener.progressToComplete();
            }
        }
        changeColor(progress);
    }
    public void changeColor(int progress){
        if (progress>160 && progress<241){
            setRingProgressColor(getResources().getColor(R.color.nearly_dangerous));
        }
        else if (progress<70 || progress>=241){
            setRingProgressColor(getResources().getColor(R.color.dangerous));
        }
        else setRingProgressColor(getResources().getColor(R.color.normal));
    }

    /**
     * 获取圆环的颜色
     */
    public int getRingColor() {

        return ringColor;
    }


    /**
     * 设置圆环的颜色
     */
    public void setRingColor(int ringColor) {

        this.ringColor = ringColor;
    }


    /**
     * 获取圆环进度的颜色
     */
    public int getRingProgressColor() {

        return ringProgressColor;
    }


    /**
     * 设置圆环进度的颜色
     */
    public void setRingProgressColor(int ringProgressColor) {

        this.ringProgressColor = ringProgressColor;
    }


    /**
     * 获取文字的颜色
     */
    public int getTextColor() {

        return textColor;
    }


    /**
     * 设置文字颜色
     */
    public void setTextColor(int textColor) {

        this.textColor = textColor;
    }


    /**
     * 获取文字的大小
     */
    public float getTextSize() {

        return textSize;
    }


    /**
     * 设置文字的大小
     */
    public void setTextSize(float textSize) {

        this.textSize = textSize;
    }


    /**
     * 获取圆环的宽度
     */
    public float getRingWidth() {

        return ringWidth;
    }


    /**
     * 设置圆环的宽度
     */
    public void setRingWidth(float ringWidth) {

        this.ringWidth = ringWidth;
    }


    /**
     * dp转px
     */
    public int dp2px(int dp) {

        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }


    /**
     * 进度完成回调接口
     */
    public interface OnProgressListener {

        void progressToComplete();
    }


    public void setOnProgressListener(OnProgressListener mOnProgressListener) {

        this.mOnProgressListener = mOnProgressListener;
    }
}