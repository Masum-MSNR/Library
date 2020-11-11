package com.project.library.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.project.library.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class SplashScreen extends AppCompatActivity {

    Shimmer appName;
    ShimmerTextView appNameTv;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appNameTv=findViewById(R.id.appName);
        logo=findViewById(R.id.logo);
        appName = new Shimmer()
                .setStartDelay(1500)
                .setDuration(500)
                .setRepeatCount(0)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR);
        appName.start(appNameTv);
        Animation iv = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoomin);
        Animation tv = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottomtotop);
        appNameTv.setAnimation(tv);
        logo.setAnimation(iv);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,Login.class));
                SplashScreen.this.finish();
            }
        },3000);
    }
}