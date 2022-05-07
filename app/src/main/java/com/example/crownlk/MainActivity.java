package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        myRef.setValue("Hello, User");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("crownlk", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("crownlk", "Failed to read value.", error.toException());
            }
        });

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

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
                          startActivity(new Intent(getApplicationContext(),cart.class));
                          overridePendingTransition(0,0);
                          return true;
                  }

                return false;
            }
        });
    }
}