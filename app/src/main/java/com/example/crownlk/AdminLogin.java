package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.net.BindException;
import java.util.regex.Pattern;

public class AdminLogin extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private TextView back;
    private ProgressBar loading;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        this.email = findViewById(R.id.adminEmail);
        this.password = findViewById(R.id.adminPassword);
        this.login = findViewById(R.id.adminLogin);
        this.back = findViewById(R.id.back);
        this.loading = findViewById(R.id.loginProgressBar);

        mAuth = FirebaseAuth.getInstance();
        AdminDatabaseOperations adminDatabaseOperations = new AdminDatabaseOperations();

//        Intent intent = getIntent();
//        if(!intent.getStringExtra("email").isEmpty()){
//
//        String intentEmail = intent.getStringExtra("email");
//        email.setText(intentEmail);
//        }




        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                String pw = password.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("Please enter email");
                    email.requestFocus();
                }
                if(password.length() < 6 ){
                    password.setError("Password should contain at least 6 characters");
                    password.requestFocus();
                }

                loading.setVisibility(View.VISIBLE);


        if(Patterns.EMAIL_ADDRESS.matcher(mail).matches() && password.length() >=6){

                adminDatabaseOperations.adminSignIn(mail,pw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    loading.setVisibility(View.GONE);
                                    int duration = Toast.LENGTH_SHORT;
                                    CharSequence msg = "Success";
                                    Context context = getApplicationContext();

                                    Toast toast = Toast.makeText(context, msg, duration);
                                    toast.show();
                                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 10, 0);
                                    startActivity(new Intent(AdminLogin.this,DashBoardActivity.class).putExtra("email",mail));
                                }else{
                                    loading.setVisibility(View.GONE);
                                    int duration = Toast.LENGTH_SHORT;
                                    CharSequence msg = "Fail to Login";
                                    Context context = getApplicationContext();

                                    Toast toast = Toast.makeText(context, msg, duration);
                                    toast.show();
                                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 10, 0);
                                }

                            }
                        });
        }




            }
        });

        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this,MainActivity.class));
            }
        });

    }
}