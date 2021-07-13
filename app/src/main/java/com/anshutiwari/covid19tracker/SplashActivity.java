package com.anshutiwari.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import ui.DashboardActivity;
import ui.OnBoardActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 5000;
    LottieAnimationView mLotIcon;
    LottieAnimationView mLotStaySafe;
    TextView mTvAppName;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mTvAppName = findViewById(R.id.tv_app_name);
        mLotIcon = findViewById(R.id.lottie_covid);
        mLotStaySafe = findViewById(R.id.lottie_stay_safe);

        mLotIcon.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        mLotStaySafe.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        mTvAppName.animate().translationY(3000).setDuration(1000).setStartDelay(4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

                boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);
                if (isFirstTime) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    startActivity(new Intent(SplashActivity.this, OnBoardActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    finish();
                }
            }
        },SPLASH_TIME);
    }
}