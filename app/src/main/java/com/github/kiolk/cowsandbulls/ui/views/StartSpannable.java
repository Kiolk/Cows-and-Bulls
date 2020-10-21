package com.github.kiolk.cowsandbulls.ui.views;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class StartSpannable extends ClickableSpan {

    @ColorInt
    private int startColor;

    private StartSpannableOnclickListener onclickListener;

    public StartSpannable(@NonNull StartSpannableOnclickListener onclickListener,
                          @NonNull int startColor) {
        this.onclickListener = onclickListener;
        this.startColor = startColor;
    }

    @Override
    public void onClick(@NonNull View view) {
        onclickListener.start();
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(startColor);
    }

    public StartSpannableOnclickListener getOnclickListener() {
        return onclickListener;
    }

    public interface StartSpannableOnclickListener {
        public void start();
    }
}
