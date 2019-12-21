package com.github.kiolk.cowsandbulls.ui.screens.result.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.PeriodType;

public class ResultsPagerAdapter extends FragmentPagerAdapter {

    private TodayResultFragment mTodayFragment;
    private WeekResultFragment mWeekResultFragment;
    private MonthResultFragment mMonthFragment;
    private YearResultFragment mYearResultFragment;



    public ResultsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mTodayFragment = new TodayResultFragment();
        mWeekResultFragment = new WeekResultFragment();
        mMonthFragment = new MonthResultFragment();
        mYearResultFragment = new YearResultFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = mTodayFragment;
                break;
            case 1:
                fragment = mWeekResultFragment;
                break;
            case 2:
                fragment = mMonthFragment;
                break;
            default:
                fragment = mYearResultFragment;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = PeriodType.TODAY.getValue();
                break;
            case 1:
                title = PeriodType.WEEK.getValue();
                break;
            case 2:
                title = PeriodType.MONTH.getValue();
                break;
            case 3:
                title = PeriodType.YEAR.getValue();
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public View getTabView(Context context, int position, boolean isSelected) {
        String title = "";
        switch (position){
            case 0:
                title = context.getResources().getString(PeriodType.TODAY.getStringResId());
                break;
            case 1:
                title = context.getResources().getString(PeriodType.WEEK.getStringResId());
                break;
            case 2:
                title = context.getResources().getString(PeriodType.MONTH.getStringResId());
                break;
            case 3:
                title = context.getResources().getString(PeriodType.YEAR.getStringResId());
                break;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.item_result_tab, null);
        TextView tabTitle = view.findViewById(R.id.tv_result_tab);

        if(isSelected){
            tabTitle.setBackground(context.getResources().getDrawable(R.drawable.bg_blue_round));
            tabTitle.setTextColor(context.getResources().getColor(R.color.generalBlue));
        }else{
            tabTitle.setBackground(context.getResources().getDrawable(R.color.white));
            tabTitle.setTextColor(context.getResources().getColor(R.color.generalGray));
        }

        tabTitle.setText(title);

        return view;
    }

    public void onRefresh() {
        mTodayFragment.onRefresh();
        mWeekResultFragment.onRefresh();
        mMonthFragment.onRefresh();
        mYearResultFragment.onRefresh();
    }

    public void onDestroy() {
        FragmentManager fm = mTodayFragment.requireFragmentManager();
        fm.beginTransaction().remove(mTodayFragment).commit();
        fm.beginTransaction().remove(mMonthFragment).commit();
        fm.beginTransaction().remove(mWeekResultFragment).commit();
        fm.beginTransaction().remove(mYearResultFragment).commit();
    }
}
