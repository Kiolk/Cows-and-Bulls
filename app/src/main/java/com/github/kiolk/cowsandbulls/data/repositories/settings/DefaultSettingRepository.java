package com.github.kiolk.cowsandbulls.data.repositories.settings;

public class DefaultSettingRepository implements SettingsRepository {

    private SettingsDataSource local;

    public DefaultSettingRepository(SettingsDataSource local) {
        this.local = local;
    }

    @Override
    public void setUserName(String userName) {
        local.setUserName(userName);
    }

    @Override
    public String getUserName() {
        return local.getUserName();
    }

    @Override
    public void setIdentification(String identification) {
        local.setIdentification(identification);
    }

    @Override
    public String getIdentification() {
        return local.getIdentification();
    }

    @Override
    public void setDeviceToken(String token) {
        local.setDeviceToken(token);
    }

    @Override
    public String getDeviceToken() {
        return local.getDeviceToken();
    }

    @Override
    public int getThemePref() { return local.getThemePref(); }

    @Override
    public void setThemePref(int newTheme) {
        local.setThemePref(newTheme);
    }
}
