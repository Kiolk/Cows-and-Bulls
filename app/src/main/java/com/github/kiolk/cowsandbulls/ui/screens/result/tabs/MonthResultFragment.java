package com.github.kiolk.cowsandbulls.ui.screens.result.tabs;

import com.github.kiolk.cowsandbulls.data.PeriodType;

public class MonthResultFragment extends BaseCategoryResultFragment {
    @Override
    PeriodType getType() {
        return PeriodType.MONTH;
    }
}
