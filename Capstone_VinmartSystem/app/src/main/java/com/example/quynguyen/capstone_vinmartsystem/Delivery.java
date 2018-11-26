package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Parcel;
import android.os.Parcelable;

public class Delivery implements Parcelable{
    private int deliveryID;
    private int invoiceID;
    private String deliveryDate;
    private String cusAddress;
    private String cusPhone;

    public Delivery(int deliveryID, int invoiceID, String deliveryDate, String cusAddress, String cusPhone) {
        this.deliveryID = deliveryID;
        this.invoiceID = invoiceID;
        this.deliveryDate = deliveryDate;
        this.cusAddress = cusAddress;
        this.cusPhone = cusPhone;
    }

    protected Delivery(Parcel in) {
        deliveryID = in.readInt();
        invoiceID = in.readInt();
        deliveryDate = in.readString();
        cusAddress = in.readString();
        cusPhone = in.readString();
    }

    public static final Creator<Delivery> CREATOR = new Creator<Delivery>() {
        @Override
        public Delivery createFromParcel(Parcel in) {
            return new Delivery(in);
        }

        @Override
        public Delivery[] newArray(int size) {
            return new Delivery[size];
        }
    };

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deliveryID);
        dest.writeInt(invoiceID);
        dest.writeString(deliveryDate);
        dest.writeString(cusAddress);
        dest.writeString(cusPhone);
    }
}
