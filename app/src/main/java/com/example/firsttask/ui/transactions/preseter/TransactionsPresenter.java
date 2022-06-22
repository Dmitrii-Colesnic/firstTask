package com.example.firsttask.ui.transactions.preseter;

import android.util.Log;

import com.example.firsttask.R;
import com.example.firsttask.data.App;
import com.example.firsttask.data.retrofit.entities.ReturnObject;
import com.example.firsttask.data.retrofit.entities.UserResponse;
import com.example.firsttask.ui.authentication.model.sharedpref.SharedPrefTokenStorage;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsPresenter implements Transactions.Presenter {

    private Transactions.View view;
    private Transactions.Fragment fragment;

    App app = new App();

    SharedPrefTokenStorage sharedPrefTokenStorage = new SharedPrefTokenStorage(app.getContext());

//    public TransactionsPresenter() {}

    public TransactionsPresenter(Transactions.View view) {
        this.view = view;
    }

    public TransactionsPresenter(Transactions.Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void getData() {

        String token = sharedPrefTokenStorage.getToken();

        Call<UserResponse> userResponseCall = app.getUserService().getTransactionRecent(String.format("bearer $s", token));

        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.e("getTransactionRecent", "onResponse");

                ArrayList<TransactionDescription> transactions = new ArrayList();

                for (ReturnObject item : response.body().getReturnObject()) {

                    TransactionDescription transaction = new TransactionDescription();

                    transaction.setName(item.getName());
                    transaction.setDescription(item.getDescription());
                    transaction.setFee(item.getFee().toString());

                    //setImage
                    if (item.getType() == 2) {
                        transaction.setImage(R.drawable.ic_money_type2);
                    } else {
                        transaction.setImage(R.drawable.ic_love_type3);
                    }

                    //setData
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date d = sdf.parse(item.getDateTransaction());
                        transaction.setTime(output.format(d));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //setAmount
                    Float amount = item.getAmount().floatValue()/100;
                    transaction.setAmount(amount.toString());

                    transactions.add(transaction);
                }

                fragment.setUpListOfDataIntoRecyclerView(transactions);

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("getTransactionRecent", "onFailure");
                t.printStackTrace();
            }
        });

    }


    public void logout() {
        sharedPrefTokenStorage.delete();
        view.navigateToAuthenticateActivity();
    }

}
