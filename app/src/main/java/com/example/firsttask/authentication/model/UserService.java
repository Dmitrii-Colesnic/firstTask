package com.example.firsttask.authentication.model;

import com.example.firsttask.authentication.model.entities.LoginRequest;
import com.example.firsttask.authentication.model.entities.LoginResponse;
import com.example.firsttask.authentication.model.entities.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("/auth")
    Call<LoginResponse>getToken(@Body LoginRequest loginRequest);

    @GET("/api/Transaction/Recent")
    Call<UserResponse>login(@Body String token);

}
