package com.example.firsttask.authentication.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("ReturnObject")
    private List<ReturnObject> returnObject;

}

class ReturnObject {

    @SerializedName("TransactionKey")
    private String transactionKey;

    @SerializedName("TransactionID")
    private int transactionID;

    @SerializedName("Name")
    private String name;

    @SerializedName("ExternalWFTypeCode")
    private String externalWFTypeCode;

    @SerializedName("Description")
    private String description;

    @SerializedName("DateTransaction")
    private String dateTransaction;

    @SerializedName("DateSorted")
    private String dateSorted;

    //TODO ????
    @SerializedName("Month")
    private String month;

    @SerializedName("TypeName")
    private String typeName;

    @SerializedName("Status")
    private int status;

    @SerializedName("Type")
    private int type;

    @SerializedName("Amount")
    private int amount;

    @SerializedName("Fee")
    private int fee;


    public String getTransactionKey() {
        return transactionKey;
    }

    public void setTransactionKey(String transactionKey) {
        this.transactionKey = transactionKey;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalWFTypeCode() {
        return externalWFTypeCode;
    }

    public void setExternalWFTypeCode(String externalWFTypeCode) {
        this.externalWFTypeCode = externalWFTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getDateSorted() {
        return dateSorted;
    }

    public void setDateSorted(String dateSorted) {
        this.dateSorted = dateSorted;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}