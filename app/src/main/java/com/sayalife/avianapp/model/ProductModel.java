package com.sayalife.avianapp.model;

public class ProductModel {

    private int product_id;
    private String product_name;
    private String product_category;
    private String product_size;
    private String product_color;
    private int product_transferable;
    private int product_returnable;
    private int manufacturer_id;
    private String product_description;
    private String product_quantity;
    private String product_price;

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public ProductModel(int product_id, String product_name, String product_category, String product_size, String product_color, int product_transferable, int product_returnable, int manufacturer_id, String product_description, String product_quantity, String product_price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_size = product_size;
        this.product_color = product_color;
        this.product_transferable = product_transferable;
        this.product_returnable = product_returnable;
        this.manufacturer_id = manufacturer_id;
        this.product_description = product_description;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
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

    public int getProduct_transferable() {
        return product_transferable;
    }

    public int getProduct_returnable() {
        return product_returnable;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }

    public void setProduct_color(String product_color) {
        this.product_color = product_color;
    }

    public void setProduct_transferable(int product_transferable) {
        this.product_transferable = product_transferable;
    }

    public void setProduct_returnable(int product_returnable) {
        this.product_returnable = product_returnable;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }
}
