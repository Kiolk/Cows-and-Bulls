package com.github.kiolk.cowsandbulls.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GameResult implements Parcelable {

    private int moves;

    private long time;

    public GameResult(int moves, long time) {
        this.moves = moves;
        this.time = time;
    }

    public GameResult() {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.moves);
        dest.writeLong(this.time);
    }

    protected GameResult(Parcel in) {
        this.moves = in.readInt();
        this.time = in.readLong();
    }

    public static final Creator<GameResult> CREATOR = new Creator<GameResult>() {
        @Override
        public GameResult createFromParcel(Parcel source) {
            return new GameResult(source);
        }

        @Override
        public GameResult[] newArray(int size) {
            return new GameResult[size];
        }
    };
}
