package com.anshutiwari.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import ui.DashboardActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 5500;
    LottieAnimationView mLotIcon;
    LottieAnimationView mLotStaySafe;
    TextView mTvAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mTvAppName = findViewById(R.id.tv_app_name);
        mLotIcon = findViewById(R.id.lottie_covid);
        mLotStaySafe = findViewById(R.id.lottie_stay_safe);

        mLotIcon.animate().translationY(1600).setDuration(1000).setStartDelay(4000);
        mLotStaySafe.animate().translationY(1600).setDuration(1000).setStartDelay(4000);
        mTvAppName.animate().translationY(1600).setDuration(1000).setStartDelay(4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                finish();
            }
        },SPLASH_TIME);
    }
}