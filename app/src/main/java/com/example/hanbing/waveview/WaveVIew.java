package com.example.hanbing.waveview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hanbing on 15-4-2.
 */
public class WaveVIew extends View {
    /**
     *
     */
    private int mViewWidth;

    private int mViewHight;

    private ArrayList<Point> mPointList;
    /**
     * 水位线
     */
    private float mLevelLine;
    /**
     * 波浪起伏幅度
     */
    private float mWaveHeight = 20;
    /**
     * 波浪起伏波长
     */
    private float mWaveWidth = 50;
    /**
     * 被隐藏的最左边的波形
     */
    private float mLeftSide;

    private float mMoveLen;
    /**
     * 水波平移速度
     */
    public static final float SPEED = 1.7f;

    private Paint mPaint;
    private Paint mTextPaint;

    private Path mWavePath;

    private boolean isMeasured = false;

    private Timer timer;

    private MyTimerTask myTimerTask;
    private Bitmap backgroundBitmap;
    private int backgroundWidth;
    private int backgroundHeight;
    private Bitmap showBitmap;
    private Canvas mCanvas;
    private float background_x,background_y;

    public WaveVIew(Context context) {
        super(context);
        init();
    }

    public WaveVIew(Context context, AttributeSet set) {
        super(context, set);
        init();

    }

    public WaveVIew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    private void init() {
        mPointList = new ArrayList<>();
        timer = new Timer();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);

        mTextPaint = new Paint();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(30);

        mWavePath = new Path();

        //获取图片，并设置取相交部分
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        backgroundBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.k);
        backgroundWidth=backgroundBitmap.getWidth();
        backgroundHeight=backgroundBitmap.getHeight();
        showBitmap=Bitmap.createBitmap(backgroundWidth,backgroundHeight, Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas();

        mCanvas.drawBitmap(backgroundBitmap, 0, 0, null);
        //设置模式为相交
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

//        mPaint.setXfermode(null);
    }

    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //记录平移总位移
            mMoveLen += SPEED;
            //水位上升
            mLevelLine -= 0.1f;
            if (mLevelLine < 0) {
                mLevelLine = 0;
            }
            mLeftSide += SPEED;
            for (int i = 0; i < mPointList.size(); i++) {
                mPointList.get(i).setX(mPointList.get(i).getX() + SPEED);
                switch (i % 4) {
                    case 0:
                    case 2:
                        mPointList.get(i).setY(mLevelLine);
                        break;
                    case 1:
                        mPointList.get(i).setY(mLevelLine + mWaveHeight);
                        break;
                    case 3:

                        mPointList.get(i).setY(mLevelLine - mWaveHeight);
                        break;
                }

            }
            if (mMoveLen >= mWaveWidth) {
                //平移超过一个完整波形后复位
                mMoveLen = 0;
                resetPoints();
            }
            invalidate();
        }
    };

    private void resetPoints() {
        mLeftSide = -mWaveWidth;
        for (int i = 0; i < mPointList.size(); i++) {
            mPointList.get(i).setX(i * mWaveWidth / 4 - mWaveWidth);

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        start();
    }

    private void start() {
        if (myTimerTask != null) {

            myTimerTask.cancel();
            myTimerTask = null;
        }
        myTimerTask = new MyTimerTask(updateHandler);
        timer.schedule(myTimerTask, 0, 10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasured) {
            isMeasured = true;
            mViewWidth = backgroundWidth;
            mViewHight = backgroundHeight;

            //水位线从最底下开始上升
            mLevelLine = mViewHight;
            //根据VIEW的宽度计算波形峰值
//            mWaveHeight = mViewWidth / 2.5f;
            //波长等于四倍View宽度也就是View中只能看到四分之一个波形，这样可以使起伏更明显
            mWaveWidth = mViewWidth/2.5f ;
            //左边隐藏的距离预留的一个波形
            mLeftSide = -mWaveWidth;
            //计算在可见的View宽度中能容纳几个波形
            int n = (int) Math.round(mViewWidth / mWaveWidth + 0.5);
            //n个波形需要4n＋1个点但是我们要预留一个波形在左边隐藏区域，所以需要4n＋5个点
            for (int i = 0; i < (4 * n + 5); i++) {
                //从P0开始出时候到P4n＋4，总共4n＋5个点
                float x = i * mWaveWidth / 4 - mWaveWidth;
                float y = 0;
                switch (i % 4) {
                    case 0:
                    case 2:
                        //
                        y = mLevelLine+background_y;
                        break;
                    case 1:
                        //
                        y = mLevelLine + mWaveHeight;
                        break;
                    case 3:
                        y = mLevelLine - mWaveHeight;
                        break;

                }
                mPointList.add(new Point(x, y));
            }
        }
    setMeasuredDimension(mViewWidth,mViewHight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWavePath.reset();
        int i = 0;
        mWavePath.moveTo(mPointList.get(0).getX(), mPointList.get(0).getY());
        for (; i < mPointList.size() - 2; i = i + 2) {
            mWavePath.quadTo(mPointList.get(i + 1).getX(), mPointList.get(i + 1).getY(), mPointList.get(i + 2).getX(), mPointList.get(i + 2).getY());

        }
        mWavePath.lineTo(mPointList.get(i).getX(), mViewHight);
        mWavePath.lineTo(mLeftSide, mViewHight);
        mWavePath.close();

        canvas.drawPath(mWavePath, mPaint);
//      canvas.drawBitmap(showBitmap,0,0,null);
        canvas.drawText("" + ((int) ((1 - mLevelLine / mViewHight) * 100)) + "%", mViewWidth / 2-30, mLevelLine + mWaveHeight + (mViewHight - mLevelLine - mWaveHeight), mTextPaint);
    }

    class MyTimerTask extends TimerTask {

        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;


        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }
    }

    class Point {
        private float x;
        private float y;


        public Point(float x, float y) {
            this.x = x;
            this.y = y;

        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }

}
