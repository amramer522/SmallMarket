package com.amoor.SmallMarket.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable
{
    private String pro_name , pro_id,pro_price;

    public Product()
    {
    }

    public Product(String pro_id,String pro_name, String pro_price) {
        this.pro_name = pro_name;
        this.pro_id = pro_id;
        this.pro_price = pro_price;
    }

    protected Product(Parcel in) {
        pro_name = in.readString();
        pro_id = in.readString();
        pro_price = in.readString();
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

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pro_name);
        dest.writeString(pro_id);
        dest.writeString(pro_price);
    }
}
