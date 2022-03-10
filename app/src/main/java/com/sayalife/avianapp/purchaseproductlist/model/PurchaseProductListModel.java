package com.sayalife.avianapp.purchaseproductlist.model;

public class PurchaseProductListModel {
    private int batchID;
    private String brand;
    private String category;
    private String specification;
    private int quantity;
    private int price;

    public PurchaseProductListModel(int batchID, String brand, String category, String specification, int quantity, int price) {
        this.batchID = batchID;
        this.brand = brand;
        this.category = category;
        this.specification = specification;
        this.quantity = quantity;
        this.price = price;
    }

    public int getBatchID() {
        return batchID;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getSpecification() {
        return specification;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
