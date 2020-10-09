package com.github.kiolk.cowsandbulls.ui.views;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.github.kiolk.cowsandbulls.R;

public class StartSpannable extends ClickableSpan {

    private StartSpannableOnclickListener onclickListener;

    public StartSpannable(StartSpannableOnclickListener onclickListener) {
        this.onclickListener = onclickListener;
    }

    @Override
    public void onClick(@NonNull View view) {
        if (onclickListener != null) {
            onclickListener.start();
        }
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        if (onclickListener != null) {
            ds.setColor(onclickListener.mGetContext().getResources().getColor(R.color.middleBlue));
        }
    }

    public interface StartSpannableOnclickListener {

        public void start();
        public Context mGetContext();

    }
}
