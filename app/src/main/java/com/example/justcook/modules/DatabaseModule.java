package com.example.justcook.modules;

import android.app.Application;

import androidx.room.Room;

import com.example.justcook.room.RecipeDao;
import com.example.justcook.room.RecipeDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static RecipeDatabase provideRecipeDatabase(Application application){
        return Room.databaseBuilder(application, RecipeDatabase.class, "Favorite Database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static RecipeDao provideRecipeDao(RecipeDatabase recipeDatabase){
        return recipeDatabase.recipeDao();
    }
}
