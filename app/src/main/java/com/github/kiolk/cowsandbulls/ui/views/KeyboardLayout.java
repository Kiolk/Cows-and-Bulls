package com.github.kiolk.cowsandbulls.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.kiolk.cowsandbulls.R;

public class KeyboardLayout extends LinearLayout implements View.OnClickListener {

    public interface OnKeyBoardListener {
        void onKeyPressed(String pressed);

        void onStartPressed();

        void onStopPressed();

        void onEnterPressed();
    }

    private OnKeyBoardListener listener;
    private Boolean isStarted = false;
    private String mInput = "";

    private TextView mStart;
    private RelativeLayout mEnter;

    public KeyboardLayout(Context context) {
        super(context);
        init(context);
    }

    public KeyboardLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KeyboardLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_keyboard, this, true);

        TextView mOne = view.findViewById(R.id.btn_one);
        TextView mTwo = view.findViewById(R.id.btn_two);
        TextView mThree = view.findViewById(R.id.btn_three);
        TextView mForth = view.findViewById(R.id.btn_four);
        TextView mFifth = view.findViewById(R.id.btn_five);
        TextView mSix = view.findViewById(R.id.btn_six);
        TextView mSeven = view.findViewById(R.id.btn_seven);
        TextView mEight = view.findViewById(R.id.btn_eight);
        TextView mNine = view.findViewById(R.id.btn_nine);
        TextView mZero = view.findViewById(R.id.btn_zero);
        mEnter = view.findViewById(R.id.btn_enter);
        ImageView mClean = view.findViewById(R.id.btn_clear);
        mStart = view.findViewById(R.id.btn_start);

        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);
        mForth.setOnClickListener(this);
        mFifth.setOnClickListener(this);
        mSix.setOnClickListener(this);
        mSeven.setOnClickListener(this);
        mEight.setOnClickListener(this);
        mNine.setOnClickListener(this);
        mZero.setOnClickListener(this);
        mEnter.setOnClickListener(this);
        mClean.setOnClickListener(this);
        mStart.setOnClickListener(this);
    }

    public void setOnKeyBoardListener(OnKeyBoardListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (!isStarted && v.getId() != R.id.btn_start) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_one:
                checkPress("1");
                break;
            case R.id.btn_two:
                checkPress("2");
                break;
            case R.id.btn_three:
                checkPress("3");
                break;
            case R.id.btn_four:
                checkPress("4");
                break;
            case R.id.btn_five:
                checkPress("5");
                break;
            case R.id.btn_six:
                checkPress("6");
                break;
            case R.id.btn_seven:
                checkPress("7");
                break;
            case R.id.btn_eight:
                checkPress("8");
                break;
            case R.id.btn_nine:
                checkPress("9");
                break;
            case R.id.btn_zero:
                checkPress("0");
                break;
            case R.id.btn_start:
                if (!isStarted) {
                    listener.onStartPressed();
                    mStart.setText(R.string.stop);
                    isStarted = true;
                    mEnter.setEnabled(false);
                    mInput = "";
                    listener.onKeyPressed(mInput);
                } else {
                    listener.onStopPressed();
                    mStart.setText(R.string.start);
                    isStarted = false;
                    mEnter.setEnabled(false);
                }
                break;
            case R.id.btn_clear:
                if (!mInput.isEmpty()) {
                    mInput = mInput.substring(0, mInput.length() - 1);
                }
                mEnter.setEnabled(false);
                listener.onKeyPressed(mInput);
                break;
            case R.id.btn_enter:
                listener.onEnterPressed();
                mInput = "";
                listener.onKeyPressed(mInput);
                break;
        }
    }

    private void checkPress(String pressedNumber) {
        if (!mInput.contains(pressedNumber)) {
            mInput = mInput + pressedNumber;
            listener.onKeyPressed(mInput);
        }

        if (mInput.length() >= 4) {
            mEnter.setEnabled(true);
        } else {
            mEnter.setEnabled(false);
        }
    }
}
