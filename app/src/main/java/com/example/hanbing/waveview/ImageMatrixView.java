package com.example.hanbing.waveview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hanbing on 15-4-7.
 */
public class ImageMatrixView extends View {

    private Matrix mMatrix;
    private Bitmap bitmap;
    public ImageMatrixView(Context context){
        super(context);
        initView();

    }
    public ImageMatrixView(Context context,AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public ImageMatrixView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        initView();
    }

    private void initView(){
        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        setImageMatrix(new Matrix());
    }
    public void setImageMatrix(Matrix mMatrix){
        this.mMatrix=mMatrix;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawBitmap(bitmap,mMatrix,null);

    }
}
