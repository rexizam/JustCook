package com.example.justcook.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {

    private int number;
    private String step;

    public Step() {
    }

    public Step(int number, String step) {
        this.number = number;
        this.step = step;
    }

    protected Step(Parcel in) {
        number = in.readInt();
        step = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(number);
        parcel.writeString(step);
    }
}
