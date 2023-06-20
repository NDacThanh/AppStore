package com.example.myapptrenlop.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class User {
    @SerializedName("id")
    private Integer id;
    @SerializedName("userName")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("imageId")
    private int imageId;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("createDate")
    private Date createdAt;
    @SerializedName("status")
    private String status;
    @SerializedName("address")
    private Address address;
    @SerializedName("permissionId")
    private int permissionId;
    @SerializedName("addressIds")
    private List<Integer> addressIds;
    @SerializedName("imageBase64")
    private String imageBase64;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", imageId=" + imageId +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                ", address=" + address +
                ", permissionId=" + permissionId +
                ", addressIds=" + addressIds +
                ", imageBase64='" + imageBase64 + '\'' +
                '}';
    }

    public User(Integer id, String name, String password, String firstName, String lastName, int imageId, String email, String phone, Date createdAt, String status, Address address, int permissionId, List<Integer> addressIds, String imageBase64) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageId = imageId;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.status = status;
        this.address = address;
        this.permissionId = permissionId;
        this.addressIds = addressIds;
        this.imageBase64 = imageBase64;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public List<Integer> getAddressIds() {
        return addressIds;
    }

    public void setAddressIds(List<Integer> addressIds) {
        this.addressIds = addressIds;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
