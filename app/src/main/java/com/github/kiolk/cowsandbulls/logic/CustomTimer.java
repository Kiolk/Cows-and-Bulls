package com.github.kiolk.cowsandbulls.logic;

import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {
    private static int DELAY = 1000;
    private static int PERIOD = 1000;
    private static int INIT_TIME = 0;
    private static String TIME_KEY = "TIME";
    private static String IS_RUNNING_KEY = "IS_RUNNING";

    private long time;
    private Timer timer;
    private TimerTask timerTask;
    private boolean isRunning = false;
    private TimerChange timerChange;

    public CustomTimer(TimerChange timerChangeListener) {
        this.timerChange = timerChangeListener;
        this.time = INIT_TIME;
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            private long currentTime = INIT_TIME;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;
                timerChange.updateView(getTime());
            }
        };
    }

    public CustomTimer(TimerChange timerChangeListener, long restoreTime,boolean isRunning){
        this.isRunning=isRunning;
        this.timerChange=timerChangeListener;
        this.time=restoreTime;

        this.timer=new Timer();
        this.timerTask = new TimerTask() {
            private long currentTime = time;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;
                timerChange.updateView(getTime());
            }
        };
        if(isRunning){
            this.timer.scheduleAtFixedRate(timerTask, DELAY, PERIOD);
        }
        else{
            timerChange.updateView(getTime());
        }
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            this.timer.scheduleAtFixedRate(timerTask, DELAY, PERIOD);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void reset() {
        this.isRunning = false;
        this.timerTask.cancel();
        this.time = INIT_TIME;
        this.timerChange.updateView(new SimpleDateFormat("mm:ss", Locale.getDefault()).format(0));
        this.timerTask = new TimerTask() {
            private long currentTime = INIT_TIME;

            @Override
            public void run() {
                currentTime++;
                time = currentTime;
                timerChange.updateView(getTime());
            }
        };


    }

    public Bundle saveState(Bundle bundle) {
        bundle.putLong(TIME_KEY, this.time);
        bundle.putBoolean(IS_RUNNING_KEY, this.isRunning);
        return bundle;
    }

    static public CustomTimer restoreTimer(Bundle bundle, TimerChange listener) {
        long time = bundle.getLong(TIME_KEY);
        listener.updateView(new SimpleDateFormat("mm:ss", Locale.getDefault()).format(time * 1000));
        return new CustomTimer(listener, time, bundle.getBoolean(IS_RUNNING_KEY));
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

    public long getTimeLong(){
        return this.time;
    }

}
