package com.example.firsttask.data.retrofit;

import com.example.firsttask.data.retrofit.entities.LoginResponse;
import com.example.firsttask.data.retrofit.entities.UserResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @FormUrlEncoded
    @POST("/auth")
    Single<LoginResponse> login(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("merchant_code") String merchantCode
    );

    @GET("/api/Transaction/Recent")
    Single<UserResponse> getTransactionRecent();

}
