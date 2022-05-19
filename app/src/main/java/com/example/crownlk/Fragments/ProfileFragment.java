package com.example.crownlk.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.crownlk.R;
import com.example.crownlk.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    EditText mail,name,address,nic,phone;
    Button update,delete;

    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private ProgressDialog progressDialog;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        this.mail = view.findViewById(R.id.userEmail);
        this.name =view.findViewById(R.id.userName);
        this.address =view.findViewById(R.id.userAddress);
        this.nic =view.findViewById(R.id.userNic);
        this.phone =view.findViewById(R.id.userPhone);
        this.update =view.findViewById(R.id.profile_update);
        this.delete =view.findViewById(R.id.profile_delete);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        userRef = FirebaseDatabase.getInstance().getReference("Shop").child("Users").child(userId);


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Email = (String) snapshot.child("email").getValue();
                String Name = (String) snapshot.child("name").getValue();
                String Address = (String) snapshot.child("address").getValue();
                String Nic = (String) snapshot.child("nic").getValue();
                String Phone = (String) snapshot.child("phone").getValue();
                String Id = (String) snapshot.child("id").getValue();

                mail.setText(Email);
                name.setText(Name);
                address.setText(Address);
                nic.setText(Nic);
                phone.setText(Phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Error fetch user data",Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                String txtEmail = mail.getText().toString().trim();
                String txtAddress = address.getText().toString().trim();
                String txtNic = nic.getText().toString().trim();
                String txtPhone = phone.getText().toString().trim();
                String txtName = name.getText().toString().trim();
                boolean success = true;

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",txtName);
                hashMap.put("address",txtAddress);
                hashMap.put("nic",txtNic);
                hashMap.put("phone",txtPhone);


                if(txtName.isEmpty()){
                    name.setError("Please enter name ! ");
                    name.requestFocus();
                    success=false;
                }
                if(txtAddress.isEmpty()){
                    address.setError("Please enter Address ! ");
                    address.requestFocus();
                    success=false;
                }
                if(txtNic.isEmpty()){
                    nic.setError("Please enter NIC ! ");
                    nic.requestFocus();
                    success=false;
                }
                if(txtPhone.isEmpty()){
                    phone.setError("Please enter NIC ! ");
                    phone.requestFocus();
                    success=false;
                }

                if(success){

                    userRef.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"User Updated",Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"User Updating failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"User Updating failed",Toast.LENGTH_SHORT).show();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                userRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"User Deleted",Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(getActivity(), StartActivity.class);
                            startActivity(intent);

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"User Deleting failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return view;
    }
}
