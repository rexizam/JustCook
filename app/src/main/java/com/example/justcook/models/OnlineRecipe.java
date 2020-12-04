package com.example.justcook.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OnlineRecipe implements Parcelable {

    private List<ExtendedIngredient> extendedIngredients;
    private int id;
    private String title;
    private String image;
    private List<AnalyzedInstruction> analyzedInstructions;

    public OnlineRecipe() {
    }

    public OnlineRecipe(int id, String title, String image,
                        List<AnalyzedInstruction> analyzedInstructions,
                        List<ExtendedIngredient> extendedIngredients) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.analyzedInstructions = analyzedInstructions;
        this.extendedIngredients = extendedIngredients;
    }

    protected OnlineRecipe(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        analyzedInstructions = in.createTypedArrayList(AnalyzedInstruction.CREATOR);
        extendedIngredients = in.createTypedArrayList(ExtendedIngredient.CREATOR);
    }

    public static final Creator<OnlineRecipe> CREATOR = new Creator<OnlineRecipe>() {
        @Override
        public OnlineRecipe createFromParcel(Parcel in) {
            return new OnlineRecipe(in);
        }

        @Override
        public OnlineRecipe[] newArray(int size) {
            return new OnlineRecipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public void setAnalyzedInstructions(List<AnalyzedInstruction> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeTypedList(analyzedInstructions);
        parcel.writeTypedList(extendedIngredients);
    }
}
