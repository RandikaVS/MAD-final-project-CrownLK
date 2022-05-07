package com.example.crownlk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.crownlk.databinding.ActivityDisputeBinding;

public class DisputeActivity extends DrawerBase {

    ActivityDisputeBinding activityDisputeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisputeBinding = ActivityDisputeBinding.inflate(getLayoutInflater());
        setContentView(activityDisputeBinding.getRoot());
        allocateActivityTitle("Disputes");
    }
}