package com.example.firsttask.ui.authentication.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.firsttask.ui.transactions.view.DataActivity;
import com.example.firsttask.ui.authentication.Authentication;
import com.example.firsttask.ui.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.databinding.ActivityAuthenticationBinding;

public class AuthenticationActivity extends AppCompatActivity implements Authentication.View {

    private ActivityAuthenticationBinding binding;

    private AuthenticationPresenter presenter = new AuthenticationPresenter(AuthenticationActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDefaultData("713565", "132736", "a111111!");

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.etUsername.getText().toString().isEmpty()
                        || binding.etPassword.getText().toString().isEmpty()
                        || binding.etMerchantCode.getText().toString().isEmpty()
                ) {
                    Toast.makeText(AuthenticationActivity.this, "Username/Password Required", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.login(
                            binding.etUsername.getText().toString(),
                            binding.etPassword.getText().toString(),
                            binding.etMerchantCode.getText().toString()
                    );
                }

            }
        });

    }

    private void setDefaultData(String merchantCode, String username, String password) {

        binding.etMerchantCode.setText(merchantCode);
        binding.etUsername.setText(username);
        binding.etPassword.setText(password);

    }

    @Override
    public void navigateToHomeActivity() {
        startActivity(new Intent(AuthenticationActivity.this, DataActivity.class));
        finish();
    }

    @Override
    public void invalidFieldsErrorDialog(String code, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AuthenticationActivity.this);
        dialog.setTitle("Error Code: " + code);
        dialog.setMessage(message);
        dialog.setPositiveButton("Ok", null);
        dialog.show();
    }

}