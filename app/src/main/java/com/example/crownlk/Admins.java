package com.example.crownlk;

public class Admins {

    public String userEmail;
    public String userName;
    public String userAddress;
    public String userNic;
    public String userPassword;

    public Admins(){}

    public Admins(String userEmail, String userName, String userAddress, String userNic, String userPassword){
        this.userEmail = userEmail;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userNic = userNic;
        this.userPassword = userPassword;
    }

    public String getUserEmail(){
        return this.userEmail;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserAddress() {
        return this.userAddress;
    }

    public String getUserNic() {
        return this.userNic;
    }

    public String getUserPassword() {
        return this.userPassword;
    }
}
