package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class ProductViewActivity extends AppCompatActivity {

    private String get_item_id, get_image, get_price, get_discount, get_description, user_id, address;

    private ImageView image;
    private ImageButton plus, minus;
    private TextView name, description, price, discount, quantity;
    private Button buy, cart;

    private static int quantity_count = 1;

    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Product View");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        user_id = firebaseUser.getUid();

        Intent intent = getIntent();
        get_item_id = intent.getStringExtra("item_id");
        get_image = intent.getStringExtra("image");
        get_price = intent.getStringExtra("price");
        get_discount = intent.getStringExtra("discount");
        get_description = intent.getStringExtra("description");

        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        discount = findViewById(R.id.discount);
        quantity = findViewById(R.id.quantity);

        buy = findViewById(R.id.buy);
        cart = findViewById(R.id.cart);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);

        Picasso.get().load(get_image).into(image);
        name.setText(get_item_id);
        description.setText(get_description);
        price.setText(   "Price : Rs."+get_price);
        discount.setText("Discount : Rs."+get_discount);

        databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child("Users").child(user_id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                address = (String) dataSnapshot.child("address").getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderItem();

                quantity_count = 1;
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart();
                quantity_count = 1;
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity_count = quantity_count + 1;
                quantity.setText(String.valueOf(quantity_count));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity_count >= 2) {
                    quantity_count = quantity_count - 1;
                    quantity.setText(String.valueOf(quantity_count));
                }else{
                    Toast.makeText(ProductViewActivity.this, "Can't Minus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        quantity.setText(String.valueOf(quantity_count));


    }


    private void addCart() {
        progressDialog.setMessage("Adding item to cart....");
        progressDialog.show();
        final String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", timestamp);
        hashMap.put("product_name", get_item_id);
        hashMap.put("image", get_image);
        hashMap.put("user_id", user_id);
        hashMap.put("quantity", String.valueOf(quantity_count));
        hashMap.put("item_price", get_price);
        hashMap.put("total_price", String.format("%.2f",totalPrice()));

        databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child("Cart");
        databaseReference.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(ProductViewActivity.this, "Add to cart Successfully ...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ProductViewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }





    private void orderItem() {
        progressDialog.setMessage("Ordering item....");
        progressDialog.show();
        final String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", timestamp);
        hashMap.put("product_name", get_item_id);
        hashMap.put("order_date", timestamp);
        hashMap.put("user_id", user_id);
        hashMap.put("quantity", String.valueOf(quantity_count));
        hashMap.put("address", address);
        hashMap.put("item_price", get_price);
        hashMap.put("total_price", String.format("%.2f",totalPrice()));

        databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child("Orders");
        databaseReference.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(ProductViewActivity.this, "Order Successful ...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ProductViewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private Double totalPrice(){
        return quantity_count*Double.parseDouble(get_price);
    }
}