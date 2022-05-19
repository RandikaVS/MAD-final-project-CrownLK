package com.example.crownlk.Model;

public class User {

    public String userEmail;
    public String userName;
    public String userAddress;
    public String userNic;
    public String userId;
    public String userPhone;

    public User(){}

    public User(String userEmail, String userName, String userAddress, String userNic, String userId,String userPhone){
        this.userEmail = userEmail;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userNic = userNic;
        this.userId = userId;
        this.userPhone = userPhone;
    }


}
