package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Parcel;
import android.os.Parcelable;
public class Category implements Parcelable{
    private int catID;
    private int catImage;
    private String catName;
    private int catIDParent;

    public Category(int catID, int catImage, String catName) {
        this.catID = catID;
        this.catImage = catImage;
        this.catName = catName;
    }

    public Category(int catID, int catImage, String catName, int catIDParent) {
        this.catID = catID;
        this.catImage = catImage;
        this.catName = catName;
        this.catIDParent = catIDParent;
    }

    public int getCatIDParent() {
        return catIDParent;
    }

    public void setCatIDParent(int catIDParent) {
        this.catIDParent = catIDParent;
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

    protected Category(Parcel in) {
        this.catID = in.readInt();
        this.catName = in.readString();
        this.catImage = in.readInt();
        this.catIDParent = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(catID);
        dest.writeString(catName);
        dest.writeInt(catImage);
        dest.writeInt(catIDParent);
    }
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

}
