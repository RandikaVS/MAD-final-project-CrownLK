package com.example.crownlk;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ShopDatabaseOperations {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public  ShopDatabaseOperations(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Shop.class.getSimpleName());
        mAuth=FirebaseAuth.getInstance();
    }

    public Task<Void> createShop(Shop shop){
        System.out.println("Create shop called");
        return databaseReference.child("MyShop").child(mAuth.getCurrentUser().getUid()).setValue(shop);

    }

    public  Task<Void> shopUpdate(String key, HashMap<String,Object> hashMap){
        return databaseReference.child("Shop").updateChildren(hashMap);
    }


}
