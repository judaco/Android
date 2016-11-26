package com.example.juda.listofusers;

/**
 * Created by Juda on 26/11/2016.
 */

public class User {
    private String userName;
    private String password;
    private int image;


    public User(String userName, String password, int image) {
        this.userName = userName;
        this.password = password;
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
