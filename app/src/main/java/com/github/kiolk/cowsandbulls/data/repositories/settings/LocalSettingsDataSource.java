package com.github.kiolk.cowsandbulls.data.repositories.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalSettingsDataSource implements SettingsDataSource {

    private static final String SETTINGS = "SETTINGS";
    public static final String USER_NAME = "USER_NAME";
    public static final String IDENTIFICATION = "IDENTIFICATION";

    private SharedPreferences preferences;

    public LocalSettingsDataSource(Context context) {
        this.preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
    }

    @Override
    public void setUserName(String userName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    @Override
    public String getUserName() {
        return preferences.getString(USER_NAME, null);
    }

    @Override
    public void setIdentification(String identification) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(IDENTIFICATION, identification);
        editor.apply();
    }

    @Override
    public String getIdentification() {
        return preferences.getString(IDENTIFICATION, null);
    }
}
