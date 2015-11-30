package com.example.hanbing.waveview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/** 图像倒影处理
 * Created by hanbing on 15-4-8.
 */
public class ReflectView extends View {
    private Bitmap mSrcBitmap;
    private Bitmap mReflectBitmap;
    private Paint mPaint;
    public ReflectView(Context context) {
        super(context);
        initView();
    }

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ReflectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        mSrcBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.test);
        Matrix matrix=new Matrix();
        matrix.setScale(1,-1);
        mReflectBitmap=Bitmap.createBitmap(mSrcBitmap,0,0,mSrcBitmap.getWidth(),mSrcBitmap.getHeight(),matrix,true);
        mPaint=new Paint();
        mPaint.setShader(new LinearGradient(0,mSrcBitmap.getHeight(),0,mSrcBitmap.getHeight()*1.4f, 0XDD000000,0X10000000, Shader.TileMode.CLAMP));
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mSrcBitmap,0,0,null);
        canvas.drawBitmap(mReflectBitmap,0,mSrcBitmap.getHeight(),null);
        canvas.drawRect(0,mReflectBitmap.getHeight(),mReflectBitmap.getWidth(),mReflectBitmap.getHeight()*2,mPaint);

    }
}
