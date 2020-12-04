package com.example.justcook.models;

import java.util.ArrayList;

public class RecipeResponse {

    private ArrayList<OnlineRecipe> results;

    public RecipeResponse(ArrayList<OnlineRecipe> results) {
        this.results = results;
    }

    public void setResults(ArrayList<OnlineRecipe> results) {
        this.results = results;
    }

    public ArrayList<OnlineRecipe> getResults() {
        return results;
    }
}
