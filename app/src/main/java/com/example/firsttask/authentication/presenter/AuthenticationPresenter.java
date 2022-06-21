package com.example.firsttask.authentication.presenter;

import static com.example.firsttask.authentication.model.sharedpref.SharedPrefTokenStorage.DEFAULT_VALUE;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.firsttask.authentication.App;
import com.example.firsttask.authentication.Authentication;
import com.example.firsttask.authentication.model.retrofit.entities.LoginRequest;
import com.example.firsttask.authentication.model.retrofit.entities.LoginResponse;
import com.example.firsttask.authentication.model.retrofit.entities.ReturnObject;
import com.example.firsttask.authentication.model.retrofit.entities.UserResponse;
import com.example.firsttask.authentication.model.sharedpref.SharedPrefTokenStorage;
import com.example.firsttask.authentication.presenter.entities.TransactionDescription;


import java.util.ArrayList;

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

    private void getTransactionRecent() {

        String token = sharedPrefTokenStorage.getToken();

        Call<UserResponse> userResponseCall = app.getUserService().getTransactionRecent(String.format("bearer $s" ,token));

        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.e("getTransactionRecent", "onResponse");

                ArrayList<TransactionDescription> transactions = new ArrayList();

                for(ReturnObject item : response.body().getReturnObject()){
                    transactions.add(new TransactionDescription(
                            item.getName(),
                            item.getDescription(),
                            item.getDateSorted(),
                            item.getAmount(),
                            item.getFee()
                    ));
                }



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
