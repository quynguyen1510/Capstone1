package com.example.quynguyen.capstone_vinmartsystem;

public class Category {
    private int catID;
    private int catImage;
    private String catName;

    public Category(int catID, int catImage, String catName) {
        this.catID = catID;
        this.catImage = catImage;
        this.catName = catName;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }
    public int getCatImage() {
        return catImage;
    }

    public void setCatImage(int catImage) {
        this.catImage = catImage;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
