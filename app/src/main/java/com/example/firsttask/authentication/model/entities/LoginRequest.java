package com.example.firsttask.authentication.model.entities;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("merchant_code")
    private String merchantCode;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }
}
