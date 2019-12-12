package com.github.kiolk.cowsandbulls.ui.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.logic.CustomTimer;
import com.github.kiolk.cowsandbulls.logic.TimerChange;

public class GameActivity extends AppCompatActivity implements TimerChange {

    private CustomTimer mCustomTimer;
    private TextView mTimerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCustomTimer = new CustomTimer(this);
        mTimerTV = findViewById(R.id.timerTV);
        //start
        Button buttonStart = findViewById(R.id.startButton);
        Button buttonStop = findViewById(R.id.stopButton);
        Button buttonReset = findViewById(R.id.resetButton);

        buttonStart.setOnClickListener(listener);
        buttonStop.setOnClickListener(listener);
        buttonReset.setOnClickListener(listener);
        //stop -> this block only for tasting

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startButton: {
                    mCustomTimer.start();
                    break;
                }
                case R.id.stopButton: {
                    mCustomTimer.stop();
                    break;
                }
                case R.id.resetButton: {
                    mCustomTimer.reset();
                    break;
                }
            }

        }
    };

    @Override
    public void updateView(String text) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                (mTimerTV).setText(text);

            }
        });

    }



}
