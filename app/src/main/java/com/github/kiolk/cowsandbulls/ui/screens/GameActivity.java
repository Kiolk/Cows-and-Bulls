package com.github.kiolk.cowsandbulls.ui.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.logic.CustomTimer;
import com.github.kiolk.cowsandbulls.ui.views.DisplayLayout;
import com.github.kiolk.cowsandbulls.ui.views.KeyboardLayout;

public class GameActivity extends AppCompatActivity implements KeyboardLayout.OnKeyBoardListener {

    private CustomTimer mCustomTimer;
    private TextView mTimerTV;
    private DisplayLayout mDisplayLayout;
    private KeyboardLayout mKeyboardLayout;
    private String mInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayLayout = findViewById(R.id.display_input);
        mKeyboardLayout = findViewById(R.id.keyboard_game);
        mKeyboardLayout.setOnKeyBoardListener(this);
    }

    @Override
    public void onKeyPressed(String input) {
        mInput = input;
        mDisplayLayout.setText(input);
    }

    @Override
    public void onStartPressed() {

    }

    @Override
    public void onStopPressed() {

    }

    @Override
    public void onEnterPressed() {

    }
}