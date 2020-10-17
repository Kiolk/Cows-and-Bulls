package com.github.kiolk.cowsandbulls.ui.screens.rules;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.ui.screens.GameActivity;
import com.github.kiolk.cowsandbulls.ui.screens.base.ToolBarFragment;
import com.github.kiolk.cowsandbulls.ui.views.StartSpannable;

public class RulesFragment extends ToolBarFragment {

    public static final String TAG = "RulesFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rules, container, false);
        TextView mRules = view.findViewById(R.id.tv_rules_description);
        Resources resources = view.getContext().getResources();
        String text = resources.getString(R.string.rules);
        String coloredText = resources.getString(R.string.start_part);
        String bottomText = "";
        StartSpannable clickableSpan = new StartSpannable((StartSpannable.StartSpannableOnclickListener) getActivity(),
                getContext().getResources().getColor(R.color.middleBlue));
        Spannable span = new SpannableString(text);
        String howToPlayText = resources.getString(R.string.how_to_play_part);
        int length = text.length();

        span.setSpan(clickableSpan, length - coloredText.length() - 2, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        span.setSpan(bss, 0, howToPlayText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRules.setText(span);
        mRules.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

    @Override
    public String getTitle() {
        if (getContext() == null) {
            return "";
        }
        return getContext().getResources().getString(R.string.rules_title);
    }

    @Override
    public void onBackPressed() {
        if (getActivity() == null) {
            return;
        }
        ((GameActivity) getActivity()).hideResults();
    }
}
