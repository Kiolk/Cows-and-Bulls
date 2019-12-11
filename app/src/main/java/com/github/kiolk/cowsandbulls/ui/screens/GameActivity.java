package com.github.kiolk.cowsandbulls.ui.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.kiolk.cowsandbulls.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
