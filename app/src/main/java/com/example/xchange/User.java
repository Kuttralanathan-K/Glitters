package com.example.xchange;


public class User {

    public String username;
    public String email;
    public String mobile;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }



    public User(String email, String mobile, String username) {
        this.username = username;
        this.email = email;
        this.mobile=mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
