package com.github.kiolk.cowsandbulls.data.repositories.settings;

public interface SettingsDataSource {

    void setUserName(String userName);

    String getUserName();

    void setIdentification(String identification);

    String getIdentification();
}
