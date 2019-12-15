package com.github.kiolk.cowsandbulls.data.repositories.settings;

public interface SettingsRepository {

    void setUserName(String userName);

    String getUserName();

    void setIdentification(String identification);

    String getIdentification();
}
