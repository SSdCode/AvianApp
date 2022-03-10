package com.sayalife.avianapp.model;

public class StoresModel {


    private String storeName;
    private String contactNum;
    private String cityName;
    private String stateName;
    private String pinCode;
    private String address;

    //Constructors


    public StoresModel(String storeName, String contactNum, String cityName, String stateName, String pinCode, String address) {
        this.storeName = storeName;
        this.contactNum = contactNum;
        this.cityName = cityName;
        this.stateName = stateName;
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

    public String getStateName() {
        return stateName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getAddress() {
        return address;
    }
}

