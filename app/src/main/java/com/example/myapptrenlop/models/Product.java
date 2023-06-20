package com.example.myapptrenlop.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Product {
    @SerializedName("id")
    private Integer productID;
    @SerializedName("productDescription")
    private String product_description;
    @SerializedName("productName")
    private String product_name;
    @SerializedName("status")
    private String status;
    @SerializedName("price")
    private int product_price;
    @SerializedName("specification")
    private String specificaion;
    @SerializedName("calculationUnit")
    private String calculation_unit;
    @SerializedName("discount")
    private int discount;
    @SerializedName("sold")
    private int sold;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("imageIds")
    private int[] imageIds;
    @SerializedName("brandId")
    private int brand_id;
    @SerializedName("categoryId")
    private int category_id;
    @SerializedName("imageBase64")
    private String image;

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", product_description='" + product_description + '\'' +
                ", product_name='" + product_name + '\'' +
                ", status='" + status + '\'' +
                ", product_price=" + product_price +
                ", specificaion='" + specificaion + '\'' +
                ", calculation_unit='" + calculation_unit + '\'' +
                ", discount=" + discount +
                ", sold=" + sold +
                ", quantity=" + quantity +
                ", imageIds=" + Arrays.toString(imageIds) +
                ", brand_id=" + brand_id +
                ", category_id=" + category_id +
                ", image='" + image + '\'' +
                '}';
    }

    public int[] getImageIds() {
        return imageIds;
    }

    public void setImageIds(int[] imageIds) {
        this.imageIds = imageIds;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Product(Integer productID, String product_description, String product_name, String status,
                   int product_price, String specificaion, String calculation_unit,
                   int discount, int sold, int quantity, String image,
                   int brand_id, int category_id, int[] imageIds) {
        this.productID = productID;
        this.calculation_unit = calculation_unit;
        this.discount = discount;
        this.product_price = product_price;
        this.product_description = product_description;
        this.product_name = product_name;
        this.sold = sold;
        this.quantity = quantity;
        this.specificaion = specificaion;
        this.status = status;
        this.brand_id = brand_id;
        this.category_id = category_id;
        this.image =image;
        this.imageIds =imageIds;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getCalculation_unit() {
        return calculation_unit;
    }

    public void setCalculation_unit(String calculation_unit) {
        this.calculation_unit = calculation_unit;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSpecificaion() {
        return specificaion;
    }

    public void setSpecificaion(String specificaion) {
        this.specificaion = specificaion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }





}