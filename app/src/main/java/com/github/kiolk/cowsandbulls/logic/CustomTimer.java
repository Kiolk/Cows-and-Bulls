package com.github.kiolk.cowsandbulls.logic;

import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {
    private long time;
    private Timer timer;
    private TimerTask timerTask;
    private boolean isRunning = false;
    private TimerChange timerChange;

    private int delay = 0;
    private int period = 1000;

    private String TIME_KEY = "time";
    private String IS_RUNNING_KEY = "isRunning";

    public CustomTimer(TimerChange timerChangeListener) {
        this.timerChange = timerChangeListener;
        this.time = -1;
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            private long currentTime = -1;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;
                timerChange.updateView(getTime());
            }
        };
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            this.timer.scheduleAtFixedRate(timerTask, delay, period);
        }
    }

    public void reset() {
        this.isRunning = false;
        this.timerTask.cancel();
        this.time = -1;
        this.timerChange.updateView(new SimpleDateFormat("mm:ss", Locale.getDefault()).format(0));
        this.timerTask = new TimerTask() {
            private long currentTime = -1;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;
                timerChange.updateView(getTime());
            }
        };
    }

    public Bundle saveState(Bundle bundle) {
        bundle.putLong(this.TIME_KEY, this.time);
        bundle.putBoolean(this.IS_RUNNING_KEY, this.isRunning);
        return bundle;
    }

    public CustomTimer restoreTimer(Bundle bundle, TimerChange listener) {
        this.time = bundle.getLong(this.TIME_KEY);
        this.isRunning = bundle.getBoolean(this.IS_RUNNING_KEY);
        return new CustomTimer(listener);
    }

    public void stop() {
        this.isRunning = false;
        this.timerTask.cancel();
        this.timerTask = new TimerTask() {
            private long currentTime = time;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;

                timerChange.updateView(getTime());
            }
        };
    }

    public String getTime() {
        return new SimpleDateFormat("mm:ss", Locale.getDefault()).format(time * 1000);
    }

}
