package com.github.kiolk.cowsandbulls.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.github.kiolk.cowsandbulls.utils.ThemeHelper;

public class Prefs {

    private Context context;

    private static final String PREFS_NAME = "prefs";
    private static final String PREFS_THEME = "themePref";

    public Prefs(Context context) {
        this.context = context;
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getThemePref() {
        return getPrefs().getString(PREFS_THEME, ThemeHelper.LIGHT_MODE);
    }

    public void setThemePref(@NonNull String newTheme) {
        getPrefs().edit().putString(PREFS_THEME, newTheme).apply();
    }

}
