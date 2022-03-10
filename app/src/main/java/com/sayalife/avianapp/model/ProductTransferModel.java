package com.sayalife.avianapp.model;

public class ProductTransferModel {

    private final String from;
    private final String to;
    private final String product_code;
    private final String req_date;
    private final String product_status;
    private final String product_quantity;

    public ProductTransferModel(String from, String to, String product_code, String req_date, String product_status, String product_quantity) {
        this.from = from;
        this.to = to;
        this.product_code = product_code;
        this.req_date = req_date;
        this.product_status = product_status;
        this.product_quantity = product_quantity;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getProduct_code() {
        return product_code;
    }

    public String getReq_date() {
        return req_date;
    }

    public String getProduct_status() {
        return product_status;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }
}
