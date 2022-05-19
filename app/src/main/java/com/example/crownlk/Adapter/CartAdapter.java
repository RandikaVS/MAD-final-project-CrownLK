package com.example.crownlk.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crownlk.MainActivity;
import com.example.crownlk.Model.Cart;
import com.example.crownlk.Model.Product;
import com.example.crownlk.ProductViewActivity;
import com.example.crownlk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {

    Context context;
    List<Cart> cartList;
    private DatabaseReference databaseReference;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyHolder holder, int position) {

        String txt_id = cartList.get(position).getId();
        String txt_title = cartList.get(position).getProduct_name();
        String txt_price = cartList.get(position).getItem_price();
        String txt_quantity = cartList.get(position).getQuantity();
        String txt_total_price = cartList.get(position).getTotal_price();
        String txt_image = cartList.get(position).getImage();

        holder.name.setText(txt_title);
        holder.price.setText("Item Price : Rs. "+txt_price);
        holder.quantity.setText("quantity : "+txt_quantity);
        holder.total.setText("Total Price : Rs. "+txt_total_price);

        Picasso.get().load(txt_image).into(holder.image);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogBox(txt_id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity, total;
        Button remove;
        ImageView image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            total = itemView.findViewById(R.id.total);
            remove = itemView.findViewById(R.id.remove);
            image = itemView.findViewById(R.id.image);

        }
    }

    private void ShowDialogBox(final String id) {
        String[] options = {"Yes", "No"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure to remove item");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    deleteCart(id);
                }
                if (which == 1) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }

    private void deleteCart(final String id) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child("Cart").child(id);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    ds.getRef().removeValue();
                    Toast.makeText(context, "Remove Successfully ...", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, MainActivity.class);
//                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
