package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.crownlk.databinding.ActivityAdminBinding;
import com.example.crownlk.databinding.ActivityShopBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminActivity extends DrawerBase {

    ActivityAdminBinding activityAdminBinding;

    private ImageView adminPic;
    private EditText adminEmail,adminName,adminAddress,adminNic,adminPassword,adminRe_Password;
    private Button reset,adminUpdate,AdminDelete;
    private ProgressBar loading;
    private DatabaseReference adminRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(activityAdminBinding.getRoot());
        allocateActivityTitle("Admin");

        this.adminPic = findViewById(R.id.adminPic);
        this.adminEmail = findViewById(R.id.adminAccEmail);
        this.adminName = findViewById(R.id.adminName);
        this.adminAddress = findViewById(R.id.adminAddress);
        this.adminNic = findViewById(R.id.adminNic);
        this.adminPassword = findViewById(R.id.adminAccPassword);
        this.adminRe_Password = findViewById(R.id.adminRe_Password);
        this.reset = findViewById(R.id.reset);
        this.adminUpdate = findViewById(R.id.adminUpdate);
        this.AdminDelete = findViewById(R.id.adminDelete);
        this.loading = findViewById(R.id.progressBar);

        AdminDatabaseOperations adminDatabaseOperations = new AdminDatabaseOperations();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;


        adminRef = FirebaseDatabase.getInstance().getReference("Shop").child("Admin").child(currentFirebaseUser.getUid());

        adminRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(AdminActivity.this,"Admin details",Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String email = String.valueOf(dataSnapshot.child("userEmail").getValue());
                        String name = String.valueOf(dataSnapshot.child("userName").getValue());
                        String address = String.valueOf(dataSnapshot.child("userAddress").getValue());
                        String nic = String.valueOf(dataSnapshot.child("userNic").getValue());
//                        String password = String.valueOf(dataSnapshot.child("userPassword").getValue());

                        adminEmail.setText(email);
                        adminName.setText(name);
                        adminAddress.setText(address);
                        adminNic.setText(nic);
//                        adminPassword.setText(password);
                    }else{
                        Toast.makeText(AdminActivity.this,"Error fetch admin data",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AdminActivity.this,"Error fetch admin data not success",Toast.LENGTH_SHORT).show();
                }
            }
        });


        this.adminUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);

                String txtAdminAddress = adminAddress.getText().toString().trim();
                String txtAdminName = adminName.getText().toString().trim();
                String txtAdminNic = adminNic.getText().toString().trim();
                String txtAdminPassword = adminPassword.getText().toString().trim();
                String txtAdminRe_Password = adminRe_Password.getText().toString().trim();
                boolean success = true;

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("userAddress",txtAdminAddress);
                hashMap.put("userName",txtAdminName);
                hashMap.put("userNic",txtAdminNic);

                if(txtAdminAddress.isEmpty()){
                    adminAddress.setError("Please enter address ! ");
                    adminAddress.requestFocus();
                    success=false;
                }
                if(txtAdminName.isEmpty()){
                    adminName.setError("Please enter name ! ");
                    adminName.requestFocus();
                    success=false;
                }
                if(txtAdminNic.isEmpty()){
                    adminNic.setError("Please enter NIC ! ");
                    adminNic.requestFocus();
                    success=false;
                }
                if(!txtAdminPassword.isEmpty()) {
                    if (txtAdminPassword.length() < 6) {
                        adminPassword.setError("Please enter password containing at least 6 characters ! ");
                        adminPassword.requestFocus();
                        success = false;
                    }
                }
                if(!txtAdminRe_Password.isEmpty()) {
                    if (!txtAdminRe_Password.equals(txtAdminPassword)) {
                        adminRe_Password.setError("Passwords not match !");
                        adminRe_Password.requestFocus();
                        success = false;
                    }
                }
                //adminDatabaseOperations.adminDelete("kdjfj");

                if(success) {
                    adminDatabaseOperations.adminUpdate(currentFirebaseUser.getUid(), hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AdminActivity.this, "Admin Updated Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AdminActivity.this, "Admin Updating Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                    loading.setVisibility(View.GONE);
                                }
                            });
                }else{
                    Toast.makeText(AdminActivity.this, "Admin Updating Failed!(Invalid inputs)", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

}