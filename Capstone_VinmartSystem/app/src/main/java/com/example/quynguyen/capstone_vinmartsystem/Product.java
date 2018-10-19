package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int productImg;
    private String productName;
    private String productDescription;
    private int productPrice;
    private String productDate;

    public Product(int productImg, String productName, String productDescription, int productPrice, String productDate) {
        this.productImg = productImg;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productDate = productDate;
    }

    protected Product(Parcel in) {
        this.productImg = in.readInt();
        this.productName = in.readString();
        this.productDescription = in.readString();
        this.productPrice = in.readInt();
        this.productDate = in.readString();
    }

    public int getProductImg() {
        return productImg;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productImg);
        dest.writeString(productName);
        dest.writeString(productDescription);
        dest.writeInt(productPrice);
        dest.writeString(productDate);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
