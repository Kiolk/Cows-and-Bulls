package com.github.kiolk.cowsandbulls.ui.screens.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.kiolk.cowsandbulls.R;

public abstract class ToolBarFragment extends Fragment {

    private ImageView backButton;
    private TextView mTitle;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backButton = view.findViewById(R.id.iv_result_back_button);
        backButton.setOnClickListener(v -> onBackPressed());
        mTitle = view.findViewById(R.id.tv_result_title);
        mTitle.setText(getTitle());
    }

    public abstract String getTitle();

    public abstract void onBackPressed();
}