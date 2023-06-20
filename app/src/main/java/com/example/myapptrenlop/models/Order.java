package com.example.myapptrenlop.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Order {
    @SerializedName("id")
    private Integer id;
    @SerializedName("date")
    private String orderDate;
    @SerializedName("status")
    private String status;
    @SerializedName("totalPrice")
    private int totalPrice;
    @SerializedName("paymentMode")
    private String paymentMode;
    @SerializedName("userId")
    private int userId;
    @SerializedName("orderDetailList")
    private List<OrderDetail> orderDetailList;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                ", paymentMode='" + paymentMode + '\'' +
                ", userId=" + userId +
                ", orderDetailList=" + orderDetailList +
                '}';
    }

    public Order(Integer id, String orderDate, String status, int totalPrice, String paymentMode, int userId, List<OrderDetail> orderDetailList) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.paymentMode = paymentMode;
        this.userId = userId;
        this.orderDetailList = orderDetailList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
