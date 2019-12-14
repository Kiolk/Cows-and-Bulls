package com.github.kiolk.cowsandbulls.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Move implements Parcelable {

    private String value;
    private int amountOfCows;
    private int amountOfBulls;

    public Move(String value, int amountOfCows, int amountOfBulls) {
        this.value = value;
        this.amountOfCows = amountOfCows;
        this.amountOfBulls = amountOfBulls;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getAmountOfCows() {
        return amountOfCows;
    }

    public void setAmountOfCows(int amountOfCows) {
        this.amountOfCows = amountOfCows;
    }

    public int getAmountOfBulls() {
        return amountOfBulls;
    }

    public void setAmountOfBulls(int amountOfBulls) {
        this.amountOfBulls = amountOfBulls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
        dest.writeInt(this.amountOfCows);
        dest.writeInt(this.amountOfBulls);
    }

    protected Move(Parcel in) {
        this.value = in.readString();
        this.amountOfCows = in.readInt();
        this.amountOfBulls = in.readInt();
    }

    public static final Creator<Move> CREATOR = new Creator<Move>() {
        @Override
        public Move createFromParcel(Parcel source) {
            return new Move(source);
        }

        @Override
        public Move[] newArray(int size) {
            return new Move[size];
        }
    };
}
