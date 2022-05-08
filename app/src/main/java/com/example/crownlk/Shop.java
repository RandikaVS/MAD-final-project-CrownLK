package com.example.crownlk;

public class Shop {

    public String shopName;
    public String shopAddress;
    public String ShopDescription;
    public String ShopSlogan;
    public String ShopLinks;

    public Shop(){}

    public Shop(String shopName, String shopAddress, String ShopDescription, String ShopSlogan, String ShopLinks){
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.ShopDescription = ShopDescription;
        this.ShopSlogan = ShopSlogan;
        this.ShopLinks = ShopLinks;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getShopDescription() {
        return ShopDescription;
    }

    public String getShopSlogan() {
        return ShopSlogan;
    }

    public String getShopLinks() {
        return ShopLinks;
    }
}
