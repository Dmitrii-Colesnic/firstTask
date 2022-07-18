package com.example.firsttask.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firsttask.App;
import com.example.firsttask.data.sharedpref.SharedPrefFirstLaunch;
import com.example.firsttask.databinding.ActivityMainBinding;
import com.example.firsttask.ui.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.ui.authentication.view.AuthenticationActivity;
import com.example.firsttask.ui.transactions.view.DataActivity;
import com.example.firsttask.ui.whirligig.WhirligigActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AuthenticationPresenter authenticationPresenter = new AuthenticationPresenter();
    private SharedPrefFirstLaunch sharedPrefFirstLaunch = new SharedPrefFirstLaunch(App.getContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                if (sharedPrefFirstLaunch.firstLaunch()) {
                    startActivity(new Intent(MainActivity.this, WhirligigActivity.class));
                    sharedPrefFirstLaunch.save();
                } else if (!authenticationPresenter.isAuthenticated()) {
                    startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, DataActivity.class));
                }
                finish();

            }
        }.start();

    }
}