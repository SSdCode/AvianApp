package com.sayalife.avianapp.model;

public class BrandModel {

    private String FirstName;
    private String LastName;
    private String mobile;
    private String address;
    private String city;
    private String state;
    private String pin_code;
    private String company;

    public BrandModel(String firstName, String lastName, String mobile, String address, String city, String state, String pin_code, String company) {
        FirstName = firstName;
        LastName = lastName;
        this.mobile = mobile;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pin_code = pin_code;
        this.company = company;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
