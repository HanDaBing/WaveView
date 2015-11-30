package com.example.hanbing.waveview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hanbing on 15-4-7.
 */
public class RoundRectXfermodeView extends View {
    private Bitmap bitmap;
    private Bitmap outBitmap;
    private Paint paint;

    public RoundRectXfermodeView(Context context){

        super(context);
        initView();
    }
    public RoundRectXfermodeView(Context context,AttributeSet attrs){

        super(context,attrs);
        initView();

    }
    public RoundRectXfermodeView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        initView();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.test1);
        outBitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(outBitmap);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.drawRoundRect(0,0,bitmap.getWidth(),bitmap.getHeight(),50,50,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);
        paint.setXfermode(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(outBitmap,0,0,null);
    }

    //
}
