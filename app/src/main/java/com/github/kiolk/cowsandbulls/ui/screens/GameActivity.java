package com.github.kiolk.cowsandbulls.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.logic.CustomTimer;
import com.github.kiolk.cowsandbulls.logic.TimerChange;
import com.github.kiolk.cowsandbulls.ui.views.DisplayLayout;
import com.github.kiolk.cowsandbulls.ui.views.KeyboardLayout;

public class GameActivity extends AppCompatActivity implements KeyboardLayout.OnKeyBoardListener, TimerChange {

    private CustomTimer mCustomTimer;
    private TextView mTimerTV;
    private DisplayLayout mDisplayLayout;
    private KeyboardLayout mKeyboardLayout;
    private String mInput = "";
    private ImageView mTimerIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayLayout = findViewById(R.id.display_input);
        mKeyboardLayout = findViewById(R.id.keyboard_game);
        mKeyboardLayout.setOnKeyBoardListener(this);
        mCustomTimer = new CustomTimer(this);
        mTimerTV = findViewById(R.id.timerTV);
        mTimerIV = findViewById(R.id.timerIV);
        mTimerIV.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.ic_timer));
    }

    @Override
    public void onKeyPressed(String input) {
        mInput = input;
        mDisplayLayout.setText(input);
    }

    @Override
    public void onStartPressed() {
        mCustomTimer.reset();
        mCustomTimer.start();
    }

    @Override
    public void onStopPressed() {
        mCustomTimer.stop();
    }

    @Override
    public void onEnterPressed() {

    }

    @Override
    public void updateView(String text) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                (mTimerTV).setText(text);
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mCustomTimer = CustomTimer.restoreTimer(savedInstanceState, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(mCustomTimer.saveState(outState));
    }
}