package com.github.kiolk.cowsandbulls.domain;

public interface ResultListener<B> {
    void onResult(B result);

    void onFailer(Exception ex);
}
