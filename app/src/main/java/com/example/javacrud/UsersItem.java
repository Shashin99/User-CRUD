package com.example.javacrud;

public class UsersItem {

    String userId;
    String userName;
    String userEmail;
    String userCountry;

    public UsersItem() {
    }

    public UsersItem(String userId, String userName, String userEmail, String userCountry) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCountry = userCountry;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }
}
