package com.sayalife.avianapp.model;

public class ExpenseModel {

    private final int expenseId;
    private String expenseType;
    private String amount;
    private String date;
    private String description;

    public ExpenseModel(int expenseId,String expenseType, String amount, String date, String description) {
        this.expenseId = expenseId;
        this.expenseType = expenseType;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
