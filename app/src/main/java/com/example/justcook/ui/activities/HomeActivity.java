package com.example.justcook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.justcook.R;
import com.example.justcook.adapters.MainPagerAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import me.ibrahimsn.lib.SmoothBottomBar;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {

    SmoothBottomBar bottomBar;
    ViewPager2 viewPager;
    MainPagerAdapter adapter;
    private static final int RC_SIGN_IN = 41;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        checkIfSignedIn();

        initializeUI();
        configureViewPager();

        if (savedInstanceState != null)
        {
            int index = savedInstanceState.getInt("MenuSelection");
            bottomBar.setItemActiveIndex(index);
        }

        bottomBar.setOnItemSelectedListener(i -> {
            viewPager.setCurrentItem(i);
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        NavController navController = Navigation.findNavController(this,  R.id.bottom_bar);
        bottomBar.setupWithNavController(menu, navController);
        return super.onCreateOptionsMenu(menu);
    }

    private void initializeUI() {
        bottomBar = findViewById(R.id.bottom_bar);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setUserInputEnabled(false);
    }

    private void configureViewPager() {
        adapter = new MainPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }

    private void checkIfSignedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
        {
            signIn();
        }
    }

    public void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInRequest(int resultCode) {
        if (resultCode == RESULT_CANCELED)
        {
            Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("MenuSelection", bottomBar.getItemActiveIndex());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInRequest(resultCode);
        }
    }
}
