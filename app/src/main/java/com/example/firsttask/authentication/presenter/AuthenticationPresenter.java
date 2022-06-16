package com.example.firsttask.authentication.presenter;

import static com.example.firsttask.authentication.model.sharedpref.SharedPrefTokenStorage.DEFAULT_VALUE;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.firsttask.authentication.model.retrofit.ApiClient;
import com.example.firsttask.authentication.model.retrofit.entities.LoginRequest;
import com.example.firsttask.authentication.model.retrofit.entities.LoginResponse;
import com.example.firsttask.authentication.model.retrofit.entities.UserResponse;
import com.example.firsttask.authentication.model.sharedpref.SharedPrefTokenStorage;
import com.example.firsttask.authentication.view.AuthenticationActivity;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationPresenter {

    public void login(String username, String password, Context context) {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setGrantType("password");
        loginRequest.setUsername("099125");
        loginRequest.setPassword("Vlad_890!!");
        loginRequest.setMerchantCode("303877");

        ApiClient.getUserService().getToken(loginRequest.getGrantType(), loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getMerchantCode())
                .enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e("eee", "onResponse");

                if (response.isSuccessful()) {

                    SharedPrefTokenStorage sharedPrefTokenStorage = new SharedPrefTokenStorage(context);
                    sharedPrefTokenStorage.save(response.body().getAccessToken());

                    sentToken(response.body().getAccessToken());
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("eee", "onFailure");
                t.printStackTrace();
            }
        });

    }

    private void sentToken(String token) {

        Map<String,String> map = new HashMap<String, String>(){{
            put("Authorization", "bearer"+token);
        }};

        Call<UserResponse> userResponseCall = ApiClient.getUserService().login("bearer"+token);

        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                Log.e("eee", "onResponseToken");
                Log.e("eee", response.body().toString());

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public boolean isAuthenticated(Context context){

        SharedPrefTokenStorage sharedPrefTokenStorage = new SharedPrefTokenStorage(context);

        if(sharedPrefTokenStorage.getToken().equals(DEFAULT_VALUE)){
            return false;
        } else {
            return true;
        }

    }

    public void logout(Context context) {
        SharedPrefTokenStorage sharedPrefTokenStorage = new SharedPrefTokenStorage(context);
        sharedPrefTokenStorage.delete();
    }
}
