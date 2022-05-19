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
import com.example.crownlk.Model.Fav;
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

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyHolder> {

    Context context;
    List<Fav> favList;
    private DatabaseReference databaseReference;

    public FavAdapter(Context context, List<Fav> favList) {
        this.context = context;
        this.favList = favList;
    }

    @NonNull
    @Override
    public FavAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fav, parent, false);
        return new FavAdapter.MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FavAdapter.MyHolder holder, int position) {

        String txt_id = favList.get(position).getId();
        String txt_title = favList.get(position).getProduct_name();
        String txt_price = favList.get(position).getItem_price();
        String txt_quantity = favList.get(position).getQuantity();
        String txt_total_price = favList.get(position).getTotal_price();
        String txt_image = favList.get(position).getImage();

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
        return favList.size();
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
                    deleteFav(id);
                }
                if (which == 1) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }

    private void deleteFav(final String id) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Shop").child("Wishlist").child(id);
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
