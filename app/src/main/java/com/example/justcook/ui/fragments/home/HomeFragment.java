package com.example.justcook.ui.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;
import com.example.justcook.R;
import com.example.justcook.adapters.OnlineRecipeAdapter;
import com.example.justcook.models.OnlineRecipe;
import com.example.justcook.viewmodels.HomeViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    LottieAnimationView animationView;
    EditText searchTextField;
    ImageView searchButton;
    HomeViewModel viewModel;
    OnlineRecipeAdapter adapter;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        animationView = view.findViewById(R.id.lottieFoodCarousel);
        animationView.setRenderMode(RenderMode.SOFTWARE);
        searchTextField = view.findViewById(R.id.searchTitle);
        searchButton = view.findViewById(R.id.searchButton);
        recyclerView = view.findViewById(R.id.homeRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new OnlineRecipeAdapter(getContext());
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(view1 -> {
            if (!searchTextField.getText().toString().isEmpty())
            {
                viewModel.getRecipe(searchTextField.getText().toString());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getRecipes();
        observeData();
    }

    private void observeData()
    {
        viewModel.getRecipeList().observe(getViewLifecycleOwner(), new Observer<ArrayList<OnlineRecipe>>() {
            @Override
            public void onChanged(ArrayList<OnlineRecipe> onlineRecipes) {
                adapter.updateList(onlineRecipes);
            }
        });
    }
}