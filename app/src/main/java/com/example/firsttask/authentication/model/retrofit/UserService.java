package com.example.firsttask.authentication.model.retrofit;

import com.example.firsttask.authentication.model.retrofit.entities.LoginResponse;
import com.example.firsttask.authentication.model.retrofit.entities.UserResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface UserService {

    @FormUrlEncoded
    @POST("/auth")
    Call<LoginResponse> getToken(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("merchant_code") String merchantCode
    );

//    @GET("/api/Transaction/Recent")
//    Call<UserResponse> login(@QueryMap Map<String,String> authorization);

    @GET("/api/Transaction/Recent")
    Call<UserResponse> login(@Query("Authorization") String authorization);

}
