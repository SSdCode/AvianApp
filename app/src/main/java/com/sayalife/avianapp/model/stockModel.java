package com.sayalife.avianapp.model;

public class stockModel {
    private final String Code;
    private final String category;
    private final String Name;
    private final String Specification;
    private final String Remains;
    private final String Brand;

    public stockModel(String code, String category, String name, String specification, String remains, String brand) {
        Code = code;
        this.category = category;
        Name = name;
        Specification = specification;
        Remains = remains;
        Brand = brand;
    }

    public String getCode() {
        return Code;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return Name;
    }

    public String getSpecification() {
        return Specification;
    }

    public String getRemains() {
        return Remains;
    }

    public String getBrand() {
        return Brand;
    }
}
