package com.github.kiolk.cowsandbulls.domain.use_cases;

import com.github.kiolk.cowsandbulls.App;
import com.github.kiolk.cowsandbulls.data.models.result.remote.ResultRemote;
import com.github.kiolk.cowsandbulls.domain.ResultListener;
import com.github.kiolk.cowsandbulls.domain.TaskExecutor;

public class PublishUseCase extends TaskExecutor<PublishUseCase.Params, Void> {

    public PublishUseCase(Params params, ResultListener<Void> listener) {
        super(params, listener);
    }

    @Override
    public Void onExecute() {
        ResultRemote resultRemote = params.result;
        resultRemote.setDeviceToken(App.getSettingsRepository().getDeviceToken());
        App.getGameRepository().publishResult(resultRemote);
        return null;
    }

    public static class Params{

        ResultRemote result;

        public Params(ResultRemote result) {
            this.result = result;
        }
    }
}
