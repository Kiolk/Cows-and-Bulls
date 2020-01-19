package com.github.kiolk.cowsandbulls.data.repositories.settings.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.kiolk.cowsandbulls.data.repositories.settings.SettingsDataSource;

import java.util.UUID;

public class LocalSettingsDataSource implements SettingsDataSource {

    private static final String SETTINGS = "SETTINGS";
    public static final String USER_NAME = "USER_NAME";
    public static final String IDENTIFICATION = "IDENTIFICATION";
    public static final String DEVICE_TOKEN = "DEVICE_TOKEN";

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
        String uuid = preferences.getString(IDENTIFICATION, null);
        if(uuid == null){
            uuid = UUID.randomUUID().toString();
            setIdentification(uuid);
        }

        return uuid;
    }

    @Override
    public void setDeviceToken(String token) {
       SharedPreferences.Editor editor = preferences.edit();
       editor.putString(DEVICE_TOKEN, token);
       editor.apply();
    }

    @Override
    public String getDeviceToken() {
        return preferences.getString(DEVICE_TOKEN, null);
    }
}
