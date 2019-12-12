package com.github.kiolk.cowsandbulls.ui.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.logic.CustomTimer;

public class GameActivity extends AppCompatActivity {

    private CustomTimer mCustomTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCustomTimer = new CustomTimer();
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
                    ((TextView) findViewById(R.id.timerTV)).setText(mCustomTimer.stop());
                    break;
                }
                case R.id.resetButton: {
                    ((TextView) findViewById(R.id.timerTV)).setText(mCustomTimer.reset());
                    break;
                }
            }

        }
    };
}
