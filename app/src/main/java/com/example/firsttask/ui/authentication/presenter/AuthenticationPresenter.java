package com.example.firsttask.ui.authentication.presenter;

import static com.example.firsttask.ui.authentication.model.sharedpref.SharedPrefTokenStorage.DEFAULT_VALUE;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.firsttask.data.App;
import com.example.firsttask.data.retrofit.entities.Error;
import com.example.firsttask.data.retrofit.entities.ErrorCodeAndMessage;
import com.example.firsttask.ui.authentication.Authentication;
import com.example.firsttask.data.retrofit.entities.LoginRequest;
import com.example.firsttask.data.retrofit.entities.LoginResponse;
import com.example.firsttask.data.retrofit.entities.ReturnObject;
import com.example.firsttask.data.retrofit.entities.UserResponse;
import com.example.firsttask.ui.authentication.model.sharedpref.SharedPrefTokenStorage;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationPresenter implements Authentication.Presenter {

    private Authentication.View view;

    App app = new App();

    SharedPrefTokenStorage sharedPrefTokenStorage = new SharedPrefTokenStorage(app.getContext());

    public AuthenticationPresenter() {
    }

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

        app.getUserService().
                login(loginRequest.getGrantType(), loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getMerchantCode())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<LoginResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {Log.e("login", "onSubscribe");}

                    @Override
                    public void onSuccess(Response<LoginResponse> response) {
                        Log.e("login", "onSuccess");

                        if(response.isSuccessful()){

                            sharedPrefTokenStorage.save(response.body().getAccessToken());
                            view.navigateToHomeActivity();

                        } else {

                            Gson gson = new GsonBuilder().create();
                            Error error = new Error();
                            try {
                                error = gson.fromJson(response.errorBody().string(), Error.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if(!error.getError().isEmpty()){

                                Gson gson2 = new GsonBuilder().create();
                                ErrorCodeAndMessage errorCodeAndMessage = gson.fromJson(error.getError(), ErrorCodeAndMessage.class);

                                if(!errorCodeAndMessage.getCode().isEmpty() && !errorCodeAndMessage.getMessage().isEmpty()){
                                    view.invalidFieldsErrorDialog(errorCodeAndMessage.getCode(), errorCodeAndMessage.getMessage());
                                }

                            } else {
                                view.invalidFieldsErrorDialog("Undefined Code", "Something went wrong, try again");
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("login", "onError");
                        e.printStackTrace();
                    }
                });

    }

    public boolean isAuthenticated() {

        if (sharedPrefTokenStorage.getToken().equals(DEFAULT_VALUE)) {
            return false;
        } else {
            return true;
        }

    }

}
