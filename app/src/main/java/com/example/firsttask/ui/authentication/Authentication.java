package com.example.firsttask.ui.authentication;

public interface Authentication {

    interface View {

        void navigateToHomeActivity();

        void invalidFieldsErrorDialog(String code, String message);

        void setLocale(String language);


    }

    interface Presenter {

        void login(String username, String password, String merchantCode);

        String getLanguage();

        void saveLanguage(String language);
    }

}
