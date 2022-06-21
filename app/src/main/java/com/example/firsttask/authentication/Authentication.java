package com.example.firsttask.authentication;

public interface Authentication {

    interface View {

        void navigateToHomeActivity();

    }

    interface Presenter {

        void login(String username, String password);

    }

}
