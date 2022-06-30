package com.example.firsttask.ui.transactions.adapter.entities;

import java.util.ArrayList;

public class ParentTransactionDescription {

    private String date;

    private ArrayList<TransactionDescription> transactionDescriptions;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<TransactionDescription> getTransactionDescriptions() {
        return transactionDescriptions;
    }

    public void setTransactionDescriptions(ArrayList<TransactionDescription> transactionDescriptions) {
        this.transactionDescriptions = transactionDescriptions;
    }
}
