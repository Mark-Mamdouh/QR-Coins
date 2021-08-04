package com.theprogrammer.qrcoins;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by markmamdouh on 7/16/2016.
 */


public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ImageView img = (ImageView)findViewById(R.id.splashscreen);
        final ImageView developer = (ImageView) findViewById(R.id.developer);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_2);
        final Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_2);
        if (img != null) {
            img.setAnimation(animation);
        }

        if (developer != null) {
            developer.setAnimation(animation);
        }

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //When you want delay some time
                try {
                    Thread.sleep(2500);
                    //When animate finish, load main activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (Exception e){

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
