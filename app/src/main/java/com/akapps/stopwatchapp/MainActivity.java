package com.akapps.stopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer,timermin,timersec,timermilli;
    LottieAnimationView startpause,reset;
    private boolean isResume;
    Handler handler;
    long tmillisec,tstart,tbuff,tupdate=0L;
    int sec,min,millisec;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timersec = findViewById(R.id.Timersec);
        timermilli = findViewById(R.id.Timermilisec);
        timermin = findViewById(R.id.Timermin);
        chronometer = findViewById(R.id.Timer);
        startpause = findViewById(R.id.palypause);
        reset = findViewById(R.id.reset);
        handler = new Handler();
        reset.setMinProgress(0.5f);
        startpause.setMinProgress(0.5f);
        startpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    tstart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    chronometer.start();
                    isResume = true;
                    startpause.setMinAndMaxProgress(0.5f,1.0f);
                    startpause.setSpeed(2);
                    startpause.playAnimation();
                }
                else {
                    tbuff +=tmillisec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    startpause.setMinAndMaxProgress(0.0f,0.5f);
                    startpause.setSpeed(2);
                    startpause.playAnimation();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    tmillisec=0L;
                    tstart=0L;
                    tbuff=0L;
                    tupdate=0L;
                    sec=0;
                    min=0;
                    millisec=0;
                    reset.setMinAndMaxProgress(0.0f,0.5f);
                    reset.setSpeed(2);
                    reset.playAnimation();
                    timersec.setText("00");
                    timermilli.setText("00");
                    timermin.setText("00");
                }
                else {
                    reset.setMinProgress(0.5f);
                    tmillisec=0L;
                    tstart=0L;
                    tbuff=0L;
                    tupdate=0L;
                    sec=0;
                    min=0;
                    millisec=0;
                    reset.setMinAndMaxProgress(0.0f,0.5f);
                    reset.setSpeed(2);
                    reset.playAnimation();
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    timersec.setText("00");
                    timermilli.setText("00");
                    timermin.setText("00");
                    isResume = false;
                    startpause.setMinAndMaxProgress(0.0f,0.5f);
                }
            }
        });
    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tmillisec = SystemClock.uptimeMillis()-tstart;
            tupdate = tbuff+tmillisec;
            sec = (int)(tupdate/1000);
            min = sec/60;
            sec=sec%60;
            millisec=(int)(tupdate%100);
            timermin.setText(String.format("%02d",min));
            timersec.setText(String.format("%02d",sec));
            timermilli.setText(String.format("%02d",millisec));
            handler.postDelayed(this,60);
        }
    };
}