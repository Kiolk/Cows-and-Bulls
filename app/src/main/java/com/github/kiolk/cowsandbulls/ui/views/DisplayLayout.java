package com.github.kiolk.cowsandbulls.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.kiolk.cowsandbulls.R;

public class DisplayLayout extends LinearLayout {

    private TextView mFirst;
    private TextView mSecond;
    private TextView mThird;
    private TextView mForth;

    public DisplayLayout(Context context) {
        super(context);
        init(context);
    }

    public DisplayLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DisplayLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_display, this, true);
        mFirst = view.findViewById(R.id.tv_first);
        mSecond = view.findViewById(R.id.tv_second);
        mThird = view.findViewById(R.id.tv_third);
        mForth = view.findViewById(R.id.tv_forth);
    }

    public void setText(String text) {
        clear();
        char[] array = text.toCharArray();
        for (int i = 0; i < array.length; i++) {
            switch (i) {
                case 0:
                    mFirst.setText(String.format("%s", array[i]));
                    break;
                case 1:
                    mSecond.setText(String.format("%s", array[i]));
                    break;
                case 2:
                    mThird.setText(String.format("%s", array[i]));
                    break;
                case 3:
                    mForth.setText(String.format("%s", array[i]));
                    break;
            }
        }
    }

    private void clear() {
        mFirst.setText("");
        mSecond.setText("");
        mThird.setText("");
        mForth.setText("");
    }
}
