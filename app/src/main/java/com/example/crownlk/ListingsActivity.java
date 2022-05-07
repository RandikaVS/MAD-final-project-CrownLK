package com.example.crownlk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.crownlk.databinding.ActivityDisputeBinding;
import com.example.crownlk.databinding.ActivityListingsBinding;

public class ListingsActivity extends DrawerBase {

    ActivityListingsBinding activityListingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListingsBinding = ActivityListingsBinding.inflate(getLayoutInflater());
        setContentView(activityListingsBinding.getRoot());
        allocateActivityTitle("Listings");
    }
}