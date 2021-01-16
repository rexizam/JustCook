package com.example.justcook.network;

import com.example.justcook.models.OnlineRecipe;
import com.example.justcook.models.RecipeResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApiService {

    @GET("recipes/complexSearch")
    Observable<RecipeResponse> searchRecipesByTitle(@Query("apiKey") String key, @Query("query") String title);

    @GET("recipes/{id}/information")
    Observable<OnlineRecipe> getRecipeById(@Path("id") int id, @Query("apiKey") String key);

    @GET("recipes/complexSearch")
    Observable<RecipeResponse> getRecipes(@Query("apiKey") String key);
}
