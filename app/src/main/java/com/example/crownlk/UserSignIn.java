package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignIn extends AppCompatActivity {

    private EditText email,name,address,nic,password,rePassword;
    private Button signin;
    RelativeLayout logBack;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);

        email = findViewById(R.id.userEmail);
        name = findViewById(R.id.userName);
        address = findViewById(R.id.userAddress);
        nic = findViewById(R.id.userNic);
        password = findViewById(R.id.userPassword);
        rePassword = findViewById(R.id.userRe_Password);
        signin = findViewById(R.id.userRegButton);
        logBack = findViewById(R.id.userLoginBack);
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Shop");

        this.logBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserSignIn.this, MainActivity.class));
                finish();
            }
        });

        this.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString().trim();
                String userName = name.getText().toString().trim();
                String userAddress = address.getText().toString().trim();
                String userNic = nic.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userRe_Passowrd = rePassword.getText().toString().trim();

                User user = new User(userEmail,userName,userAddress,userNic,userRe_Passowrd);

                mAuth.createUserWithEmailAndPassword(user.userEmail,user.userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            myRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(UserSignIn.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(UserSignIn.this,MainActivity.class));
                                            }else{
                                                Toast.makeText(UserSignIn.this, "User Failed to Register", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(UserSignIn.this, "User Failed to Register email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}