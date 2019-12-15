package com.github.kiolk.cowsandbulls;

import android.app.Application;

import com.github.kiolk.cowsandbulls.data.repositories.game.DefaultGameRepository;
import com.github.kiolk.cowsandbulls.data.repositories.game.GameRepository;
import com.github.kiolk.cowsandbulls.data.repositories.game.remote.RemoteGameDataSource;
import com.github.kiolk.cowsandbulls.data.repositories.settings.DefaultSettingRepository;
import com.github.kiolk.cowsandbulls.data.repositories.settings.LocalSettingsDataSource;
import com.github.kiolk.cowsandbulls.data.repositories.settings.SettingsRepository;

public class App extends Application {

    private static GameRepository gameRepository;
    private static SettingsRepository settingRepository;
    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static GameRepository getGameRepository(){
        if(gameRepository == null){
            gameRepository = new DefaultGameRepository(new RemoteGameDataSource());
        }

        return gameRepository;
    }

    public static SettingsRepository getSettingsRepository(){
        if(settingRepository == null){
            settingRepository = new DefaultSettingRepository(new LocalSettingsDataSource(instance.getApplicationContext()));
        }

        return settingRepository;
    }
}
