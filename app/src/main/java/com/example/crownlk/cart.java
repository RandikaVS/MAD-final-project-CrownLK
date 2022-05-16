package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class cart extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.favorite:
                startActivity(new Intent(getApplicationContext(),favorite.class));
                overridePendingTransition(0,0);
                return true;

            case R.id.home:
                startActivity(new Intent(getApplicationContext(),HomePage.class));
                overridePendingTransition(0,0);
                return true;

            case R.id.profile:
                startActivity(new Intent(getApplicationContext(),profile.class));
                overridePendingTransition(0,0);
                return true;

            case R.id.update:
                startActivity(new Intent(getApplicationContext(),update.class));
                overridePendingTransition(0,0);
                return true;

            case R.id.cart:
                return true;
            }

             return false;
            }
        });

    }
}