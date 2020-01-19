package com.github.kiolk.cowsandbulls.domain;

import android.os.AsyncTask;

public abstract class TaskExecutor<T, B> extends AsyncTask<T, Void, ResultHandler> {

    protected T params;
    private ResultListener<B> listener;

    public TaskExecutor(T params, ResultListener<B> listener) {
        this.params = params;
        this.listener = listener;
    }

    public abstract B onExecute();

    public TaskExecutor start() {
        execute(params);
        return this;
    }

    @Override
    protected ResultHandler doInBackground(T... ts) {
        ResultHandler<B> handler = new ResultHandler<B>();

        try {
            handler.result = onExecute();
        } catch (Exception ex) {
            handler.exception = ex;
        }

        return handler;
    }

    @Override
    protected void onPostExecute(ResultHandler resultHandler) {
        super.onPostExecute(resultHandler);
        if(listener == null){
            return;
        }

        if(resultHandler.exception == null && resultHandler.result != null){
            listener.onResult((B) resultHandler.result);
        }else{
            listener.onFailer(resultHandler.exception);
        }
    }
}

class ResultHandler<B> {
    B result;

    Exception exception;

    public B getResult() {
        return result;
    }

    public void setResult(B result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
