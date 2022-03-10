package com.sayalife.avianapp.coupon.model;

public class CouponModel {
    private String code;
    private int value;
    private String type;
    private String valid_from;
    private String valid_till;

    public CouponModel(String code, int value, String type, String valid_from, String valid_till) {
        this.code = code;
        this.value = value;
        this.type = type;
        this.valid_from = valid_from;
        this.valid_till = valid_till;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getValid_from() {
        return valid_from;
    }

    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
    }

    public String getValid_till() {
        return valid_till;
    }

    public void setValid_till(String valid_till) {
        this.valid_till = valid_till;
    }
}
