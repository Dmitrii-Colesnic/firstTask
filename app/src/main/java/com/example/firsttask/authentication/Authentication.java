package com.example.firsttask.authentication;

import com.example.firsttask.authentication.presenter.entities.TransactionDescription;

import java.util.ArrayList;

public interface Authentication {

    interface View {

        void navigateToHomeActivity();

        void invalidFieldsErrorDialog();

//        void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array);

    }

    interface Presenter {

        void login(String username, String password, String merchantCode);

    }

}
