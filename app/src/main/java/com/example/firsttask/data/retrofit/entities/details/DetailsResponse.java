package com.example.firsttask.data.retrofit.entities.details;

import com.google.gson.annotations.SerializedName;

public class DetailsResponse {

    @SerializedName("ReturnObject")
    private ReturnObjectDetails returnObjectDetails;

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

    public ReturnObjectDetails getReturnObjectDetails() {
        return returnObjectDetails;
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
