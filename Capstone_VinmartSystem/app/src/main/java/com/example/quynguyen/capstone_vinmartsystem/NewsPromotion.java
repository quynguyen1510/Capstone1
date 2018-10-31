package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NewsPromotion implements Parcelable{

    private int newsID;
    private String newsName;
    private int newsImage;
    private String newsDescription;
    private String newsDetail;
    private String newsDate;
    private int parentID;

    public NewsPromotion(int newsID, String newsName, int newsImage, String newsDescription, String newsDetail, String newsDate) {
        this.newsID = newsID;
        this.newsName = newsName;
        this.newsImage = newsImage;
        this.newsDescription = newsDescription;
        this.newsDetail = newsDetail;
        this.newsDate = newsDate;
    }

    public NewsPromotion(int newsID, String newsName, int newsImage, String newsDescription, String newsDetail, String newsDate, int parentID) {
        this.newsID = newsID;
        this.newsName = newsName;
        this.newsImage = newsImage;
        this.newsDescription = newsDescription;
        this.newsDetail = newsDetail;
        this.newsDate = newsDate;
        this.parentID = parentID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public int getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(int newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsDetail() {
        return newsDetail;
    }

    public void setNewsDetail(String newsDetail) {
        this.newsDetail = newsDetail;
    }

    public NewsPromotion(int newsID, String newsName, int newsImage, String newsDescription, String newsDetail) {
        this.newsID = newsID;
        this.newsName = newsName;
        this.newsImage = newsImage;
        this.newsDescription = newsDescription;
        this.newsDetail = newsDetail;
    }

    protected NewsPromotion(Parcel in) {
        this.newsID = in.readInt();
        this.newsName = in.readString();
        this.newsImage = in.readInt();
        this.newsDescription = in.readString();
        this.newsDetail = in.readString();
        this.newsDate = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(newsID);
        dest.writeString(newsName);
        dest.writeInt(newsImage);
        dest.writeString(newsDescription);
        dest.writeString(newsDetail);
        dest.writeString(newsDate);
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
