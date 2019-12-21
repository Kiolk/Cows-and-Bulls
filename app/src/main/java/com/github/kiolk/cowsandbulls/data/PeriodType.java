package com.github.kiolk.cowsandbulls.data;

import com.github.kiolk.cowsandbulls.R;

public enum PeriodType {

    TODAY("today", R.string.result_tab_today),
    WEEK("week", R.string.result_tab_week),
    MONTH("month", R.string.result_tab_month),
    YEAR("year", R.string.result_tab_year);

    private String value;
    private int stringResId;

    PeriodType(String value, int stringResId) {
        this.value = value;
        this.stringResId = stringResId;
    }

    public String getValue() {
        return value;
    }

    public int getStringResId() {
        return stringResId;
    }
}
