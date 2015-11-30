package com.example.hanbing.waveview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.example.hanbing.waveview.anim.FabTransformation;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{


    FloatingActionButton floatingActionButton;
    private View buttom_view;
    private boolean isTransforming;
    TextView all_select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waveview);
//        floatingActionButton= (FloatingActionButton) findViewById(R.id.fab_btn);
//        buttom_view=findViewById(R.id.buttom_view);
//        floatingActionButton.setOnClickListener(this);
//        all_select= (TextView) findViewById(R.id.buttom_view_btn_add);
//        all_select.setOnClickListener(this);
//        FabTransformation.with(floatingActionButton).transformTo(buttom_view);
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_btn:
                FabTransformation.with(floatingActionButton).transformTo(buttom_view);

                break;
            case R.id.buttom_view_btn_add:
                FabTransformation.with(floatingActionButton).transformFrom(buttom_view);
                break;

        }
    }
}
