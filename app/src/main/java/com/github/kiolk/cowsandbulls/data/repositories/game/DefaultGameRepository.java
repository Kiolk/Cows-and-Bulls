package com.github.kiolk.cowsandbulls.data.repositories.game;

import com.github.kiolk.cowsandbulls.data.models.GameResultRemote;

public class DefaultGameRepository implements GameRepository {

    private GameDataSource remote;

    public DefaultGameRepository(GameDataSource remote) {
        this.remote = remote;
    }

    @Override
    public void publishResult(GameResultRemote result) {
        remote.publishResult(result);
    }
}
