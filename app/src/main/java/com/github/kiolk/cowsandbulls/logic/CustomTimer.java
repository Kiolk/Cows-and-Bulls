package com.github.kiolk.cowsandbulls.logic;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {
    private long time;
    private Timer timer;
    private TimerTask timerTask;
    private boolean isRunning = false;

    public CustomTimer() {
        this.time = 0;
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            private volatile long currentTime = 0;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;
            }
        };
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            this.timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }


    }

    public String reset() {
        this.isRunning = false;
        this.timerTask.cancel();
        this.timerTask = new TimerTask() {
            private volatile long currentTime = 0;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;
            }
        };
        return getTime();


    }

    public String stop() {
        this.isRunning = false;
        this.timerTask.cancel();
        this.timerTask = new TimerTask() {
            private volatile long currentTime = time;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;
            }
        };
        return getTime();
    }

    public String getTime() {
        Log.d("tag", String.valueOf(time));
        return String.format("%02d:%02d", this.time / 60, this.time % 60);
    }

}
