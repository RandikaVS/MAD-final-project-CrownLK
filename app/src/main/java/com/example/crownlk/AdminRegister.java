package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegister extends AppCompatActivity {


    private EditText email;
    private EditText name;
    private EditText address;
    private EditText nic;
    private EditText password;
    private EditText Re_Password;
    private Button signin;
    private RelativeLayout logBack;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        this.email = findViewById(R.id.regEmail);
        this.name = findViewById(R.id.regName);
        this.address = findViewById(R.id.regAddress);
        this.nic = findViewById(R.id.regNic);
        this.password = findViewById(R.id.regPassword);
        this.Re_Password = findViewById(R.id.regRe_Password);
        this.signin = findViewById(R.id.regButton);
        this.logBack = findViewById(R.id.loginBack);
        this.progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        AdminDatabaseOperations adminDatabaseOperations = new AdminDatabaseOperations();

        this.logBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int duration = Toast.LENGTH_SHORT;
                CharSequence msg = "Back to login";
                Context context = getApplicationContext();

                Toast toast = Toast.makeText(context, msg, duration);
                toast.show();
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 10, 0);

                startActivity(new Intent(AdminRegister.this, AdminLogin.class));
                finish();
            }
        });

        this.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = email.getText().toString().trim();
                String txtName = name.getText().toString().trim();
                String txtAddress = address.getText().toString().trim();
                String txtNic = nic.getText().toString().trim();
                String txtPassword = password.getText().toString().trim();
                String txtRe_Passowrd = Re_Password.getText().toString().trim();
                boolean success = true;

                if(txtEmail.isEmpty()){
                    email.setError("Please enter email ! ");
                    email.requestFocus();
                    success=false;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
                    email.setError("Please enter valid email");
                    email.requestFocus();
                    success=false;
                }
                if(txtName.isEmpty()){
                    name.setError("Please enter name ! ");
                    name.requestFocus();
                    success=false;
                }
                if(txtAddress.isEmpty()){
                    address.setError("Please enter address ! ");
                    address.requestFocus();
                    success=false;
                }
                if(txtNic.isEmpty()){
                    nic.setError("Please enter NIC ! ");
                    nic.requestFocus();
                    success=false;
                }
                if(txtPassword.isEmpty() || txtPassword.length()<6){
                    password.setError("Please enter password containing at least 6 characters ! ");
                    password.requestFocus();
                    success=false;
                }
                if(txtRe_Passowrd.isEmpty() || !txtRe_Passowrd.equals(txtPassword)){
                    Re_Password.setError("Passwords not match !");
                    Re_Password.requestFocus();
                    success=false;
                }
                progressBar.setVisibility(View.VISIBLE);

                if(success) {
                    Admins admin = new Admins(txtEmail, txtName, txtAddress, txtNic, txtRe_Passowrd);
                    adminDatabaseOperations.adminAuth(admin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                adminDatabaseOperations.addAdmin(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(AdminRegister.this, "Admin Registered Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(AdminRegister.this, AdminLogin.class).putExtra("email",txtEmail));
                                        }else{
                                            Toast.makeText(AdminRegister.this, "Admin Failed to Register", Toast.LENGTH_SHORT).show();
                                        }
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                            }else{
                                Toast.makeText(AdminRegister.this, "Admin Failed to Register email", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
//                    mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword)
//                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                                    if (task.isSuccessful()) {
//
//                                        Admins user = new Admins(txtEmail, txtName, txtAddress, txtNic, txtRe_Passowrd);
//
//                                        FirebaseDatabase.getInstance().getReference("Admins")
//                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//
//                                                if (task.isSuccessful()) {
//
//                                                    Toast.makeText(AdminRegister.this, "Admin Registered Successfully", Toast.LENGTH_SHORT).show();
//                                                    startActivity(new Intent(AdminRegister.this, AdminLogin.class).putExtra("email",txtEmail));
//                                                } else {
//
//                                                    Toast.makeText(AdminRegister.this, "Admin Failed to Register", Toast.LENGTH_SHORT).show();
//                                                }
//                                                progressBar.setVisibility(View.GONE);
//                                            }
//                                        });
//                                    } else {
//                                        Toast.makeText(AdminRegister.this, "Admin Failed to Register email", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
                }else{

                        progressBar.setVisibility(View.GONE);

                }
            }
        });


    }//oncreate
}