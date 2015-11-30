package com.example.hanbing.waveview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hanbing on 15-4-8.
 */
public class BitmapShaderView extends View {

    private Bitmap mBitmap;
    private Paint mPaint;
    private BitmapShader mBitmapShader;

    public BitmapShaderView(Context context) {
        super(context);
    }

    public BitmapShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BitmapShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint=new Paint();
        mBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.test);
        mBitmapShader=new BitmapShader(mBitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        mPaint.setShader(mBitmapShader);
        canvas.drawCircle(300,200,150,mPaint);

    }
}
