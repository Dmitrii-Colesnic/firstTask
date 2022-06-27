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


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

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
                .subscribe(new SingleObserver<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {Log.e("login", "onSubscribe");}

                    @Override
                    public void onSuccess(LoginResponse loginResponse) {
                        Log.e("login", "onSuccess");
                        sharedPrefTokenStorage.save(loginResponse.getAccessToken());
                        view.navigateToHomeActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("login", "onError");
                        view.invalidFieldsErrorDialog();
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
