package com.example.crownlk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.crownlk.databinding.ActivityNotificationBinding;

public class MessagesActivity extends DrawerBase {

    ActivityNotificationBinding activityNotificationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotificationBinding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(activityNotificationBinding.getRoot());
        allocateActivityTitle("Messages");
    }
}