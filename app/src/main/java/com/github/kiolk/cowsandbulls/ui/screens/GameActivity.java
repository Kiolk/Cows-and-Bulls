package com.github.kiolk.cowsandbulls.ui.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.models.GameResult;
import com.github.kiolk.cowsandbulls.data.models.Move;
import com.github.kiolk.cowsandbulls.logic.CustomTimer;
import com.github.kiolk.cowsandbulls.ui.dialogs.PublishDialog;
import com.github.kiolk.cowsandbulls.ui.views.DisplayLayout;
import com.github.kiolk.cowsandbulls.ui.views.KeyboardLayout;
import com.github.kiolk.cowsandbulls.utils.NumberUtil;

public class GameActivity extends AppCompatActivity implements KeyboardLayout.OnKeyBoardListener {

    public static final int LENGTH_CODED_NUMBER = 4;
    public static final String BUNDLE_INPUT = "BUNDLE_INPUT";
    public static final String BUNDLE_CODED_NUMBER = "BUNDLE_CODED_NUMBER";


    private CustomTimer mCustomTimer;
    private TextView mTimerTV;
    private DisplayLayout mDisplayLayout;
    private KeyboardLayout mKeyboardLayout;
    private String mInput = "";
    private String mCodedNumber = "";

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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_INPUT, mInput);
        outState.putString(BUNDLE_CODED_NUMBER, mCodedNumber);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mInput = savedInstanceState.getString(BUNDLE_INPUT);
        mCodedNumber = savedInstanceState.getString(BUNDLE_CODED_NUMBER);
        mDisplayLayout.setText(mInput);
    }

    @Override
    public void onKeyPressed(String input) {
        mInput = input;
        mDisplayLayout.setText(input);
    }

    @Override
    public void onStartPressed() {
        mCodedNumber = NumberUtil.generateRandom(LENGTH_CODED_NUMBER);
        //TODO refresh recyclerView
    }

    @Override
    public void onStopPressed() {
        mDisplayLayout.setText(mCodedNumber);
    }

    @Override
    public void onEnterPressed() {
        if(!NumberUtil.checkCorrectInput(mInput, LENGTH_CODED_NUMBER)){
            Toast.makeText(this, "Your number incorrect", Toast.LENGTH_LONG).show();
        }

        int cows = NumberUtil.getCowsNumber(mCodedNumber, mInput);
        int bulls = NumberUtil.getBullsNumber(mCodedNumber, mInput);

        Log.d("MyLogs", "onEnterPressed: cows " + cows + " bulls: " + bulls);

        if(bulls == LENGTH_CODED_NUMBER){
            mDisplayLayout.setText(mCodedNumber);
            mKeyboardLayout.stop();
            PublishDialog dialog = new PublishDialog();
            dialog.setResult(new GameResult(5, 123l));
            dialog.show(getSupportFragmentManager(), PublishDialog.TAG);
        }

        Move nextMove = new Move(mInput, cows, bulls);
        //TODO pass object to adapter
    }
}