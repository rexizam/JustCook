package com.example.justcook.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import com.example.justcook.models.AnalyzedInstruction;
import com.example.justcook.models.ExtendedIngredient;
import com.example.justcook.models.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converter {

    @TypeConverter
    public static List<ExtendedIngredient> stringToIngredientList(String data) {
        Gson gson = new Gson();

        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ExtendedIngredient>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ingredientListToString(List<ExtendedIngredient> extendedIngredients) {
        Gson gson = new Gson();
        return gson.toJson(extendedIngredients);
    }

    @TypeConverter
    public static List<Step> stringToStepList(String data) {
        Gson gson = new Gson();

        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Step>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String stepListToString(List<Step> steps) {
        Gson gson = new Gson();
        return gson.toJson(steps);
    }

    @TypeConverter
    public static List<AnalyzedInstruction> stringToInstructionList(String data) {
        Gson gson = new Gson();

        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<AnalyzedInstruction>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String instructionListToString(List<AnalyzedInstruction> analyzedInstructions) {
        Gson gson = new Gson();
        return gson.toJson(analyzedInstructions);
    }

    @TypeConverter
    public static byte[] bitMapToByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    @TypeConverter
    public static Bitmap byteArrayToBitMap(byte[] data)
    {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
