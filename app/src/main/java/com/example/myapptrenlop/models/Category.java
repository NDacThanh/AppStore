package com.example.myapptrenlop.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {
    @SerializedName("id")
    private int categoryID;
    @SerializedName("name")
    private String categoryName;
    @SerializedName("imageBase64")
    private String categoryImage;
    @SerializedName("note")
    private String categoryNote;
    public String getCategoryNote() {
        return categoryNote;
    }

    public void setCategoryNote(String categoryNote) {
        this.categoryNote = categoryNote;
    }


    public Category(int categoryID, String categoryName, String categoryImage, String categoryNote) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.categoryImage = categoryImage;

        this.categoryNote=categoryNote;
    }



    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
