package com.sayalife.avianapp.model;

public class RevisedPurchaseModel {
    private Integer batchId;
    private Integer brandId;
    private String description;
    private Integer quantity;
    private Integer purchasePrice;
    private Integer revisedPrice;

    public RevisedPurchaseModel(Integer batchId, Integer brandId, String description, Integer quantity, Integer purchasePrice, Integer revisedPrice) {
        this.batchId = batchId;
        this.brandId = brandId;
        this.description = description;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.revisedPrice = revisedPrice;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getRevisedPrice() {
        return revisedPrice;
    }

    public void setRevisedPrice(Integer revisedPrice) {
        this.revisedPrice = revisedPrice;
    }
}
