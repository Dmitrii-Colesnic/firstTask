package com.example.firsttask.ui.transactions.adapter.entities;

public class TransactionDescription {

    private String transactionKey;
    private String name;
    private String description;
    private String time;
    private String amount;
    private String fee;
    private int image;
    private int isChecked;

    public TransactionDescription() {}

    public TransactionDescription(String name, String description, String time, String amount, String fee, int image, int isChecked) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.amount = amount;
        this.fee = fee;
        this.image = image;
        this.isChecked = isChecked;
    }

    public String getTransactionKey() {
        return transactionKey;
    }

    public void setTransactionKey(String transactionKey) {
        this.transactionKey = transactionKey;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
