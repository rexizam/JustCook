package com.example.justcook.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.justcook.R;
import com.example.justcook.adapters.IngredientListAdapter;
import com.example.justcook.adapters.InstructionListAdapter;
import com.example.justcook.models.ExtendedIngredient;
import com.example.justcook.models.OnlineRecipe;
import com.example.justcook.models.Recipe;
import com.example.justcook.models.Step;
import com.example.justcook.util.Converter;
import com.example.justcook.viewmodels.RecipeDetailsViewModel;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RecipeDetailActivity extends AppCompatActivity {

    RoundedImageView imageView;
    LottieAnimationView deleteButton;
    LottieAnimationView favouriteButton;
    TextView recipeTitle;
    ListView ingredients;
    ListView instructions;
    IngredientListAdapter ingredientsAdapter;
    InstructionListAdapter instructionListAdapter;
    RecipeDetailsViewModel viewModel;
    OnlineRecipe recipe;
    Recipe dbRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_activity);

        viewModel = new ViewModelProvider(this).get(RecipeDetailsViewModel.class);

        imageView = findViewById(R.id.recipeDetailImg);
        imageView.setDrawingCacheEnabled(true);
        recipeTitle = findViewById(R.id.recipeDetailTitle);
        ingredients = findViewById(R.id.recipeIngredients);
        instructions = findViewById(R.id.recipeInstructions);
        deleteButton = findViewById(R.id.deleteRecipe);
        favouriteButton = findViewById(R.id.saveRecipe);

        ingredientsAdapter = new IngredientListAdapter(this,
                R.layout.ingredient_adapter_view_layout);
        ingredients.setAdapter(ingredientsAdapter);

        instructionListAdapter = new InstructionListAdapter(this,
                R.layout.instruction_adapter_view_layout);
        instructions.setAdapter(instructionListAdapter);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("RecipeDetails"))
        {
            favouriteButton.setVisibility(View.GONE);

            dbRecipe = bundle.getParcelable("RecipeDetails");
            ingredientsAdapter.setIngredientList((ArrayList<ExtendedIngredient>)dbRecipe.getExtendedIngredients());
            instructionListAdapter.setInstructionList((ArrayList<Step>)dbRecipe.getAnalyzedInstructions().get(0).getSteps());
            recipeTitle.setText(dbRecipe.getName());
            imageView.setImageBitmap(Converter.byteArrayToBitMap(dbRecipe.getPicture()));

            setListViewHeightBasedOnChildren(ingredients);
            setListViewHeightBasedOnChildren(instructions);
        }

        if (bundle != null && bundle.containsKey("OnlineRecipeDetails"))
        {
            deleteButton.setVisibility(View.GONE);

            int recipeId = bundle.getInt("OnlineRecipeDetails");
            System.out.println(recipeId + "=================================================================================================");
            viewModel.getRecipeDetails(recipeId);

            viewModel.getRecipe().observe(this, onlineRecipe -> {

                recipe = onlineRecipe;

                ingredientsAdapter.setIngredientList((ArrayList<ExtendedIngredient>)recipe.getExtendedIngredients());
                instructionListAdapter.setInstructionList((ArrayList<Step>)recipe.getAnalyzedInstructions().get(0).getSteps());

                setListViewHeightBasedOnChildren(ingredients);
                setListViewHeightBasedOnChildren(instructions);

                recipeTitle.setText(recipe.getTitle());
                Glide.with(getApplicationContext()).load(recipe.getImage()).into(imageView);
            });
        }

        deleteButton.setOnClickListener(view -> {

            viewModel.deleteRecipe(dbRecipe);
            deleteButton.setSpeed(1);
            deleteButton.playAnimation();
            Toast toast = Toast.makeText(view.getContext(), "Recipe deleted", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        });

        favouriteButton.setOnClickListener(view -> {

            Recipe toBeSaved = new Recipe();
            toBeSaved.setName(recipe.getTitle());
            toBeSaved.setPicture(Converter.bitMapToByteArray(imageView.getDrawingCache()));
            toBeSaved.setAnalyzedInstructions(recipe.getAnalyzedInstructions());
            toBeSaved.setExtendedIngredients(recipe.getExtendedIngredients());

            viewModel.insert(toBeSaved);

            favouriteButton.setSpeed(1);
            favouriteButton.playAnimation();
            Toast toast = Toast.makeText(view.getContext(), "Recipe saved", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        });
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}