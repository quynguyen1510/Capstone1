package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsPromotion implements Parcelable{

    private int Image;
    private String Description;
    private String Detail;

    public NewsPromotion(int image, String description, String detail) {
        Image = image;
        Description = description;
        Detail = detail;
    }

    protected NewsPromotion(Parcel in) {
        this.Image = in.readInt();
        this.Description = in.readString();
        this.Detail = in.readString();
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Image);
        dest.writeString(Description);
        dest.writeString(Detail);
    }
    public static final Creator<NewsPromotion> CREATOR = new Creator<NewsPromotion>() {
        @Override
        public NewsPromotion createFromParcel(Parcel in) {
            return new NewsPromotion(in);
        }

        @Override
        public NewsPromotion[] newArray(int size) {
            return new NewsPromotion[size];
        }
    };
}
