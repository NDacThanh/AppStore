package com.example.myapptrenlop.models;

import com.google.gson.annotations.SerializedName;

public class Brand {

    @SerializedName("id")
    private int brandID;

    @SerializedName("name")
    private String brandName;

    @SerializedName("descipttion")
    private String brandDecription;

    public Brand(int brandID, String brandName, String brandDecription) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.brandDecription = brandDecription;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDecription() {
        return brandDecription;
    }

    public void setBrandDecription(String brandDecription) {
        this.brandDecription = brandDecription;
    }
}
