package com.github.kiolk.cowsandbulls.domain.use_cases;

import com.github.kiolk.cowsandbulls.App;
import com.github.kiolk.cowsandbulls.domain.ResultListener;
import com.github.kiolk.cowsandbulls.domain.TaskExecutor;

public class SetDeviceToken extends TaskExecutor<SetDeviceToken.Params, Void> {

    public SetDeviceToken(Params params, ResultListener<Void> listener) {
        super(params, listener);
    }

    @Override
    public Void onExecute() {
        App.getSettingsRepository().setDeviceToken(params.deviceToke);
        return null;
    }

    public static class Params {
        String deviceToke;

        public Params(String deviceToke) {
            this.deviceToke = deviceToke;
        }
    }
}
