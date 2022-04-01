package com.sayalife.avianapp.model;

public class ExpenseTypeModel {

    private String expenseType;
    private int expenseTypeId;

    public ExpenseTypeModel(int expenseTypeId, String expenseType) {
        this.expenseType = expenseType;
        this.expenseTypeId = expenseTypeId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }
}
