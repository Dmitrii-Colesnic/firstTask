package com.example.firsttask.authentication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firsttask.DisplayDataActivity;
import com.example.firsttask.MainActivity;
import com.example.firsttask.R;
import com.example.firsttask.authentication.Authentication;
import com.example.firsttask.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.databinding.ActivityAuthenticationBinding;

public class AuthenticationActivity extends AppCompatActivity implements Authentication.View {

    private ActivityAuthenticationBinding binding;

    private AuthenticationPresenter presenter = new AuthenticationPresenter(AuthenticationActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.etUsername.getText().toString().isEmpty()
                        || binding.etPassword.getText().toString().isEmpty()
                ) {
                    Toast.makeText(AuthenticationActivity.this, "Username/Password Required", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.login(binding.etUsername.getText().toString(), binding.etPassword.getText().toString());
                }

            }
        });

    }

    @Override
    public void navigateToHomeActivity() {
        startActivity(new Intent(AuthenticationActivity.this, DisplayDataActivity.class));
    }

}