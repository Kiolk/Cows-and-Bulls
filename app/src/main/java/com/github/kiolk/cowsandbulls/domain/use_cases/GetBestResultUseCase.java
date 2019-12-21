package com.github.kiolk.cowsandbulls.domain.use_cases;

import com.github.kiolk.cowsandbulls.App;
import com.github.kiolk.cowsandbulls.data.PeriodType;
import com.github.kiolk.cowsandbulls.data.models.result.Result;
import com.github.kiolk.cowsandbulls.data.models.result.remote.ResultRemote;
import com.github.kiolk.cowsandbulls.domain.ResultListener;
import com.github.kiolk.cowsandbulls.domain.TaskExecutor;

import java.util.ArrayList;
import java.util.List;

public class GetBestResultUseCase extends TaskExecutor<GetBestResultUseCase.Params, List<Result>> {

    public GetBestResultUseCase(Params params, ResultListener<List<Result>> listener) {
        super(params, listener);
    }

    @Override
    public List<Result> onExecute() {

        List<ResultRemote> resultsRemote = App.getGameRepository().getBestResults(params.type);
        List<Result> results = new ArrayList<>();

        for(ResultRemote result: resultsRemote){
            results.add(ResultRemote.toResult(result));
        }
        return results;
    }

    public static class Params{
        PeriodType type;

        public Params(PeriodType type) {
            this.type = type;
        }
    }
}
