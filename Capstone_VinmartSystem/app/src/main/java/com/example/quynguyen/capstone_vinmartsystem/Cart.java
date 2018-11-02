package com.example.quynguyen.capstone_vinmartsystem;

public class Cart  {
    private int cartID;
    private String productName;
    private int productImg;
    private int productID;
    private int quantity;
    private int price;
    private int cusID;

    public Cart(String productName, int productImg, int productID, int quantity, int price, int cusID) {
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
}
