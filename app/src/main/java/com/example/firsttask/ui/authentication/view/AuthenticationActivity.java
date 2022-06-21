package com.example.firsttask.ui.authentication.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firsttask.ui.transactions.view.DisplayDataActivity;
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

        setDefaultData("099125", "Vlad_890!!", "303877");

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.etUsername.getText().toString().isEmpty()
                        || binding.etPassword.getText().toString().isEmpty()
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

    private void setDefaultData(String username, String password, String merchantCode) {

        binding.etUsername.setText(username);
        binding.etPassword.setText(password);
        binding.etMerchantCode.setText(merchantCode);

    }

    @Override
    public void navigateToHomeActivity() {
        startActivity(new Intent(AuthenticationActivity.this, DisplayDataActivity.class));
    }

    @Override
    public void invalidFieldsErrorDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AuthenticationActivity.this);
        dialog.setTitle("Error");
        dialog.setMessage("Check username, password and merchant code fields");
        dialog.setPositiveButton("Ok", null);
        dialog.show();
    }

}