package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crownlk.databinding.ActivityShopBinding;
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

public class ShopActivity extends DrawerBase {

    ActivityShopBinding activityShopBinding;

    private EditText ShopName,ShopAddress,ShopDescription,ShopSlogan,ShopLinks;
    private TextView ShopNameHeader;
    private Button UpdateShop;
    private ProgressBar ProgressBarShop;
    private DatabaseReference shopRef;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShopBinding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(activityShopBinding.getRoot());
        allocateActivityTitle("Shop Info");

        this.ShopName = findViewById(R.id.shopName);
        this.ShopAddress = findViewById(R.id.shopAddress);
        this.ShopDescription = findViewById(R.id.shopDescription);
        this.ShopSlogan = findViewById(R.id.shopSlogan);
        this.ShopLinks = findViewById(R.id.shopLinks);
        this.ProgressBarShop = findViewById(R.id.progressBarShop);
        this.UpdateShop = findViewById(R.id.updateShop);
        this.ShopNameHeader = findViewById(R.id.shopNameHeader);

        ShopDatabaseOperations shopDatabaseOperations = new ShopDatabaseOperations();

        shopRef = FirebaseDatabase.getInstance().getReference("Shop").child("Shop");

        shopRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String address = String.valueOf(dataSnapshot.child("address").getValue());
                        String description = String.valueOf(dataSnapshot.child("description").getValue());
                        String slogan = String.valueOf(dataSnapshot.child("slogan").getValue());
                        String links = String.valueOf(dataSnapshot.child("links").getValue());

                        ShopName.setText(name);
                        ShopAddress.setText(address);
                        ShopDescription.setText(description);
                        ShopSlogan.setText(slogan);
                        ShopLinks.setText(links);
                        ShopNameHeader.setText(name);

                    }else{
                        Toast.makeText(ShopActivity.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ShopActivity.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });



        this.UpdateShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBarShop.setVisibility(View.VISIBLE);

                String shopName = ShopName.getText().toString().trim();
                String shopAddress = ShopAddress.getText().toString().trim();
                String shopDescription = ShopDescription.getText().toString().trim();
                String shopSlogan = ShopSlogan.getText().toString().trim();
                String shopLinks = ShopLinks.getText().toString().trim();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",shopName);
                hashMap.put("address",shopAddress);
                hashMap.put("description",shopDescription);
                hashMap.put("slogan",shopSlogan);
                hashMap.put("links",shopLinks);

                boolean success = true;

                if(shopName.isEmpty()){
                    ShopName.setError("Please enter shop name ! ");
                    ShopName.requestFocus();
                    success=false;
                }
                if(shopAddress.isEmpty()){
                    ShopAddress.setError("Please enter shop address");
                    ShopAddress.requestFocus();
                    success=false;
                }
                if(shopDescription.isEmpty()){
                    ShopDescription.setError("Please enter shop description ! ");
                    ShopDescription.requestFocus();
                    success=false;
                }
                if(shopSlogan.isEmpty()){
                    ShopSlogan.setError("Please enter shop slogan ! ");
                    ShopSlogan.requestFocus();
                    success=false;
                }
                if(shopLinks.isEmpty()){
                    ShopLinks.setError("Please enter shop links ! ");
                    ShopLinks.requestFocus();
                    success=false;
                }

                if(success){

                    shopDatabaseOperations.shopUpdate(shopName,hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ShopActivity.this, "Shop Updated Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ShopActivity.this, "Shop Updating Failed!", Toast.LENGTH_SHORT).show();
                            }
                            ProgressBarShop.setVisibility(View.GONE);
                        }
                    });

                }else{
                    Toast.makeText(ShopActivity.this, "Shop Updating Failed!(Invalid inputs)", Toast.LENGTH_SHORT).show();
                    ProgressBarShop.setVisibility(View.GONE);
                }
            }
        });



    }//on create
}