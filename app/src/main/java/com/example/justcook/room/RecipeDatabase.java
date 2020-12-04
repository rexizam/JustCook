package com.example.justcook.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.justcook.models.Recipe;
import com.example.justcook.util.Converter;

@Database(entities = {Recipe.class}, version = 8)
@TypeConverters({Converter.class})
public abstract class RecipeDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();
}
