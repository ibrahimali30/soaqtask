package com.ibrahim.soaqtask.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Categories implements Parcelable {
    private int id;
    private String titleEn;
    private String titleAr;
    private String photoUrl;
    private int productCount;
    private int haveModel;

    private List<Categories> subCategories;

    public Categories(int id ,String titleEn, String titleAr, String photoUrl, int productCount, int haveModel, List<Categories> subCategories) {
        this.id = id;
        this.titleEn = titleEn;
        this.titleAr = titleAr;
        this.photoUrl = photoUrl;
        this.productCount = productCount;
        this.haveModel = haveModel;
        this.subCategories = subCategories;
    }

    protected Categories(Parcel in) {
        id = in.readInt();
        titleEn = in.readString();
        titleAr = in.readString();
        photoUrl = in.readString();
        productCount = in.readInt();
        haveModel = in.readInt();
        subCategories = in.createTypedArrayList(Categories.CREATOR);
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };


    public String getTitleAr() {
        return titleAr;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getProductCount() {
        return productCount;
    }

    public List<Categories> getSubCategories() {
        return subCategories;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", titleEn='" + titleEn + '\'' +
                ", titleAr='" + titleAr + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", productCount=" + productCount +
                ", haveModel=" + haveModel +'\n'+"sub ="+
                ", subCategories=" + subCategories +
                '}'+'\n';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(titleEn);
        parcel.writeString(titleAr);
        parcel.writeString(photoUrl);
        parcel.writeInt(productCount);
        parcel.writeInt(haveModel);
        parcel.writeTypedList(subCategories);
    }
}
