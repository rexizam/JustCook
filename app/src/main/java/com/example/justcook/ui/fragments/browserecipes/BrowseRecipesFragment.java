package com.example.justcook.ui.fragments.browserecipes;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justcook.R;
import com.example.justcook.adapters.RecipeAdapter;
import com.example.justcook.viewmodels.BrowseRecipesViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import toan.android.floatingactionmenu.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowseRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
public class BrowseRecipesFragment extends Fragment {

    FloatingActionButton fabAppetizers;
    FloatingActionButton fabSalads;
    FloatingActionButton fabSoups;
    FloatingActionButton fabMainCourse;
    FloatingActionButton fabPizzas;
    FloatingActionButton fabDesserts;
    FloatingActionButton fabAll;
    BrowseRecipesViewModel viewModel;
    RecyclerView recyclerView;
    RecipeAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BrowseRecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrowseRecipes.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowseRecipesFragment newInstance(String param1, String param2) {
        BrowseRecipesFragment fragment = new BrowseRecipesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse_recipes, container, false);

        CoordinatorLayout layout = view.findViewById(R.id.coordinatorBackground);
        AnimationDrawable animationDrawable = (AnimationDrawable)layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        recyclerView = view.findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new RecipeAdapter(getContext());
        recyclerView.setAdapter(adapter);

        fabAppetizers = view.findViewById(R.id.fab_appetizers);
        fabAppetizers.setOnClickListener(view1 -> viewModel.getRecipesByCategory("Appetisers")
                .observe(getViewLifecycleOwner(), recipes -> {
                    adapter.setRecipes(recipes);
                    layoutAnimation(recyclerView);
                }));

        fabDesserts = view.findViewById(R.id.fab_desserts);
        fabDesserts.setOnClickListener(view1 -> viewModel.getRecipesByCategory("Desserts")
                .observe(getViewLifecycleOwner(), recipes -> {
                    adapter.setRecipes(recipes);
                    layoutAnimation(recyclerView);
                }));

        fabMainCourse = view.findViewById(R.id.fab_main_course);
        fabMainCourse.setOnClickListener(view1 -> viewModel.getRecipesByCategory("Main course")
                .observe(getViewLifecycleOwner(), recipes -> {
                    adapter.setRecipes(recipes);
                    layoutAnimation(recyclerView);
                }));

        fabPizzas = view.findViewById(R.id.fab_pizzas);
        fabPizzas.setOnClickListener(view1 -> viewModel.getRecipesByCategory("Pizzas")
                .observe(getViewLifecycleOwner(), recipes -> {
                    adapter.setRecipes(recipes);
                    layoutAnimation(recyclerView);
                }));

        fabSalads = view.findViewById(R.id.fab_salads);
        fabSalads.setOnClickListener(view1 -> viewModel.getRecipesByCategory("Salads")
                .observe(getViewLifecycleOwner(), recipes -> {
                    adapter.setRecipes(recipes);
                    layoutAnimation(recyclerView);
                }));

        fabSoups = view.findViewById(R.id.fab_soups);
        fabSoups.setOnClickListener(view1 -> viewModel.getRecipesByCategory("Soups")
                .observe(getViewLifecycleOwner(), recipes -> {
                    adapter.setRecipes(recipes);
                    layoutAnimation(recyclerView);
                }));

        fabAll = view.findViewById(R.id.fab_all);
        fabAll.setOnClickListener(view1 -> viewModel.getAllRecipes()
        .observe(getViewLifecycleOwner(), recipes -> {
            adapter.setRecipes(recipes);
            layoutAnimation(recyclerView);
        }));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BrowseRecipesViewModel.class);
        viewModel.getAllRecipes().observe(getViewLifecycleOwner(), recipes -> {
            adapter.setRecipes(recipes);
            layoutAnimation(recyclerView);
        });
    }

    private void layoutAnimation(RecyclerView recyclerView)
    {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}