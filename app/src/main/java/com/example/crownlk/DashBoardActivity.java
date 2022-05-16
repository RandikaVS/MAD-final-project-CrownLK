package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.crownlk.databinding.ActivityDashBoardBinding;
import com.example.crownlk.databinding.ActivityDisputeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoardActivity extends DrawerBase {

    ActivityDashBoardBinding activityDisputeBinding;
    private DatabaseReference shopRef;

    TextView listingsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisputeBinding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(activityDisputeBinding.getRoot());
        allocateActivityTitle("Dashboard");

        shopRef=FirebaseDatabase.getInstance().getReference(Shop.class.getSimpleName());

        this.listingsCount = findViewById(R.id.listings_count);

        shopRef.child("Products")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int listingCount = (int) snapshot.getChildrenCount();
                        String listing = String.valueOf(listingCount);
                        listingsCount.setText(listing);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}