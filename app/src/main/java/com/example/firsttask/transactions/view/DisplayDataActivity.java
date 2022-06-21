package com.example.firsttask.transactions.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firsttask.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.authentication.view.AuthenticationActivity;
import com.example.firsttask.databinding.ActivityDisplayDataBinding;

public class DisplayDataActivity extends AppCompatActivity {

    private AuthenticationPresenter authenticationPresenter = new AuthenticationPresenter();

    ActivityDisplayDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!authenticationPresenter.isAuthenticated()) {
            startActivity(new Intent(DisplayDataActivity.this, AuthenticationActivity.class));
        }

            binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    authenticationPresenter.logout();
                    startActivity(new Intent(DisplayDataActivity.this, AuthenticationActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authenticationPresenter.logout();
    }



}