package com.example.crownlk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.crownlk.Model.Product;
import com.example.crownlk.ProductViewActivity;
import com.example.crownlk.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    Context context;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String txt_title = productList.get(position).getTitle();
        String txt_price = productList.get(position).getPrice();
        String txt_discount = productList.get(position).getDiscount();
        String txt_description = productList.get(position).getDescription();
        String txt_img0 = productList.get(position).getImg0();

        holder.name.setText(txt_title);
        holder.price.setText("Rs. "+txt_price);

        Picasso.get().load(txt_img0).into(holder.image);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductViewActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("item_id", txt_title);
                intent.putExtra("image", txt_img0);
                intent.putExtra("price", txt_price);
                intent.putExtra("discount", txt_discount);
                intent.putExtra("description", txt_description);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name, price, description;
        ImageView image;
        LinearLayout item;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            item = itemView.findViewById(R.id.item);

        }
    }
}
