package com.github.kiolk.cowsandbulls;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class ReleaseApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
    }
}
