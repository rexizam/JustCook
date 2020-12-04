package com.example.justcook.repository;

import androidx.lifecycle.LiveData;

import com.example.justcook.models.OnlineRecipe;
import com.example.justcook.models.Recipe;
import com.example.justcook.models.RecipeResponse;
import com.example.justcook.network.RecipeApiService;
import com.example.justcook.room.RecipeDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class RecipeRepository {

    private RecipeDao recipeDao;
    private RecipeApiService recipeApiService;
    private final String apiKey = "9369843ac03543d2921e0744c0eb494b";

    @Inject
    public RecipeRepository(RecipeDao recipeDao, RecipeApiService recipeApiService) {
        this.recipeDao = recipeDao;
        this.recipeApiService = recipeApiService;
    }

    public Observable<RecipeResponse> getRecipes(){
        return recipeApiService.getRecipes(apiKey);
    }

    public Observable<RecipeResponse> getRecipe(String title)
    {
        return recipeApiService.getRecipe(apiKey ,title);
    }

    public Observable<OnlineRecipe> getRecipeById(int id)
    {
        return recipeApiService.getRecipeById(id, apiKey);
    }

    public LiveData<List<Recipe>> getRecipesByCategory(String category)
    {
        return recipeDao.getRecipesByCategory(category);
    }

    public Recipe getRecipe(int id)
    {
        return recipeDao.getRecipe(id);
    }

    public void insert(Recipe recipe)
    {
        recipeDao.insert(recipe);
    }

    public void update(Recipe recipe)
    {
        recipeDao.update(recipe);
    }

    public void delete(Recipe recipe)
    {
        recipeDao.delete(recipe);
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return recipeDao.getRecipes();
    }
}
