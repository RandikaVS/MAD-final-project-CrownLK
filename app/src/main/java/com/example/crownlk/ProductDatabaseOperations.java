package com.example.crownlk;

import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;

public class ProductDatabaseOperations {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;


    public ProductDatabaseOperations(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Shop.class.getSimpleName());


    }

    public Task<Void> addProduct(Product product){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("title",product.title);
        hashMap.put("category",product.category);
        hashMap.put("description",product.description);
        hashMap.put("qty",product.qty);
        hashMap.put("discount",product.discount);
        hashMap.put("price",product.price);
        hashMap.put("discountCalcPrice",product.discountCalcPrice);

        return databaseReference.child("Products").child(product.title).updateChildren(hashMap);
    }


    public Task<Void> addImages(String title,String category,String url,String imageCount){
        return databaseReference.child("Products").child(title).child(imageCount).setValue(url);
    }
}
