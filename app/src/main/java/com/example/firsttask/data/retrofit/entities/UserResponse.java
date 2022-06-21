package com.example.firsttask.data.retrofit.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("ReturnObject")
    private List<ReturnObject> returnObject;

    public List<ReturnObject> getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(List<ReturnObject> returnObject) {
        this.returnObject = returnObject;
    }

    public ReturnObject get(int index){
        return returnObject.get(index);
    }

}
