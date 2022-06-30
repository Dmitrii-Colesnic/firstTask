package com.example.firsttask.data.retrofit.entities.details;

import com.google.gson.annotations.SerializedName;

public class ReturnObjectDetails {

    @SerializedName("TransactionKey")
    private String transactionKey;

    @SerializedName("TransactionID")
    private Integer transactionID;

    @SerializedName("Amount")
    private Integer amount;


    @SerializedName("OrderNumber")
    private String orderNumber;

    @SerializedName("Status")
    private Integer status;

    @SerializedName("Categories")
    private String categories;

    @SerializedName("SentTo")
    private String sentTo;

    @SerializedName("Type")
    private Integer type;

    @SerializedName("Created")
    private String created;

    @SerializedName("Expired")
    private String expired;

    @SerializedName("PaymentDate")
    private String paymentDate;

    @SerializedName("Commission")
    private Integer commission;


    public String getTransactionKey() {
        return transactionKey;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public String getCategories() {
        return categories;
    }

    public String getSentTo() {
        return sentTo;
    }

    public Integer getType() {
        return type;
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

    public Integer getCommission() {
        return commission;
    }
}
