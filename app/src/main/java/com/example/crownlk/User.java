package com.example.crownlk;

public class User {

    public String userEmail;
    public String userName;
    public String userAddress;
    public String userNic;
    public String userPassword;

    public User(){}

    public User(String userEmail, String userName, String userAddress, String userNic, String userPassword){
        this.userEmail = userEmail;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userNic = userNic;
        this.userPassword = userPassword;
    }
}
