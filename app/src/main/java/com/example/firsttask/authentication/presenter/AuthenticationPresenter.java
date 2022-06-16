package com.example.firsttask.authentication.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.firsttask.authentication.model.ApiClient;
import com.example.firsttask.authentication.model.entities.LoginRequest;
import com.example.firsttask.authentication.model.entities.LoginResponse;
import com.example.firsttask.authentication.model.entities.UserResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationPresenter {

    public void login(String username, String password, Context  context) {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setGrantType("password");
        loginRequest.setUsername("099125");
        loginRequest.setPassword("Vlad_890!!");
        loginRequest.setMerchantCode("303877");

        ApiClient.getUserService().getToken(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

//                String a = response.body().getToken_type();
//                String r = response.body().getToken_type();
//
//                Log.e("eee", response.body().getToken_type());

                Log.e("eee", "onResponse");

                if(response.isSuccessful()) {
/*
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("eee", response.body().getToken_type());

                            LoginResponse loginResponse = response.body();

                        }
                    },700);
*/

                Log.e("eee", response.body().getTokenType());

                    if (response.body().getAccessToken() != null) {
                        Toast.makeText(context, "Token was generated successfully", Toast.LENGTH_SHORT).show();
                        //sentToken(response.body().getAccess_token());
                    } else {
                        Toast.makeText(context, "ERROR token generation", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void sentToken(String token){

        Call<UserResponse> userResponseCall = ApiClient.getUserService().login(token);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
