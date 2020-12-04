package com.example.justcook.di;

import android.content.Context;

import androidx.room.Room;

import com.example.justcook.room.RecipeDatabase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public class TestAppModule {

    @Provides
    @Named("test_db")
    public RecipeDatabase provideMemoryDb(@ApplicationContext Context context)
    {
        return Room.inMemoryDatabaseBuilder(context, RecipeDatabase.class).allowMainThreadQueries().build();
    }
}
