package com.example.firsttask.data.retrofit.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("ReturnObject")
    private List<ReturnObject> returnObject;

    public List<ReturnObject> getReturnObject() {
        return returnObject;
    }

}

