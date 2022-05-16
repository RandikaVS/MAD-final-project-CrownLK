package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    RelativeLayout toSignin;
    EditText email,password;
    Button login;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Shop");

        this.toSignin = findViewById(R.id.userSigninBtn);
        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        login = findViewById(R.id.userLoginBtn);

        mAuth = FirebaseAuth.getInstance();

        toSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UserSignIn.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = email.getText().toString().trim();
                String userpassword = password.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()){
                    email.setError("Please enter valid email");
                    email.requestFocus();
                }

                mAuth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(MainActivity.this,HomePage.class));
                        }else{
                            Toast.makeText(MainActivity.this,"Fail lo login",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });





    }
}