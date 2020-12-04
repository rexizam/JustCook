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

import com.bumptech.glide.Glide;
import com.example.justcook.R;
import com.example.justcook.models.OnlineRecipe;
import com.example.justcook.ui.activities.RecipeDetailActivity;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;

public class OnlineRecipeAdapter extends RecyclerView.Adapter<OnlineRecipeAdapter.OnlineRecipeHolder> {

    private static final String TAG = "OnlineRecipeListAdapter";
    private Context context;
    private ArrayList<OnlineRecipe> onlineRecipes = new ArrayList<>();

    public OnlineRecipeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OnlineRecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.online_recipe_item, parent, false);
        return new OnlineRecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineRecipeHolder holder, int position) {

        OnlineRecipe currentRecipe = onlineRecipes.get(position);
        holder.title.setText(currentRecipe.getTitle());
        Glide.with(context).load(currentRecipe.getImage()).into(holder.image);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.load_up_anim));

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("OnlineRecipeDetails", currentRecipe.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return onlineRecipes.size();
    }

    public class OnlineRecipeHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private RoundedImageView image;
        private CardView cardView;

        public OnlineRecipeHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.onlineRecipeTitle);
            image = itemView.findViewById(R.id.onlineRecipeImg);
            cardView = itemView.findViewById(R.id.onlineRecipeCard);
        }
    }

    public void updateList(ArrayList<OnlineRecipe> onlineRecipes)
    {
        this.onlineRecipes = onlineRecipes;
        notifyDataSetChanged();
    }
}
