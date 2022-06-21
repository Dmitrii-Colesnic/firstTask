package com.example.firsttask.ui.authentication.presenter;

import static com.example.firsttask.ui.authentication.model.sharedpref.SharedPrefTokenStorage.DEFAULT_VALUE;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.firsttask.data.App;
import com.example.firsttask.ui.authentication.Authentication;
import com.example.firsttask.data.retrofit.entities.LoginRequest;
import com.example.firsttask.data.retrofit.entities.LoginResponse;
import com.example.firsttask.data.retrofit.entities.ReturnObject;
import com.example.firsttask.data.retrofit.entities.UserResponse;
import com.example.firsttask.ui.authentication.model.sharedpref.SharedPrefTokenStorage;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationPresenter implements Authentication.Presenter {

    private Authentication.View view;

    App app = new App();

    SharedPrefTokenStorage sharedPrefTokenStorage = new SharedPrefTokenStorage(app.getContext());

    public AuthenticationPresenter() {}

    public AuthenticationPresenter(Authentication.View view) {
        this.view = view;
    }

    @Override
    public void login(String username, String password, String merchantCode) {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setGrantType("password");
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        loginRequest.setMerchantCode(merchantCode);

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

                } else  {
                    view.invalidFieldsErrorDialog();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("login", "onFailure");
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
