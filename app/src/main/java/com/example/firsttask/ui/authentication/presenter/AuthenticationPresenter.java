package com.example.firsttask.ui.authentication.presenter;

import static com.example.firsttask.ui.authentication.model.sharedpref.SharedPrefTokenStorage.DEFAULT_VALUE;

import android.util.Log;

import com.example.firsttask.App;
import com.example.firsttask.data.retrofit.entities.Error;
import com.example.firsttask.data.retrofit.entities.ErrorCodeAndMessage;
import com.example.firsttask.ui.authentication.Authentication;
import com.example.firsttask.data.retrofit.entities.LoginRequest;
import com.example.firsttask.data.retrofit.entities.LoginResponse;
import com.example.firsttask.ui.authentication.model.sharedpref.SharedPrefTokenStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

                            sharedPrefTokenStorage.saveToken(response.body().getAccessToken());
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

    @Override
    public String getLanguage() {
        String language = sharedPrefTokenStorage.getLanguage();

        if(language.equals("en-US")){
            language = "en";
        } else if(language.equals("ru-RU")){
            language = "ru";
        } else if(language.equals("tr-TR")){
            language = "tr";
        }

        return language;
    }

    @Override
    public void saveLanguage(String language) {

        if(language.equals("en")){
            language = "en-US";
        } else if(language.equals("ru")){
            language = "ru-RU";
        } else if(language.equals("tr")){
            language = "tr-TR";
        }

        sharedPrefTokenStorage.saveLanguage(language);
    }

    public boolean isAuthenticated() {

        if (sharedPrefTokenStorage.getToken().equals(DEFAULT_VALUE)) {
            return false;
        } else {
            return true;
        }

    }

}
