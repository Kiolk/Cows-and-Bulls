package com.github.kiolk.cowsandbulls;

import android.app.Application;

import com.github.kiolk.cowsandbulls.data.repositories.game.DefaultGameRepository;
import com.github.kiolk.cowsandbulls.data.repositories.game.GameRepository;
import com.github.kiolk.cowsandbulls.data.repositories.game.remote.RemoteGameDataSource;

public class App extends Application {

    private static GameRepository gameRepository;


    public static GameRepository getGameRepository(){
        if(gameRepository == null){
            gameRepository = new DefaultGameRepository(new RemoteGameDataSource());
        }

        return gameRepository;
    }
}
