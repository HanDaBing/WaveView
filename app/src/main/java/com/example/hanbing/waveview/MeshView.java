package com.example.hanbing.waveview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**图片像旗子一样飘动
 * Created by hanbing on 15-4-8.
 */
public class MeshView extends View {
    /**
     * 把图像分成几个格
     */
    private int WIDTH=200;

    private int HEIGHT=200;
    private float K=1;
    private int COUNT=(WIDTH+1)*(HEIGHT+1);
    private float [] verts=new float[COUNT*2];

    private float [] orign=new float[COUNT*2];
    private Bitmap mBitmap;

    public MeshView(Context context) {
        super(context);
    }

    public MeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initView(){
        int index=0;
        mBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.test);
        float bmWidth=mBitmap.getWidth();
        float bmHeight=mBitmap.getHeight();
        for (int i = 0; i <HEIGHT+1 ; i++) {
            float fy=bmHeight*i/HEIGHT;
            for (int j = 0; j < WIDTH+1; j++) {
                float fx=bmWidth*j/WIDTH;
                orign[index*2+0]=verts[index*2+0]=fx;
                orign[index*2+1]=verts[index*2+1]=fy+200;
                index++;
            }
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i <HEIGHT+1 ; i++) {
            for (int j = 0; j < WIDTH+1; j++) {
                verts[(i*(WIDTH+1)+j)*2+0]+=0;
                float offsetY=(float)Math.sin((float)j/WIDTH*2*Math.PI+K*2*Math.PI);
                verts[(i*(WIDTH+1)+j)*2+1]=orign[(i*(WIDTH+1)+j)*2+1]+offsetY*50;
            }
        }
    K+=0.1f;
        canvas.drawBitmapMesh(mBitmap,WIDTH,HEIGHT,verts,0,null,0,null);
        invalidate();
    }
}
