package com.example.crownlk;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Product {

    public String title;
    public String category;
    public String description;
    public String qty;
    public String discount="0";
    public String price;
    public String discountCalcPrice="0";
    public String img0,img1,img2,img3,img4;
    ArrayList<Uri> images = new ArrayList<Uri>(4);

    public Product(){}

//    public Product(String title,String category,String description,String qty,String discount,String price,String discountCalcPrice){
//
//        this.title = title;
//        this.category = category;
//        this.description = description;
//        this.qty = qty;
//        this.discount = discount;
//        this.price = price;
//        this.discountCalcPrice = discountCalcPrice;
//    }

    public void addProduct(String title,String category,String description,String qty,String discount,String price,String discountCalcPrice){
        this.title = title;
        this.category = category;
        this.description = description;
        this.qty = qty;
        this.discount = discount;
        this.price = price;
        this.discountCalcPrice = discountCalcPrice;
    }


    public String getTitle() {return title;}

    public String getCategory() {return category;}

    public String getDescription() {return description;}

    public String getQty() {return qty;}

    public String getDiscount() {return discount;}

    public String getPrice() {return price;}

    public String getDiscountCalcPrice() {return discountCalcPrice;}

    public String getImg0() {return img0;}

    public String getImg1() {return img1;}

    public String getImg2() {return img2;}

    public String getImg3() {return img3;}

    public String getImg4() {return img4;}

    public void setImg0(String img0) {this.img0 = img0;}

    public void setImg1(String img1) {this.img1 = img1;}

    public void setImg2(String img2) {this.img2 = img2;}

    public void setImg3(String img3) {this.img3 = img3;}

    public void setImg4(String img4) {this.img4 = img4;}
}
