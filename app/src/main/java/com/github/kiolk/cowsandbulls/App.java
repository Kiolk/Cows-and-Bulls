package com.github.kiolk.cowsandbulls;

import android.app.Application;
import android.util.Log;

import com.github.kiolk.cowsandbulls.data.repositories.game.DefaultGameRepository;
import com.github.kiolk.cowsandbulls.data.repositories.game.GameRepository;
import com.github.kiolk.cowsandbulls.data.repositories.game.remote.RemoteGameDataSource;
import com.github.kiolk.cowsandbulls.data.repositories.settings.DefaultSettingRepository;
import com.github.kiolk.cowsandbulls.data.repositories.settings.local.LocalSettingsDataSource;
import com.github.kiolk.cowsandbulls.data.repositories.settings.SettingsRepository;
import com.github.kiolk.cowsandbulls.domain.use_cases.SetDeviceToken;
import com.google.firebase.iid.FirebaseInstanceId;

public class App extends Application {

    private static GameRepository gameRepository;
    private static SettingsRepository settingRepository;
    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setupFireBaseMessaging();
    }

    public static GameRepository getGameRepository() {
        if (gameRepository == null) {
            gameRepository = new DefaultGameRepository(new RemoteGameDataSource());
        }

        return gameRepository;
    }

    public static SettingsRepository getSettingsRepository() {
        if (settingRepository == null) {
            settingRepository = new DefaultSettingRepository(new LocalSettingsDataSource(instance.getApplicationContext()));
        }

        return settingRepository;
    }

    private void setupFireBaseMessaging() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null)
                            new SetDeviceToken(new SetDeviceToken.Params(task.getResult().getToken()), null).start();
                        Log.d("MyLogs", "onCreate: " + task.getResult().getToken());
                    }
                }
        );
    }
}
