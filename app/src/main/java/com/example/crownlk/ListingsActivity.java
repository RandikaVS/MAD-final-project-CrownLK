package com.example.crownlk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.crownlk.databinding.ActivityDisputeBinding;
import com.example.crownlk.databinding.ActivityListingsBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ListingsActivity extends DrawerBase {

    ActivityListingsBinding activityListingsBinding;

    ImageButton AddListingBtn;
    Button UpdateBtn,DeleteBtn;

    RecyclerView recyclerView;
    ListingViewAdapter listingViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListingsBinding = ActivityListingsBinding.inflate(getLayoutInflater());
        setContentView(activityListingsBinding.getRoot());
        allocateActivityTitle("Listings");

        recyclerView = (RecyclerView)findViewById(R.id.listing_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.AddListingBtn = findViewById(R.id.addListingsBtn);


        this.AddListingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListingsActivity.this,AddListing.class));
                finish();
            }
        });

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(Shop.class.getSimpleName())
                                .child("Products"), Product.class)
                        .build();

        listingViewAdapter = new ListingViewAdapter(options);
        recyclerView.setAdapter(listingViewAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        listingViewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listingViewAdapter.stopListening();
    }
}