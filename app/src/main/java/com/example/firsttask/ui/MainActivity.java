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

//            firstLoad();
            startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
            finish();

        } else {

            startActivity(new Intent(MainActivity.this, DataActivity.class));
            finish();

        }

    }

    private void firstLoad(){
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
                finish();
            }
        }.start();
    }
}