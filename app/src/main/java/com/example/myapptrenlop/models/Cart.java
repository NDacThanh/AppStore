package com.example.myapptrenlop.models;

public class Cart {

    private int productID;
    private String product_name;
    private int quantity;
    private int product_price;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }


    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }






    public Cart(int productID, int product_price, String product_name, int quantity,String image) {
        this.productID = productID;
        this.product_price = product_price;
        this.product_name = product_name;
        this.quantity = quantity;
        this.image = image;
    }

}
