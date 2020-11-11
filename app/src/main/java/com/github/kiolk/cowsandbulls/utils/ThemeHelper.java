package com.github.kiolk.cowsandbulls.utils;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeHelper {

    public static void applyTheme(int themePref) {
        switch (themePref) {
            case AppCompatDelegate.MODE_NIGHT_NO: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            }
            case AppCompatDelegate.MODE_NIGHT_YES: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            }
            default: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
                break;
            }
        }
    }

}
