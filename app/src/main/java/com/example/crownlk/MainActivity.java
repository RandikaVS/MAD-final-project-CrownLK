package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.crownlk.Fragments.CartFragment;
import com.example.crownlk.Fragments.FavouriteFragment;
import com.example.crownlk.Fragments.HomeFragment;
import com.example.crownlk.Fragments.ProfileFragment;
import com.example.crownlk.Fragments.UpdateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottom_navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation_view = findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.setSelectedItemId(R.id.navigation_home);
    }

    CartFragment cartFragment = new CartFragment();
    FavouriteFragment favouriteFragment = new FavouriteFragment();
    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    UpdateFragment updateFragment = new UpdateFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.navigation_favourite:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, favouriteFragment).commit();
                return true;

            case R.id.navigation_update:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, updateFragment).commit();
                return true;

            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                return true;

            case R.id.navigation_cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, cartFragment).commit();
                return true;
        }

        return false;
    }
}