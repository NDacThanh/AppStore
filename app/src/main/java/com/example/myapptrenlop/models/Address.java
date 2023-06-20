package com.example.myapptrenlop.models;

import com.google.gson.annotations.SerializedName;

public class Address {
        @SerializedName("id")
        private Integer id;
        @SerializedName("addressGeneral")
        private String addressGeneral;
        @SerializedName("addressSpecific")
        private String addressSpecific;
        @SerializedName("userId")
        private Integer userId;

        public Address(Integer id, String addressGeneral, String addressSpecific, Integer userId) {
                this.id = id;
                this.addressGeneral = addressGeneral;
                this.addressSpecific = addressSpecific;
                this.userId = userId;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getAddressGeneral() {
                return addressGeneral;
        }

        public void setAddressGeneral(String addressGeneral) {
                this.addressGeneral = addressGeneral;
        }

        public String getAddressSpecific() {
                return addressSpecific;
        }

        public void setAddressSpecific(String addressSpecific) {
                this.addressSpecific = addressSpecific;
        }

        public Integer getUserId() {
                return userId;
        }

        public void setUserId(Integer userId) {
                this.userId = userId;
        }
}
