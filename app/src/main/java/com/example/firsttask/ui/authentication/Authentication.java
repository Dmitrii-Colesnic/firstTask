package com.example.firsttask.ui.authentication;

public interface Authentication {

    interface View {

        void navigateToHomeActivity();

        void invalidFieldsErrorDialog();

    }

    interface Presenter {

        void login(String username, String password, String merchantCode);

    }

}
