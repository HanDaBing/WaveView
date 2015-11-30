package com.example.hanbing.waveview;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

/**
 * Created by hanbing on 15-4-2.
 */
public class ImageMatrixTest extends Activity {
    private GridLayout mGridLayout;
    private ImageMatrixView imageMatrixView;
    private int mEdWidth;
    private int mEdHeight;
    private float [] mImageMatrix=new float[9];
    private EditText [] mEts=new EditText[9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix);
        imageMatrixView= (ImageMatrixView) findViewById(R.id.view);
        mGridLayout= (GridLayout) findViewById(R.id.grid_group);
        mGridLayout.post(new Runnable() {
            @Override
            public void run() {
                mEdWidth=mGridLayout.getWidth()/3;
                mEdHeight=mGridLayout.getHeight()/3;
                addEts();
                initImageMatrix();
            }
        });

    }

    private void addEts() {
        for (int i=0;i<9;i++){
            EditText editText=new EditText(this);
            editText.setGravity(Gravity.CENTER);
            mEts[i]=editText;
            mGridLayout.addView(editText,mEdWidth,mEdHeight);


        }
    }

    private void initImageMatrix(){
        for (int i = 0; i <9 ; i++) {
            if (i%4==0){
                mEts[i].setText(String.valueOf(1));
            }else{
                mEts[i].setText(String.valueOf(0));
            }

        }

    }
    private void getImageMatrix(){
        for (int i = 0; i <9 ; i++) {
            EditText ed=mEts[i];
            mImageMatrix[i]=Float.parseFloat(ed.getText().toString());

        }

    }
    public void change(View view){
       getImageMatrix();
        Matrix matrix=new Matrix();
//        matrix.setValues(mImageMatrix);

        imageMatrixView.setImageMatrix(matrix);
        imageMatrixView.invalidate();
    }
    public void reset(View view){
        initImageMatrix();
       getImageMatrix();
        Matrix matrix=new Matrix();
        matrix.setValues(mImageMatrix);
        imageMatrixView.setImageMatrix(matrix);
        imageMatrixView.invalidate();
    }

}
