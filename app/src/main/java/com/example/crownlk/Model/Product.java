package com.example.crownlk.Model;

public class Product {

    private String category;
    private String description;
    private String discount;
    private String discountCalcPrice;
    private String img0;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String price;
    private String qty;
    private String title;

    public Product() {

    }

    public Product(String category, String description, String discount, String discountCalcPrice, String img0, String img1, String img2, String img3, String img4, String price, String qty, String title) {
        this.category = category;
        this.description = description;
        this.discount = discount;
        this.discountCalcPrice = discountCalcPrice;
        this.img0 = img0;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.price = price;
        this.qty = qty;
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountCalcPrice() {
        return discountCalcPrice;
    }

    public void setDiscountCalcPrice(String discountCalcPrice) {
        this.discountCalcPrice = discountCalcPrice;
    }

    public String getImg0() {
        return img0;
    }

    public void setImg0(String img0) {
        this.img0 = img0;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
