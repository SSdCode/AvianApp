package com.sayalife.avianapp.model;

public class StoresModel {

    private int storeId;
    private int userId;
    private String storeName;
    private String licenceNo;
    private String contactNum;
    private String cityName;
    private String pinCode;
    private String address;

    public StoresModel(int storeId, int userId, String storeName, String licenceNo, String contactNum, String cityName, String pinCode, String address) {
        this.storeId = storeId;
        this.userId = userId;
        this.storeName = storeName;
        this.licenceNo = licenceNo;
        this.contactNum = contactNum;
        this.cityName = cityName;
        this.pinCode = pinCode;
        this.address = address;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public String getCityName() {
        return cityName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getAddress() {
        return address;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getUserId() {
        return userId;
    }

    public String getLicenceNo() {
        return licenceNo;
    }
}

