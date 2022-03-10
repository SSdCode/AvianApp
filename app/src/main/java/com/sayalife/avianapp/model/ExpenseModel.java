package com.sayalife.avianapp.model;

public class ExpenseModel {

    private final String expenseType;
    private final String amount;
    private final String date;
    private final String description;

    public ExpenseModel(String expenseType, String amount, String date, String description) {
        this.expenseType = expenseType;
        this.amount = amount;
        this.date = date;
        this.description = description;
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
}
