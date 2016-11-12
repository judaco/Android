package com.example.juda.listview;

/**
 * Created by Juda on 12/11/2016.
 */

public class Club {
    private String name;
    private int image;

    public Club(String name, int image){
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
