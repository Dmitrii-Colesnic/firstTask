package com.example.firsttask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.firsttask.databinding.ActivityMainBinding;
import com.example.firsttask.ui.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.ui.authentication.view.AuthenticationActivity;
import com.example.firsttask.ui.transactions.view.DataActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AuthenticationPresenter authenticationPresenter = new AuthenticationPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!authenticationPresenter.isAuthenticated()){

            startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
            finish();

        } else {

            startActivity(new Intent(MainActivity.this, DataActivity.class));
            finish();

        }

    }
}