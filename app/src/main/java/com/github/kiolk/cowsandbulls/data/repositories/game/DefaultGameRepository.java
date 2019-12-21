package com.github.kiolk.cowsandbulls.data.repositories.game;

import com.github.kiolk.cowsandbulls.data.PeriodType;
import com.github.kiolk.cowsandbulls.data.models.result.remote.ResultRemote;

import java.util.List;

public class DefaultGameRepository implements GameRepository {

    private GameDataSource remote;

    public DefaultGameRepository(GameDataSource remote) {
        this.remote = remote;
    }

    @Override
    public void publishResult(ResultRemote result) {
        remote.publishResult(result);
    }

    @Override
    public List<ResultRemote> getBestResults(PeriodType type) {
        return remote.getBestResults(type);
    }
}
