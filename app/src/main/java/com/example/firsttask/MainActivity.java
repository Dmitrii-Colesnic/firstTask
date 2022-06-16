package com.example.firsttask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firsttask.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.authentication.view.AuthenticationActivity;
import com.example.firsttask.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AuthenticationPresenter authenticationPresenter = new AuthenticationPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!authenticationPresenter.isAuthenticated(MainActivity.this)){
            startActivity(new Intent(this, AuthenticationActivity.class));
        }

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(authenticationPresenter.isAuthenticated(MainActivity.this)){
                    authenticationPresenter.logout(MainActivity.this);
                    startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
                }
            }
        });

    }

}