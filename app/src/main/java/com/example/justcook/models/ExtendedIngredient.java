package com.example.justcook.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ExtendedIngredient implements Parcelable {

    private int id;
    private String name;
    private double amount;
    private String unit;

    public ExtendedIngredient() {
    }

    public ExtendedIngredient(int id, String name, double amount, String unit) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public ExtendedIngredient(String name, double amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    protected ExtendedIngredient(Parcel in) {
        id = in.readInt();
        name = in.readString();
        amount = in.readDouble();
        unit = in.readString();
    }

    public static final Creator<ExtendedIngredient> CREATOR = new Creator<ExtendedIngredient>() {
        @Override
        public ExtendedIngredient createFromParcel(Parcel in) {
            return new ExtendedIngredient(in);
        }

        @Override
        public ExtendedIngredient[] newArray(int size) {
            return new ExtendedIngredient[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(amount);
        parcel.writeString(unit);
    }
}
