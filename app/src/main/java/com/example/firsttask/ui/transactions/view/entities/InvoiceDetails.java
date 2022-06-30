package com.example.firsttask.ui.transactions.view.entities;

import com.google.gson.annotations.SerializedName;

public class InvoiceDetails {

    private String amount;

    private String orderNumber;

    private String categories;

    private String sentTo;

    private String created;

    private String expired;

    private String paymentDate;

    private String commission;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCategories() {
        return categories;
    }

    public String getSentTo() {
        return sentTo;
    }

    public String getCreated() {
        return created;
    }

    public String getExpired() {
        return expired;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

}
