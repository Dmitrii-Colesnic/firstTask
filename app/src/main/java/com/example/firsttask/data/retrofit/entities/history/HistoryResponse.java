package com.example.firsttask.data.retrofit.entities.history;

import com.example.firsttask.data.retrofit.entities.ReturnObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryResponse {

    @SerializedName("ReturnObject")
    private List<HistoryReturnObject> historyReturnObject;

    @SerializedName("TransactionId")
    private Integer transactionId;

    @SerializedName("ResultCode")
    private Integer resultCode;

    @SerializedName("ResultMessage")
    private String resultMessage;

    @SerializedName("ResultPrivateMessage")
    private String resultPrivateMessage;

    @SerializedName("ValidationErrors")
    private String validationErrors;

    @SerializedName("IsOK")
    private Boolean isOK;

    public List<HistoryReturnObject> getHistoryReturnObject() {
        return historyReturnObject;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getResultPrivateMessage() {
        return resultPrivateMessage;
    }

    public String getValidationErrors() {
        return validationErrors;
    }

    public Boolean getOK() {
        return isOK;
    }
}


