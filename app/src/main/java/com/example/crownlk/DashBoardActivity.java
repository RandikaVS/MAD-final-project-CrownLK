package com.example.crownlk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.crownlk.databinding.ActivityDashBoardBinding;
import com.example.crownlk.databinding.ActivityDisputeBinding;

public class DashBoardActivity extends DrawerBase {

    ActivityDashBoardBinding activityDisputeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDisputeBinding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(activityDisputeBinding.getRoot());
        allocateActivityTitle("Dashboard");
    }
}