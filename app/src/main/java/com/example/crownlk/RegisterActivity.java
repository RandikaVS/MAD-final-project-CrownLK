package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private MaterialEditText name, email, nic, phone, address, password1, password2;
    private Button register;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        nic = findViewById(R.id.nic);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_name = name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_nic = nic.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_address = address.getText().toString();
                String txt_password1 = password1.getText().toString();
                String txt_password2 = password2.getText().toString();

                if(TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_nic) || TextUtils.isEmpty(txt_phone) || TextUtils.isEmpty(txt_address) || TextUtils.isEmpty(txt_password1) || TextUtils.isEmpty(txt_password2) ) {
                    Toast.makeText(RegisterActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                }else if(!txt_password1.equals(txt_password2)){
                    Toast.makeText(RegisterActivity.this, "Password are not matched", Toast.LENGTH_SHORT).show();
                } else if (txt_password1.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must be least 6 characters", Toast.LENGTH_SHORT).show();
                }else {
                    userRegister(txt_name,txt_email,txt_nic,txt_phone,txt_address,txt_password1);
                }

            }
        });

    }

    private void userRegister(String name, String email, String nic, String phone, String address, String password1) {

        firebaseAuth.createUserWithEmailAndPassword(email,password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                            String userID = firebaseUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child("Users").child(userID);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userID);
                            hashMap.put("name",name);
                            hashMap.put("email", email);
                            hashMap.put("nic", nic);
                            hashMap.put("phone", phone);
                            hashMap.put("address", address);

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(RegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}