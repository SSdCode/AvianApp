package com.sayalife.avianapp.model;

public class ProductPurchaseModel {
    private Integer Id;
    private Integer BrandId;
    private String Category;
    private String Specification;
    private Integer Quantity;
    private Integer Price;

    public ProductPurchaseModel(Integer id, Integer brandId, String category, String specification, Integer quantity, Integer price) {
        Id = id;
        BrandId = brandId;
        Category = category;
        Specification = specification;
        Quantity = quantity;
        Price = price;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getBrandId() {
        return BrandId;
    }

    public void setBrandId(Integer brandId) {
        BrandId = brandId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }
}
