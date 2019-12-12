package com.github.kiolk.cowsandbulls.ui.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.models.GameResult;
import com.github.kiolk.cowsandbulls.ui.dialogs.PublishDialog;


public class GameActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_test).setOnClickListener(v -> {
            showResultDialog();
        });
    }

    void showResultDialog() {
        PublishDialog dialog = new PublishDialog();
        dialog.setResult(new GameResult(5, 123l));
        dialog.show(getSupportFragmentManager(), "44");
    }
}
