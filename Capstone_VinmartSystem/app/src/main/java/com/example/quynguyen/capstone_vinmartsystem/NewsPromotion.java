package com.example.quynguyen.capstone_vinmartsystem;

public class NewsPromotion {

    private int Image;
    private String Description;

    public NewsPromotion(int image, String description) {
        Image = image;
        Description = description;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
