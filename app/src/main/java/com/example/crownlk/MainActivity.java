package com.example.crownlk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    private Button login;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("CrownLK Selling app");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("CrownLK", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("CrownLK", "Failed to read value.", error.toException());
            }
        });

        this.login = findViewById(R.id.login);
        this.register = findViewById(R.id.signup);

       this.login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               int duration = Toast.LENGTH_SHORT;
               CharSequence msg = "Login page";
               Context context = getApplicationContext();

               Toast toast = Toast.makeText(context, msg, duration);
               toast.show();
               toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 10, 0);

               startActivity(new Intent(MainActivity.this,AdminLogin.class));
               finish();
           }
       });

       this.register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int duration = Toast.LENGTH_SHORT;
               CharSequence msg = "Register page";
               Context context = getApplicationContext();

               Toast toast = Toast.makeText(context, msg, duration);
               toast.show();
               toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 10, 0);

               startActivity(new Intent(MainActivity.this, AdminRegister.class));
               finish();
           }
       });
    }
}