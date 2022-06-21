package com.example.firsttask.authentication.presenter;

import static com.example.firsttask.authentication.model.sharedpref.SharedPrefTokenStorage.DEFAULT_VALUE;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.firsttask.authentication.App;
import com.example.firsttask.authentication.Authentication;
import com.example.firsttask.authentication.model.retrofit.entities.LoginRequest;
import com.example.firsttask.authentication.model.retrofit.entities.LoginResponse;
import com.example.firsttask.authentication.model.retrofit.entities.UserResponse;
import com.example.firsttask.authentication.model.sharedpref.SharedPrefTokenStorage;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class AuthenticationPresenter implements Authentication.Presenter {

    private Authentication.View view;

    App app = new App();

    SharedPrefTokenStorage sharedPrefTokenStorage = new SharedPrefTokenStorage(app.getContext());

    public AuthenticationPresenter() {}

    public AuthenticationPresenter(Authentication.View view) {
        this.view = view;
    }

    @Override
    public void login(String username, String password) {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setGrantType("password");
        loginRequest.setUsername("099125");
        loginRequest.setPassword("Vlad_890!!");
        loginRequest.setMerchantCode("303877");

        Call<LoginResponse> userResponseCall = app.getUserService()
                .login(loginRequest.getGrantType(), loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getMerchantCode());

        userResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e("login", "onResponse");

                if (response.isSuccessful()) {
                    sharedPrefTokenStorage.save(response.body().getAccessToken());

//                    view.startActivity(view, MainActivity.class);
                view.navigateToHomeActivity();

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("login", "onFailure");
                t.printStackTrace();
            }
        });

    }

    private void getTransactionRecent() {

        String token = sharedPrefTokenStorage.getToken();

        Call<UserResponse> userResponseCall = app.getUserService().getTransactionRecent(String.format("bearer $s" ,token));

        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.e("getTransactionRecent", "onResponse");

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("getTransactionRecent", "onFailure");
                t.printStackTrace();
            }
        });

    }

    public boolean isAuthenticated(){

        if(sharedPrefTokenStorage.getToken().equals(DEFAULT_VALUE)){
            return false;
        } else {
            return true;
        }

    }

    public void logout() {
        sharedPrefTokenStorage.delete();
    }

}
