package com.example.justcook.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.justcook.models.OnlineRecipe;
import com.example.justcook.models.Recipe;
import com.example.justcook.repository.RecipeRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecipeDetailsViewModel extends ViewModel {

    private static final String TAG = "RecipeDetailViewModel";
    private RecipeRepository recipeRepository;
    private MutableLiveData<OnlineRecipe> recipe;

    @ViewModelInject
    public RecipeDetailsViewModel(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        recipe = new MutableLiveData<>();
    }

    public MutableLiveData<OnlineRecipe> getRecipe() {
        return recipe;
    }

    public void getRecipeDetails(int id)
    {
        recipeRepository.getRecipeById(id).subscribeOn(Schedulers.io())
                .map(new Function<OnlineRecipe, OnlineRecipe>() {
                    @Override
                    public OnlineRecipe apply(OnlineRecipe onlineRecipe) throws Throwable {
                        return onlineRecipe;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> recipe.setValue(result),
                        error -> Log.e(TAG, "getRecipeDetails: " + error.getMessage()));
    }

    public void insert(Recipe recipe)
    {
        recipeRepository.insert(recipe);
    }

    public void deleteRecipe(Recipe recipe)
    {
        recipeRepository.delete(recipe);
    }
}
