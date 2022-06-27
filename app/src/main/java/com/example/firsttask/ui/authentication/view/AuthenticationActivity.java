package com.example.firsttask.ui.authentication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.firsttask.R;
import com.example.firsttask.ui.transactions.view.DataActivity;
import com.example.firsttask.ui.authentication.Authentication;
import com.example.firsttask.ui.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.databinding.ActivityAuthenticationBinding;

import java.util.Locale;

public class AuthenticationActivity extends AppCompatActivity implements Authentication.View {

    private ActivityAuthenticationBinding binding;

    private AuthenticationPresenter presenter = new AuthenticationPresenter(AuthenticationActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDefaultLanguage();

        setDefaultData("303877", "099125", "Vlad_890!!");

        binding.rbLanguageEn.setOnClickListener(rbClickListener);
        binding.rbLanguageTr.setOnClickListener(rbClickListener);
        binding.rbLanguageRu.setOnClickListener(rbClickListener);

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

        /**
         * Clear focus on touch outside for all EditText inputs.
         *
         * https://gist.github.com/sc0rch/7c982999e5821e6338c25390f50d2993
         */
    }

    View.OnClickListener rbClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton)view;

            switch (rb.getId()) {
                case R.id.rb_language_en: setLocale("en"); break;
                case R.id.rb_language_tr: setLocale("tr"); break;
                case R.id.rb_language_ru: setLocale("ru"); break;
            }

        }
    };

    private void setDefaultLanguage() {

        String defaultLanguage = presenter.getLanguage();

        setLocale(defaultLanguage);

        if(defaultLanguage.equals(binding.rbLanguageEn.getText())){
            binding.rbLanguageEn.setChecked(true);
        } else if (defaultLanguage.equals(binding.rbLanguageTr.getText())) {
            binding.rbLanguageTr.setChecked(true);
        } else if (defaultLanguage.equals(binding.rbLanguageRu.getText())) {
            binding.rbLanguageRu.setChecked(true);
        }

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

    @Override
    public void setLocale(String language){
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        presenter.saveLanguage(language);
        resources.updateConfiguration(configuration, metrics);
        onConfigurationChanged(configuration);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        binding.tvLanguage.setText(R.string.select_language);
        binding.rbLanguageEn.setText(R.string.en);
        binding.rbLanguageTr.setText(R.string.tr);
        binding.rbLanguageRu.setText(R.string.ru);
        binding.tvAuthentication.setText(R.string.authentication);
        binding.tilMerchantCode.setHint(R.string.merchant_code);
        binding.tilUsername.setHint(R.string.username);
        binding.tilPassword.setHint(R.string.password);
        binding.btnLogin.setText(R.string.log_in);
    }



}