package com.sayalife.avianapp.model;

public class ProductTransferModel {

    private int id;
    private int from_store_id;
    private int to_store_id;
    private int product_id;
    private String req_date;
    private int product_status;
    private int product_quantity;

    public ProductTransferModel(int id, int from_store_id, int to_store_id, int product_id, String req_date, int product_status, int product_quantity) {
        this.id = id;
        this.from_store_id = from_store_id;
        this.to_store_id = to_store_id;
        this.product_id = product_id;
        this.req_date = req_date;
        this.product_status = product_status;
        this.product_quantity = product_quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom_store_id() {
        return from_store_id;
    }

    public void setFrom_store_id(int from_store_id) {
        this.from_store_id = from_store_id;
    }

    public int getTo_store_id() {
        return to_store_id;
    }

    public void setTo_store_id(int to_store_id) {
        this.to_store_id = to_store_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    public int getProduct_status() {
        return product_status;
    }

    public void setProduct_status(int product_status) {
        this.product_status = product_status;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }
}
