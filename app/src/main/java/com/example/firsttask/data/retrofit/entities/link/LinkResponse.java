package com.example.firsttask.data.retrofit.entities.link;

import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;

import com.google.gson.annotations.SerializedName;

public class LinkResponse {

    @SerializedName("ReturnObject")
    private String returnObject;

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

    @SerializedName("IsOk")
    private Boolean isOk;


    public String getReturnObject() {
        return returnObject;
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

    public Boolean getOk() {
        return isOk;
    }
}
