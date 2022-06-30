package com.example.firsttask.data.retrofit.entities.recent;

import com.example.firsttask.data.retrofit.entities.ReturnObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("ReturnObject")
    private List<ReturnObject> returnObject;

    public List<ReturnObject> getReturnObject() {
        return returnObject;
    }

}

