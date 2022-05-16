package com.example.crownlk;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Objects;

public class ListingViewAdapter extends FirebaseRecyclerAdapter<Product,ListingViewAdapter.listingViewHolder> {

    public static final int PICK_IMAGE = 1;

    ProductDatabaseOperations productDatabaseOperations;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListingViewAdapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull listingViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Product model) {
        holder.ListingText.setText(model.title);
        holder.StockCount.setText(model.qty);
        Glide.with(holder.ListingImage.getContext())
                .load(model.getImg0())
                .placeholder(R.drawable.crown_lk)
                .circleCrop()
                .error(R.drawable.image_icon)
                .into(holder.ListingImage);


        holder.ListingUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.ListingText.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_listing))
                        .setExpanded(true,1800)
                        .create();

                View view1 = dialogPlus.getHolderView();

                EditText updateTitle = view1.findViewById(R.id.item_update_title);
                EditText updateDescription = view1.findViewById(R.id.item_update_description);
                EditText updateQty = view1.findViewById(R.id.item_update_quantity);
                EditText updateDiscount = view1.findViewById(R.id.item_update_discount);
                EditText updatePrice = view1.findViewById(R.id.item_update_price);
                Button updateListing = view1.findViewById(R.id.item_update);
                ImageView img0 =view1.findViewById(R.id.choose_image0_update);
                ImageView img1 =view1.findViewById(R.id.choose_image1_update);
                ImageView img2 =view1.findViewById(R.id.choose_image2_update);
                ImageView img3 =view1.findViewById(R.id.choose_image3_update);
                ImageView img4 =view1.findViewById(R.id.choose_image4_update);
                ImageButton cancelImg0 = view1.findViewById(R.id.remove_image0_update);
                ImageButton cancelImg1 = view1.findViewById(R.id.remove_image1_update);
                ImageButton cancelImg2 = view1.findViewById(R.id.remove_image2_update);
                ImageButton cancelImg3 = view1.findViewById(R.id.remove_image3_update);
                ImageButton cancelImg4 = view1.findViewById(R.id.remove_image4_update);


                updateTitle.setText(model.getTitle());
                updateDescription.setText(model.getDescription());
                updateQty.setText(model.getQty());
                updateDiscount.setText(model.getDiscount());
                updatePrice.setText(model.getPrice());
                Glide.with(img0).load(model.getImg0()).placeholder(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img0);
                Glide.with(img1).load(model.getImg1()).placeholder(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img1);
                Glide.with(img2).load(model.getImg2()).placeholder(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img2);
                Glide.with(img3).load(model.getImg3()).placeholder(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img3);
                Glide.with(img4).load(model.getImg4()).placeholder(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img4);

                cancelImg0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(img0).load(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img0);

                    }
                });
                cancelImg1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(img1).load(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img1);

                    }
                });
                cancelImg2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(img2).load(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img2);

                    }
                });
                cancelImg3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(img3).load(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img3);

                    }
                });
                cancelImg4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(img4).load(R.drawable.add_image).error(R.drawable.image_icon).circleCrop().into(img4);

                    }
                });


                dialogPlus.show();

                updateListing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String txtDescription = updateDescription.getText().toString().trim();
                        String txtQty = updateQty.getText().toString().trim();
                        String txtDiscount = updateDiscount.getText().toString().trim();
                        String txtPrice = updatePrice.getText().toString().trim();
                        String txtTitle = updateTitle.getText().toString().trim();

                        int ItemPriceConv = Integer.parseInt(txtPrice);
                        int ItemDiscount = Integer.parseInt(txtDiscount);
                        int discountPrice = ItemPriceConv*ItemDiscount/100;
                        int newPrice = ItemPriceConv-discountPrice;
                        String discountCalcPrice = Integer.toString(newPrice);

                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("description",txtDescription);
                        hashMap.put("discount",txtDiscount);
                        hashMap.put("price",txtPrice);
                        hashMap.put("qty",txtQty);
                        hashMap.put("title",txtTitle);
                        hashMap.put("discountCalcPrice",discountCalcPrice);


                        FirebaseDatabase.getInstance().getReference(Shop.class.getSimpleName()).child("Products")
                                .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.ListingText.getContext(),"Listing updated successfully",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.ListingText.getContext(),"Error while updating listing",Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        });


                    }
                });

            }
        });

        holder.ListingDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.ListingText.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted listing cannot restore again ! ");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference(Shop.class.getSimpleName()).child("Products")
                                .child(getRef(position).getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.ListingText.getContext(),"Listing deleted successfully",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.ListingText.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }



    @NonNull
    @Override
    public listingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_item_card_view,parent,false);
        return new listingViewHolder(view);
    }

    class listingViewHolder extends RecyclerView.ViewHolder{

        ImageView ListingImage;
        TextView ListingText,StockCount,ListingUpdate,ListingDelete;

        public listingViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ListingImage = (ImageView)itemView.findViewById(R.id.listing_image);
            this.ListingText = (TextView)itemView.findViewById(R.id.listing_text);
            this.StockCount = (TextView)itemView.findViewById(R.id.stock_count);
            this.ListingUpdate =(TextView)itemView.findViewById(R.id.listing_update);
            this.ListingDelete = (TextView)itemView.findViewById(R.id.listing_delete);
        }
    }
}
