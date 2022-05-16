package com.example.crownlk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<cartModel> cartList;

    public CartAdapter(Context context, List<cartModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String txt_id = cartList.get(position).getId();
        String txt_price = cartList.get(position).getPrice();
        String txt_name = cartList.get(position).getName();
        String txt_quantity = cartList.get(position).getQuantity();
        String txt_image = cartList.get(position).getImage();

        holder.name.setText(txt_name);
        holder.name.setText(txt_price);
        holder.name.setText(txt_name);
        Picasso.get().load(txt_image).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout cart_item;
        TextView name,price;
        EditText quantity;
        Button buy,remove;
        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cart_item = itemView.findViewById(R.id.cart_item);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            buy = itemView.findViewById(R.id.buy);
            remove = itemView.findViewById(R.id.remove);
            image = itemView.findViewById(R.id.image);
        }
    }
}
