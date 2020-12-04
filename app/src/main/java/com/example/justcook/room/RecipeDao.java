package com.example.justcook.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.justcook.models.Recipe;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    @Insert(onConflict = REPLACE)
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM Recipe")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM Recipe WHERE category = :category")
    LiveData<List<Recipe>> getRecipesByCategory(String category);

    @Query("SELECT * FROM Recipe WHERE id = :id")
    Recipe getRecipe(int id);

    @Query("SELECT COUNT(*) FROM Recipe")
    int getCount();
}
