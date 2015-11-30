package com.example.hanbing.waveview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

/**
 * Created by hanbing on 15-10-9.
 */
public class MyAnimatorAcitvity extends Activity{

    private ImageView animator_image;
    private boolean show;
    private float screen_h;
    private float image_y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_test);
        animator_image= (ImageView) findViewById(R.id.image_test);
        screen_h=getWindowManager().getDefaultDisplay().getHeight();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show){
                    show=true;
//                    in(animator_image);
                    showPraiseAni(true);
//                    in2(animator_image);
                }else{
                    show=false;
                    showPraiseAni(false);
//                    out(animator_image);
//                    out2(animator_image);
                }

            }
        });
//        propertyValuesHolder(animator_image);


    }
    public void in(View view){
        float y=view.getY();
        PropertyValuesHolder alpha=PropertyValuesHolder.ofFloat("alpha",0f,0f,1f);
        PropertyValuesHolder pvhx_scale=PropertyValuesHolder.ofFloat("scaleX",0f,0f,1f);
        PropertyValuesHolder pvhy_scale=PropertyValuesHolder.ofFloat("scaleY",0f,0f,1f);
        PropertyValuesHolder pvhy_move=PropertyValuesHolder.ofFloat("y",screen_h,y);
        ObjectAnimator.ofPropertyValuesHolder(view,alpha,pvhx_scale,pvhy_scale,pvhy_move).setDuration(2000).start();
    }
    public void out(View view){
        float y=view.getY();
        PropertyValuesHolder alpha=PropertyValuesHolder.ofFloat("alpha", 1f, 1f, 0f);
        PropertyValuesHolder pvhx_scale=PropertyValuesHolder.ofFloat("scaleX",1f,1f,0f);
        PropertyValuesHolder pvhy_scale=PropertyValuesHolder.ofFloat("scaleY",1f,1f,0f);
        PropertyValuesHolder pvhy_move=PropertyValuesHolder.ofFloat("y",y,screen_h);
        ObjectAnimator.ofPropertyValuesHolder(view,alpha,pvhx_scale,pvhy_scale,pvhy_move).setDuration(2000).start();
    }

    public void in2(View view){
        view.setVisibility(View.VISIBLE);
        if (image_y==0){
            int [] a=new int[2];
            view.getLocationInWindow(a);
            image_y=a[1];
        }
        ObjectAnimator scaleX=ObjectAnimator.ofFloat(animator_image, "scaleX", 0f, 1f);
        ObjectAnimator scaleY=ObjectAnimator.ofFloat(animator_image,"scaleY",0f,1f);
        ObjectAnimator alpha=ObjectAnimator.ofFloat(animator_image, "alpha", 0f, 1f);
        ObjectAnimator move_y=ObjectAnimator.ofFloat(animator_image,"y",screen_h,image_y);
        AnimatorSet animSet=new AnimatorSet();
        animSet.setDuration(10000);
        animSet.setInterpolator(new OvershootInterpolator(2));
        animSet.playTogether(scaleX,scaleY,move_y,alpha);
        animSet.start();
    }
    public void out2(View view){

        ObjectAnimator scaleX=ObjectAnimator.ofFloat(animator_image, "scaleX", 1f, 0f);
        ObjectAnimator scaleY=ObjectAnimator.ofFloat(animator_image,"scaleY",1f,0f);
        ObjectAnimator move_y=ObjectAnimator.ofFloat(animator_image,"y",image_y,screen_h);
        ObjectAnimator alpha=ObjectAnimator.ofFloat(animator_image,"alpha",1f,0f);
        AnimatorSet animSet=new AnimatorSet();
        animSet.setDuration(10000);
        animSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animSet.playTogether(scaleX, scaleY, move_y,alpha);
        animSet.start();

    }
    public void showPraiseAni(final boolean isFirst){
        AnimatorSet animSet=new AnimatorSet();
        ObjectAnimator alpha,scaleX,scaleY,move_y;
        if(isFirst){
            alpha=ObjectAnimator.ofFloat(animator_image, "alpha", 0.1f, 1f);
            scaleX=ObjectAnimator.ofFloat(animator_image, "scaleX", 0.1f, 1f);
            scaleY=ObjectAnimator.ofFloat(animator_image,"scaleY",0.1f,1f);
            move_y=ObjectAnimator.ofFloat(animator_image,"y",animator_image.getTop(),animator_image.getTop()-animator_image.getHeight());
            animSet.setInterpolator(new OvershootInterpolator(2));
        }else {
            alpha=ObjectAnimator.ofFloat(animator_image, "alpha", 1f, 0.1f);
            scaleX=ObjectAnimator.ofFloat(animator_image, "scaleX", 1f, 0.1f);
            scaleY=ObjectAnimator.ofFloat(animator_image,"scaleY",1f,0.1f);
            move_y=ObjectAnimator.ofFloat(animator_image,"y",animator_image.getTop()-animator_image.getHeight(),animator_image.getTop()-animator_image.getHeight()*2);
            animSet.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        animSet.setDuration(20000);
        animSet.playTogether(alpha, scaleX, scaleY, move_y);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                animator_image.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (isFirst) {
                    showPraiseAni(false);
                } else {
                    animator_image.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animSet.start();
    }

}
