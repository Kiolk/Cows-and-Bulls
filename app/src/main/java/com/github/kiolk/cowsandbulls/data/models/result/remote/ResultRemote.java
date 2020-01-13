package com.github.kiolk.cowsandbulls.data.models.result.remote;

import com.github.kiolk.cowsandbulls.data.models.result.Result;

public class ResultRemote {

    private int moves;

    private long time;

    private String userName;

    private String date;

    private String uuid;

    private String deviceToken;

    public ResultRemote() {
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public static Result toResult(ResultRemote resultRemote){
        Result result = new Result();
        result.setDate(resultRemote.getDate());
        result.setMoves(resultRemote.getMoves());
        result.setUserName(resultRemote.getUserName());
        result.setTime(resultRemote.getTime());
        result.setUuid(resultRemote.getUuid());
        result.setOwn(false);
        return result;
    }
}
