package com.example.justcook.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Recipe implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String category;
    private List<ExtendedIngredient> extendedIngredients;
    private List<AnalyzedInstruction> analyzedInstructions;
    private byte[] picture;

    @Ignore
    public Recipe() {
    }

    public Recipe(String name, String category,
                  List<ExtendedIngredient> extendedIngredients,
                  List<AnalyzedInstruction> analyzedInstructions, byte[] picture) {
        this.name = name;
        this.category = category;
        this.extendedIngredients = extendedIngredients;
        this.analyzedInstructions = analyzedInstructions;
        this.picture = picture;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readString();
        extendedIngredients = in.createTypedArrayList(ExtendedIngredient.CREATOR);
        analyzedInstructions = in.createTypedArrayList(AnalyzedInstruction.CREATOR);
        picture = in.createByteArray();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExtendedIngredients(List<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public void setAnalyzedInstructions(List<AnalyzedInstruction> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public byte[] getPicture() {
        return picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeTypedList(extendedIngredients);
        parcel.writeTypedList(analyzedInstructions);
        parcel.writeByteArray(picture);
    }
}
