package com.github.kiolk.cowsandbulls.ui.screens;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.models.GameResult;
import com.github.kiolk.cowsandbulls.data.models.Move;
import com.github.kiolk.cowsandbulls.logic.CustomTimer;
import com.github.kiolk.cowsandbulls.logic.TimerChange;
import com.github.kiolk.cowsandbulls.ui.adapters.GameAdapter;
import com.github.kiolk.cowsandbulls.ui.dialogs.PublishDialog;
import com.github.kiolk.cowsandbulls.ui.views.DisplayLayout;
import com.github.kiolk.cowsandbulls.ui.views.KeyboardLayout;
import com.github.kiolk.cowsandbulls.utils.NumberUtil;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements KeyboardLayout.OnKeyBoardListener, TimerChange {

    public static final int LENGTH_CODED_NUMBER = 4;
    public static final String BUNDLE_INPUT = "BUNDLE_INPUT";
    public static final String BUNDLE_CODED_NUMBER = "BUNDLE_CODED_NUMBER";
    public static final String BUNDLE_MOVES = "BUNDLE_MOVES";
    public static final String BUNDLE_MOVES_VALUES = "BUNDLE_MOVES_VALUES";

    private CustomTimer mCustomTimer;
    private TextView mTimerTV;
    private DisplayLayout mDisplayLayout;
    private KeyboardLayout mKeyboardLayout;
    private String mInput = "";
    private int mMoves = 0;
    private RecyclerView mRecyclerView;
    private String mCodedNumber = "";
    private GameAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Default);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayLayout = findViewById(R.id.display_input);
        mKeyboardLayout = findViewById(R.id.keyboard_game);
        mKeyboardLayout.setOnKeyBoardListener(this);
        mCustomTimer = new CustomTimer(this);
        mTimerTV = findViewById(R.id.timerTV);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.rv_moves);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new GameAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_INPUT, mInput);
        outState.putString(BUNDLE_CODED_NUMBER, mCodedNumber);
        outState.putInt(BUNDLE_MOVES, mMoves);
        outState.putParcelableArrayList(BUNDLE_MOVES_VALUES, (ArrayList<? extends Parcelable>) mAdapter.getMoves());
        super.onSaveInstanceState(mCustomTimer.saveState(outState));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mInput = savedInstanceState.getString(BUNDLE_INPUT);
        mCodedNumber = savedInstanceState.getString(BUNDLE_CODED_NUMBER);
        mDisplayLayout.setText(mInput);
        mMoves = savedInstanceState.getInt(BUNDLE_MOVES);
        mAdapter.setMoves(savedInstanceState.getParcelableArrayList(BUNDLE_MOVES_VALUES));
        this.mCustomTimer = CustomTimer.restoreTimer(savedInstanceState, this);
    }

    @Override
    public void onKeyPressed(String input) {
        mInput = input;
        mDisplayLayout.setText(input);
    }

    @Override
    public void onStartPressed() {
        mCodedNumber = NumberUtil.generateRandom(LENGTH_CODED_NUMBER);
        mAdapter.setStartPressed();
        mAdapter.onClear();
        mCustomTimer.reset();
        mCustomTimer.start();
        mMoves = 0;
    }

    @Override
    public void onStopPressed() {
        mDisplayLayout.setText(mCodedNumber);
        mCustomTimer.stop();
        mInput = mCodedNumber;
        mMoves = 0;
    }

    @Override
    public void onEnterPressed() {
        if (!NumberUtil.checkCorrectInput(mInput, LENGTH_CODED_NUMBER)) {
            Toast.makeText(this, "Your number incorrect", Toast.LENGTH_LONG).show();
            return;
        }
        mMoves++;
        int cows = NumberUtil.getCowsNumber(mCodedNumber, mInput);
        int bulls = NumberUtil.getBullsNumber(mCodedNumber, mInput);

        if (bulls == LENGTH_CODED_NUMBER) {
            mDisplayLayout.setText(mCodedNumber);
            mCustomTimer.stop();
            PublishDialog dialog = new PublishDialog();
            dialog.setResult(new GameResult(mMoves, mCustomTimer.getTimeLong()));
            mMoves = 0;
            dialog.show(getSupportFragmentManager(), PublishDialog.TAG);
            mKeyboardLayout.stop();
        }

        Move nextMove = new Move(mInput, cows, bulls);
        mAdapter.addNextMove(nextMove);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void updateView(String text) {
        runOnUiThread(() -> mTimerTV.setText(text));
    }
}