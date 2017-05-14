package com.example.administrator.work;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view=View.inflate(this,R.layout.activity_welcome,null);
        setContentView(view);
        //
        AlphaAnimation aa=new AlphaAnimation(1,0);
        aa.setDuration(3000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                
                skip();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //字体
        AssetManager mgr=getAssets();
        Typeface tf=Typeface.createFromAsset(mgr,"fonts/BrannbollSmal.ttf");

        TextView tv_name,tv_words1,tv_words2;

        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_words1= (TextView) findViewById(R.id.tv_words1);
        tv_words2= (TextView) findViewById(R.id.tv_words2);

        tv_name.setTypeface(tf);
        tv_words1.setTypeface(tf);
        tv_words2.setTypeface(tf);
    }

    private void skip(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
