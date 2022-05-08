package com.example.crownlk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.crownlk.databinding.ActivityDisputeBinding;
import com.example.crownlk.databinding.ActivityListingsBinding;

public class ListingsActivity extends DrawerBase {

    ActivityListingsBinding activityListingsBinding;

    ImageButton AddListingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListingsBinding = ActivityListingsBinding.inflate(getLayoutInflater());
        setContentView(activityListingsBinding.getRoot());
        allocateActivityTitle("Listings");

        this.AddListingBtn = findViewById(R.id.addListingsBtn);


        this.AddListingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListingsActivity.this,AddListing.class));
                finish();
            }
        });
    }
}