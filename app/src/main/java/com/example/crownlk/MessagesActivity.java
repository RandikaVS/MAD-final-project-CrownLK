package com.example.crownlk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.crownlk.databinding.ActivityMessagesBinding;
import com.example.crownlk.databinding.ActivityNotificationBinding;

public class MessagesActivity extends DrawerBase {

    ActivityMessagesBinding activityMessagesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMessagesBinding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(activityMessagesBinding.getRoot());
        allocateActivityTitle("Messages");
    }
}