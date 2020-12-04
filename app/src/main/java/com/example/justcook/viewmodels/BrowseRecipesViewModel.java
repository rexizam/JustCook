package com.example.justcook.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.justcook.models.Recipe;
import com.example.justcook.repository.RecipeRepository;

import java.util.List;

public class BrowseRecipesViewModel extends ViewModel {

    private RecipeRepository recipeRepository;

    @ViewModelInject
    public BrowseRecipesViewModel(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return recipeRepository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getRecipesByCategory(String category)
    {
        return recipeRepository.getRecipesByCategory(category);
    }
}
