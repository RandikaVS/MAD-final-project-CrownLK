package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crownlk.databinding.ActivityAddListingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddListing extends AppCompatActivity {

    ActivityAddListingBinding binding;

    String[] category = {"Home & living","Art & collectibles","Craft supplies","Gift & cards"};
    private static final int PICK_IMAGE = 1;
    private Uri ImageUri;
    private int uploadCount=0;
    String discountCalcPrice = "0";
    boolean isImagesUploaded = false;
    ArrayList<Uri> imageList = new ArrayList<Uri>(4);

    AutoCompleteTextView autoCompleteText;
    EditText itemTitle,itemDescription,itemQuantity,itemDiscount,itemPrice;
    ImageButton BackBtn,RemoveImages;
    ImageView chooseImage;
    TextView ImageCountShow;
    Button itemPublish;
    ProgressBar progressBar;
    private ProgressDialog progressDialog;

    ArrayAdapter<String> categoryAdapter;
    Product product = new Product();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        this.autoCompleteText = findViewById(R.id.add_listing_category);
        this.BackBtn = findViewById(R.id.add_listing_back_btn);
        this.itemTitle = findViewById(R.id.itemTitle);
        this.itemDescription = findViewById(R.id.itemDescription);
        this.itemQuantity = findViewById(R.id.itemQuantity);
        this.itemDiscount = findViewById(R.id.itemDiscount);
        this.itemPrice = findViewById(R.id.itemPrice);
        this.progressBar = findViewById(R.id.progressBarAddProduct);
        this.itemPublish = findViewById(R.id.itemPublish);
        this.chooseImage = findViewById(R.id.choose_image);
        this.ImageCountShow = findViewById(R.id.image_count_show);
        this.RemoveImages = findViewById(R.id.remove_images);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        ProductDatabaseOperations productDatabaseOperations = new ProductDatabaseOperations();

        RemoveImages.setVisibility(View.GONE);

        this.categoryAdapter = new ArrayAdapter<String>(this,R.layout.category_items,category);
        autoCompleteText.setAdapter(categoryAdapter);
        this.autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String category = parent.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),"Category: "+category,Toast.LENGTH_SHORT).show();
                System.out.println(autoCompleteText.getText());
            }
        });


        this.BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddListing.this,ListingsActivity.class));
                finish();
            }
        });

        this.RemoveImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageList.clear();
                RemoveImages.setVisibility(View.GONE);
                chooseImage.setImageResource(R.drawable.add_image);
                ImageCountShow.setVisibility(View.GONE);
            }
        });


        this.chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);

                startActivityForResult(galleryIntent,PICK_IMAGE);
            }
        });


        this.itemPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = itemTitle.getText().toString().trim();
                String category = autoCompleteText.getText().toString().trim();
                String description = itemDescription.getText().toString().trim();
                String qty = itemQuantity.getText().toString().trim();
                String discount = itemDiscount.getText().toString().trim();
                String price = itemPrice.getText().toString().trim();

                boolean success = true;

                if (title.isEmpty()) {
                    itemTitle.setError("Please enter title ! ");
                    itemTitle.requestFocus();
                    success = false;
                }
                if (category.isEmpty()) {
                    autoCompleteText.setError("Please select category ! ");
                    autoCompleteText.requestFocus();
                    success = false;
                }
                if (description.isEmpty()) {
                    itemDescription.setError("Please enter description ! ");
                    itemDescription.requestFocus();
                    success = false;
                }
                if (qty.isEmpty()) {
                    itemQuantity.setError("Please enter quantity ! ");
                    itemQuantity.requestFocus();
                    success = false;
                }

                if (price.isEmpty()) {
                    itemPrice.setError("Please enter price ! ");
                    itemPrice.requestFocus();
                    success = false;
                }
                if (!discount.isEmpty()) {
                    if (discount.length() >= 3) {
                        itemDiscount.setError("Discount must lower than 100 ! ");
                        itemDiscount.requestFocus();
                        success = false;
                    } else {
                        int ItemPriceConv = Integer.parseInt(price);
                        int ItemDiscount = Integer.parseInt(discount);
                        int discountPrice = ItemPriceConv*ItemDiscount/100;
                        int newPrice = ItemPriceConv-discountPrice;
                        discountCalcPrice = Integer.toString(newPrice);
                    }
                }
//                progressBar.setVisibility(View.VISIBLE);

                if (success && imageList.size()>0) {
                    progressDialog.show();
                    StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

                    for(uploadCount = 0; uploadCount < imageList.size();uploadCount++){
                        String imageCount = Integer.toString(uploadCount);
                        String imageCount2 = "img"+imageCount;
                        Uri individualImage = imageList.get(uploadCount);
                        StorageReference ImageName = ImageFolder.child(title+individualImage.getLastPathSegment());

                        ImageName.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String url = String.valueOf(uri);
                                        productDatabaseOperations.addImages(title,category,url,imageCount2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(AddListing.this,"Images Uploaded",Toast.LENGTH_SHORT).show();

                                                }else{
                                                    Toast.makeText(AddListing.this,"Images did not uploaded realtime DB",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                    if(uploadCount>=4) {
                        product.addProduct(title, category, description, qty, discount, price, discountCalcPrice);

                        productDatabaseOperations.addProduct(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddListing.this, "Listing Published Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddListing.this, ListingsActivity.class));
                                } else {
                                    Toast.makeText(AddListing.this, "Listings failed to publish", Toast.LENGTH_SHORT).show();
                                }
//                                          progressBar.setVisibility(View.GONE);
                            }
                        });
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AddListing.this, "Images does not upload to realtime db", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AddListing.this, "Listings failed you have not filled required data", Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getClipData() !=null){
            int countClipData = data.getClipData().getItemCount();

            int currentImageSelect = 0;

            while(currentImageSelect<countClipData){
                ImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();

                imageList.add(ImageUri);
//                product.setImages(List ImageUri);

                currentImageSelect = currentImageSelect+1;

            }
            chooseImage.setImageURI(imageList.get(0));
            RemoveImages.setVisibility(View.VISIBLE);
            ImageCountShow.setVisibility(View.VISIBLE);
            ImageCountShow.setText("You have selected "+ imageList.size()+" images");
        }else{
            Toast.makeText(this,"Please select multiple images",Toast.LENGTH_SHORT).show();
        }
    }

}