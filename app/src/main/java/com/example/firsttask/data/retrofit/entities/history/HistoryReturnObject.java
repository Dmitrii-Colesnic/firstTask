package com.example.firsttask.data.retrofit.entities.history;

import com.example.firsttask.data.retrofit.entities.ReturnObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryReturnObject {

    @SerializedName("Transactions")
    private List<ReturnObject> returnObjects;

    @SerializedName("SortedDate")
    private String sortedDate;

    public List<ReturnObject> getReturnObjects() {
        return returnObjects;
    }

    public String getSortedDate() {
        return sortedDate;
    }

}
