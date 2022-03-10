package com.sayalife.avianapp.model;

public class ProductModel {

    private final String product_code;
    private final String product_name;
    private final String product_category;
    private final String product_size;
    private final String product_color;
    private final String product_transferable;
    private final String product_returnable;
    private final String product_description;
    private final String product_quantity;

    public ProductModel(String product_code, String product_name, String product_category, String product_size, String product_color, String product_transferable, String product_returnable, String product_description, String product_quantity) {
        this.product_code = product_code;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_size = product_size;
        this.product_color = product_color;
        this.product_transferable = product_transferable;
        this.product_returnable = product_returnable;
        this.product_description = product_description;
        this.product_quantity = product_quantity;
    }

    public String getProduct_code() {
        return product_code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public String getProduct_size() {
        return product_size;
    }

    public String getProduct_color() {
        return product_color;
    }

    public String getProduct_transferable() {
        return product_transferable;
    }

    public String getProduct_returnable() {
        return product_returnable;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }
}
