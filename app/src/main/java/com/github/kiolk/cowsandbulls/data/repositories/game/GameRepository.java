package com.github.kiolk.cowsandbulls.data.repositories.game;

import com.github.kiolk.cowsandbulls.data.PeriodType;
import com.github.kiolk.cowsandbulls.data.models.result.remote.ResultRemote;

import java.util.List;

public interface GameRepository {

    void publishResult(ResultRemote result);

    List<ResultRemote> getBestResults(PeriodType type);
}
