package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int cusID;
    private String fullName;
    private String email;
    private String userName;
    private String passWord;
    private String address;
    private String userPhone;

    public User(int cusID, String fullName, String email, String userName, String passWord, String address,String userPhone) {
        this.cusID = cusID;
        this.fullName = fullName;
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
        this.address = address;
        this.userPhone = userPhone;
    }

    protected User(Parcel in) {
        this.cusID = in.readInt();
        this.fullName = in.readString();
        this.email = in.readString();
        this.userName = in.readString();
        this.passWord = in.readString();
        this.address = in.readString();
        this.userPhone = in.readString();
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public int getCusID() {
        return cusID;
    }

    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cusID);
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(userName);
        dest.writeString(passWord);
        dest.writeString(address);
        dest.writeString(userPhone);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
