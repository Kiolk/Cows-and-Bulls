package com.github.kiolk.cowsandbulls.logic;

import android.util.Log;


import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {
    private long time;
    private Timer timer;
    private TimerTask timerTask;
    private boolean isRunning = false;
    private TimerChange timerChange;

    public CustomTimer(TimerChange timerChangeListener) {
        this.timerChange = timerChangeListener;
        this.time = -1;
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            private volatile long currentTime = -1;

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
            this.timer.scheduleAtFixedRate(timerTask, 0, 1000);

        }


    }

    public void reset() {
        this.isRunning = false;
        this.timerTask.cancel();
        this.time=-1;
        this.timerChange.updateView(String.format("%02d:%02d", 0 / 60, 0 % 60));
        this.timerTask = new TimerTask() {
            private volatile long currentTime = -1;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;
                timerChange.updateView(getTime());

            }
        };


    }

    public void stop() {
        this.isRunning = false;
        this.timerTask.cancel();
        this.timerTask = new TimerTask() {
            private volatile long currentTime = time;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;

                timerChange.updateView(getTime());

            }
        };

    }

    public String getTime() {
        Log.d("tag", String.valueOf(time));
        return String.format("%02d:%02d", this.time / 60, this.time % 60);
    }


}
