package com.example.justcook.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.example.justcook.models.Recipe;
import com.example.justcook.repository.RecipeRepository;

public class AddRecipeViewModel extends ViewModel {

    private RecipeRepository recipeRepository;

    @ViewModelInject
    public AddRecipeViewModel(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void insert(Recipe recipe)
    {
        recipeRepository.insert(recipe);
    }
}
