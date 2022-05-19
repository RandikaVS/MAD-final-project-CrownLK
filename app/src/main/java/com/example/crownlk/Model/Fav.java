package com.example.crownlk.Model;

public class Fav {

    private String id;
    private String item_price;
    private String product_name;
    private String quantity;
    private String image;
    private String total_price;
    private String user_id;

    public Fav() {
    }

    public Fav(String id, String item_price, String product_name, String quantity, String image, String total_price, String user_id) {
        this.id = id;
        this.item_price = item_price;
        this.product_name = product_name;
        this.quantity = quantity;
        this.image = image;
        this.total_price = total_price;
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
