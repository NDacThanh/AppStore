package com.example.myapptrenlop.models;

public class HomeProduct {
    private int productID;
    private String product_name;
    private int discount;
    private double product_price;
    private String image;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public HomeProduct(int productID, String product_name, int discount, double product_price,String image) {
        this.productID = productID;
        this.product_name = product_name;
        this.discount = discount;
        this.product_price = product_price;
        this.image =image;
    }
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }
}
