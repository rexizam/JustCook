package com.example.justcook.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.justcook.models.OnlineRecipe;
import com.example.justcook.models.Recipe;
import com.example.justcook.models.RecipeResponse;
import com.example.justcook.repository.RecipeRepository;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private RecipeRepository recipeRepository;
    private MutableLiveData<ArrayList<OnlineRecipe>> recipeList = new MutableLiveData<>();

    @ViewModelInject
    public HomeViewModel(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void insert(Recipe recipe)
    {
        recipeRepository.insert(recipe);
    }

    public MutableLiveData<ArrayList<OnlineRecipe>> getRecipeList() {
        return recipeList;
    }

    public void getRecipes(){
        recipeRepository.getRecipes()
                .subscribeOn(Schedulers.io())
                .map(new Function<RecipeResponse, ArrayList<OnlineRecipe>>() {
                    @Override
                    public ArrayList<OnlineRecipe> apply(RecipeResponse recipeResponse) throws Throwable {
                        return recipeResponse.getResults();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> recipeList.setValue(result),
                        error-> Log.e(TAG, "getRecipes: " + error.getMessage() ));
    }

    public void searchRecipesByTitle(String title)
    {
        recipeRepository.searchRecipesByTitle(title).subscribeOn(Schedulers.io())
                .map(new Function<RecipeResponse, ArrayList<OnlineRecipe>>() {
                    @Override
                    public ArrayList<OnlineRecipe> apply(RecipeResponse recipeResponse) throws Throwable {
                        return recipeResponse.getResults();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> recipeList.setValue(result),
                        error -> Log.e(TAG, "searchRecipesByTitle: " + error.getMessage() ));
    }
}
