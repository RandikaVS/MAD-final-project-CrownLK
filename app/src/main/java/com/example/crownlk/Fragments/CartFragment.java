package com.example.crownlk.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crownlk.Adapter.CartAdapter;
import com.example.crownlk.Adapter.ProductAdapter;
import com.example.crownlk.Model.Cart;
import com.example.crownlk.Model.Product;
import com.example.crownlk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private TextView item_count, total_price;
    private Button buy;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    RecyclerView recyclerView;
    List<Cart> cartList;
    CartAdapter cartAdapter;

    private String user_id;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        user_id = firebaseUser.getUid();


        item_count = view.findViewById(R.id.item_count);
        total_price = view.findViewById(R.id.total_price);
        buy = view.findViewById(R.id.buy);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        recyclerView = view.findViewById(R.id.cartRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        cartList = new ArrayList<>();
        loadCart();


        return view;
    }

    private void loadCart() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child("Cart");
        Query query = databaseReference.orderByChild("user_id").equalTo(user_id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartList.clear();
                int count = 0;
                double tot_price = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Cart cart = snapshot.getValue(Cart.class);

                    try {
                        String price = (String) snapshot.child("total_price").getValue();
                        tot_price = tot_price + Double.parseDouble(price);

                        count = count + 1;
                    }catch (Exception e){

                    }
                    cartList.add(cart);
                    cartAdapter = new CartAdapter(getActivity(), cartList);
                    recyclerView.setAdapter(cartAdapter);
                }
                item_count.setText("Item : "+ String.valueOf(count));
                total_price.setText("Total Price : "+String.format("%.2f",tot_price));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
