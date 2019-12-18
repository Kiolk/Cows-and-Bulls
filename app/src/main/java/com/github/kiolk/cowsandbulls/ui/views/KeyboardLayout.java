package com.github.kiolk.cowsandbulls.ui.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    private TextView mOne;
    private TextView mTwo;
    private TextView mThree;
    private TextView mForth;
    private TextView mFifth;
    private TextView mSix;
    private TextView mSeven;
    private TextView mEight;
    private TextView mNine;
    private TextView mZero;
    private ImageView mClean;

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

        mOne = view.findViewById(R.id.btn_one);
        mTwo = view.findViewById(R.id.btn_two);
        mThree = view.findViewById(R.id.btn_three);
        mForth = view.findViewById(R.id.btn_four);
        mFifth = view.findViewById(R.id.btn_five);
        mSix = view.findViewById(R.id.btn_six);
        mSeven = view.findViewById(R.id.btn_seven);
        mEight = view.findViewById(R.id.btn_eight);
        mNine = view.findViewById(R.id.btn_nine);
        mZero = view.findViewById(R.id.btn_zero);
        mEnter = view.findViewById(R.id.btn_enter);
        mClean = view.findViewById(R.id.btn_clear);
        mStart = view.findViewById(R.id.btn_start);

        mEnter.setEnabled(false);
        mClean.setEnabled(false);

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
                onStateChanged();
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

    public void stop(){
        onStateChanged();
    }

    private void checkPress(String pressedNumber) {
        if (!mInput.contains(pressedNumber) && mInput.length() < 4) {
            mInput = mInput + pressedNumber;
            listener.onKeyPressed(mInput);
        }

        if (mInput.length() >= 4) {
            mEnter.setEnabled(true);
        } else {
            mEnter.setEnabled(false);
        }
    }

    private void enableButtons(Boolean enabled){
        mOne.setEnabled(enabled);
        mTwo.setEnabled(enabled);
        mThree.setEnabled(enabled);
        mForth.setEnabled(enabled);
        mFifth.setEnabled(enabled);
        mSix.setEnabled(enabled);
        mSeven.setEnabled(enabled);
        mEight.setEnabled(enabled);
        mNine.setEnabled(enabled);
        mZero.setEnabled(enabled);
        mClean.setEnabled(enabled);
    }

    private void onStateChanged(){
        if (!isStarted) {
            listener.onStartPressed();
            mStart.setText(R.string.stop);
            mStart.setBackground(getContext().getResources().getDrawable(R.drawable.bg_red_button));
            isStarted = true;
            mEnter.setEnabled(false);
            mInput = "";
            listener.onKeyPressed(mInput);
            enableButtons(true);
        } else {
            listener.onStopPressed();
            mStart.setText(R.string.start);
            mStart.setBackground(getContext().getResources().getDrawable(R.drawable.bg_start_button));
            isStarted = false;
            mEnter.setEnabled(false);
            enableButtons(false);
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable state = super.onSaveInstanceState();
        SavedState ss = new SavedState(state);

        ss.isStartedValue = isStarted;
        ss.inputValue = mInput;

        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(state);

        isStarted = ss.isStartedValue;
        mInput = ss.inputValue;

        if (isStarted) {
            mStart.setText(R.string.stop);
            mStart.setBackground(getContext().getResources().getDrawable(R.drawable.bg_red_button));
            mEnter.setEnabled(false);
            enableButtons(true);

            if(mInput .length() == 4){
                mEnter.setEnabled(true);
            }
        } else {
            mStart.setText(R.string.start);
            mStart.setBackground(getContext().getResources().getDrawable(R.drawable.bg_start_button));
            mEnter.setEnabled(false);
            enableButtons(false);
        }
    }

    public class SavedState extends BaseSavedState{

        private Boolean isStartedValue = false;
        private String inputValue = "";

        private SavedState(Parcel source) {
            super(source);

            isStartedValue = source.readInt() == 1;
            inputValue = source.readString();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(isStarted ? 1 : 0);
            out.writeString(mInput);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }

        public final Creator CREATOR = new Creator() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
