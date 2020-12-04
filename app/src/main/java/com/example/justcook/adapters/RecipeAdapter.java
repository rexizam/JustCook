package com.example.justcook.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justcook.R;
import com.example.justcook.models.Recipe;
import com.example.justcook.ui.activities.RecipeDetailActivity;
import com.example.justcook.util.Converter;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    Context context;
    private List<Recipe> recipes = new ArrayList<>();

    public RecipeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item_layout, parent, false);
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe currentRecipe = recipes.get(position);
        holder.title.setText(currentRecipe.getName());
        holder.image.setImageBitmap(Converter.byteArrayToBitMap(currentRecipe.getPicture()));
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.item_fall_down));

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("RecipeDetails", currentRecipe);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes)
    {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private RoundedImageView image;
        private CardView cardView;

        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.recipeName);
            image = itemView.findViewById(R.id.recipeImg);
        }
    }
}
