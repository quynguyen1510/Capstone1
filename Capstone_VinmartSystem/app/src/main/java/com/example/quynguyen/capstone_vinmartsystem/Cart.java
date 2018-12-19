package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {
    private int cartID;
    private String productName;
    private int productImg;
    private int productID;
    private int quantity;
    private int price;
    private int cusID;

    public Cart(){

    }

    public Cart(int cartID, String productName, int productImg, int productID, int quantity, int price, int cusID) {
        this.cartID = cartID;
        this.productName = productName;
        this.productImg = productImg;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.cusID = cusID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductImg() {
        return productImg;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCusID() {
        return cusID;
    }

    public void setCusID(int cusID) {
        this.cusID = cusID;
    }
    protected Cart(Parcel in) {
        this.cartID = in.readInt();
        this.productName = in.readString();
        this.productID = in.readInt();
        this.productImg = in.readInt();
        this.quantity = in.readInt();
        this.price = in.readInt();
        this.cusID = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cartID);
        dest.writeString(productName);
        dest.writeInt(productImg);
        dest.writeInt(productID);
        dest.writeInt(quantity);
        dest.writeInt(price);
        dest.writeInt(cusID);
    }
    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
