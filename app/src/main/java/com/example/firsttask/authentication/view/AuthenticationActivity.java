package com.example.firsttask.authentication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firsttask.R;
import com.example.firsttask.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.databinding.ActivityAuthenticationBinding;

public class AuthenticationActivity extends AppCompatActivity {

    private ActivityAuthenticationBinding binding;

    private AuthenticationPresenter presenter = new AuthenticationPresenter();;

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
                    presenter.login(binding.etUsername.getText().toString(), binding.etPassword.getText().toString(), AuthenticationActivity.this);
                }

            }
        });

    }

}