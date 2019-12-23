package com.github.kiolk.cowsandbulls.data.models.result;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {

    private int moves;

    private long time;

    private String userName;

    private String date;

    private String uuid;

    private boolean isOwn;

    public Result() {
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

    public boolean isOwn() {
        return isOwn;
    }

    public void setOwn(boolean own) {
        isOwn = own;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.moves);
        dest.writeLong(this.time);
        dest.writeString(this.userName);
        dest.writeString(this.date);
        dest.writeString(this.uuid);
        dest.writeByte(this.isOwn ? (byte) 1 : (byte) 0);
    }

    protected Result(Parcel in) {
        this.moves = in.readInt();
        this.time = in.readLong();
        this.userName = in.readString();
        this.date = in.readString();
        this.uuid = in.readString();
        this.isOwn = in.readByte() != 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
