package com.github.kiolk.cowsandbulls.ui.screens.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.ui.screens.GameActivity;
import com.github.kiolk.cowsandbulls.ui.screens.result.tabs.ResultsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class BestResultFragment extends Fragment {

    public static final String TAG = "BaseResultFragment";

    private TabLayout tabs;
    private ViewPager resultPager;
    private ResultsPagerAdapter adapter;
    private ImageView backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        tabs = view.findViewById(R.id.tl_results_tabs);
        backButton = view.findViewById(R.id.iv_result_back_button);
        backButton.setOnClickListener(v -> ((GameActivity) getActivity()).hideResults());
        resultPager = view.findViewById(R.id.vp_results_pager);
        resultPager.setOffscreenPageLimit(4);
        adapter = new ResultsPagerAdapter(getFragmentManager());
        resultPager.setAdapter(adapter);
        tabs.setupWithViewPager(resultPager);
        tabs.setTabRippleColor(null);

        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.getTabAt(i).setCustomView(adapter.getTabView(getContext(), i, i == 0));
        }

        tabs.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView title = tab.getCustomView().findViewById(R.id.tv_result_tab);
                title.setBackground(getContext().getResources().getDrawable(R.drawable.bg_blue_round));
                title.setTextColor(getContext().getResources().getColor(R.color.generalBlue));
                resultPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView title = tab.getCustomView().findViewById(R.id.tv_result_tab);
                title.setBackground(getContext().getResources().getDrawable(R.color.white));
                title.setTextColor(getContext().getResources().getColor(R.color.generalGray));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}
