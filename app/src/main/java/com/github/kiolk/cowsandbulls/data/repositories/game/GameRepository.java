package com.github.kiolk.cowsandbulls.data.repositories.game;

import com.github.kiolk.cowsandbulls.data.models.GameResultRemote;

public interface GameRepository {

    void publishResult(GameResultRemote result);
}
