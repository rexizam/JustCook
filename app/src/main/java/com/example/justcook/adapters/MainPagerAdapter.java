package com.example.justcook.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.justcook.ui.fragments.addrecipe.AddRecipeFragment;
import com.example.justcook.ui.fragments.browserecipes.BrowseRecipesFragment;
import com.example.justcook.ui.fragments.home.HomeFragment;
import com.example.justcook.ui.fragments.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        fragmentList.add(new HomeFragment());
        fragmentList.add(new BrowseRecipesFragment());
        fragmentList.add(new AddRecipeFragment());
        fragmentList.add(new ProfileFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
