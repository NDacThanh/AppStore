package com.example.myapptrenlop.models;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("productId")
    private Integer productId;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("unitPrice")
    private Integer unitPrice;


    public OrderDetail(Integer productId, Integer quantity, Integer unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }
}
