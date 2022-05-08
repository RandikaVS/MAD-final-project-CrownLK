package com.example.crownlk;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminDatabaseOperations {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public  AdminDatabaseOperations(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Shop.class.getSimpleName());
        mAuth=FirebaseAuth.getInstance();
    }

    public Task<Void> addAdmin(Admins adm){

        return databaseReference.child("Admin").child(mAuth.getCurrentUser().getUid()).setValue(adm);

    }

    public Task<AuthResult> adminAuth(Admins adm){

         return mAuth.createUserWithEmailAndPassword(adm.userEmail, adm.userPassword);

    }

    public Task<AuthResult> adminSignIn(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public  Task<Void> adminUpdate(String key, HashMap<String,Object>hashMap){
        return databaseReference.child("Admin").child(key).updateChildren(hashMap);
    }
}
