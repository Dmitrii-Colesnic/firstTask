package com.example.firsttask.ui.authentication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

                if (fieldsIsNotEmpty()) {
                    if(binding.etMerchantCode.getText().length() < 6
                            || binding.etUsername.getText().length() < 6
                    ){
                        Toast.makeText(AuthenticationActivity.this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
                    } else {
                        presenter.login(
                                binding.etUsername.getText().toString(),
                                binding.etPassword.getText().toString(),
                                binding.etMerchantCode.getText().toString()
                        );
                    }
                }

            }
        });

    }

    /**
     * Clear focus on touch outside for all EditText inputs.
     *
     * https://gist.github.com/sc0rch/7c982999e5821e6338c25390f50d2993
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    View.OnClickListener rbClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton) view;

            switch (rb.getId()) {
                case R.id.rb_language_en:
                    setLocale("en");
                    break;
                case R.id.rb_language_tr:
                    setLocale("tr");
                    break;
                case R.id.rb_language_ru:
                    setLocale("ru");
                    break;
            }

        }
    };

    private void setDefaultLanguage() {

        String defaultLanguage = presenter.getLanguage();

        setLocale(defaultLanguage);

        if (defaultLanguage.equals(binding.rbLanguageEn.getText())) {
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

        int yellow = getResources().getColor(R.color.primary);

        binding.etMerchantCode.setOnFocusChangeListener((v, hasFocus) -> {
            int color;
            if(!hasFocus){
                if(binding.etMerchantCode.getText().length() < 6  &&  binding.etMerchantCode.getText().length() >= 1){
                    binding.tvMerchantCodeError.setVisibility(View.VISIBLE);
                } else {
                    binding.tvMerchantCodeError.setVisibility(View.GONE);
                }
                color = Color.GRAY;
            } else {
                binding.tvMerchantCodeError.setVisibility(View.GONE);
                color = yellow;
            }
            binding.tilMerchantCode.setStartIconTintList(ColorStateList.valueOf(color));
        });

        binding.etUsername.setOnFocusChangeListener((v, hasFocus) -> {
            int color;
            if(!hasFocus){
                if(binding.etUsername.getText().length() < 6  &&  binding.etUsername.getText().length() >= 1){
                    binding.tvUsernameError.setVisibility(View.VISIBLE);
                } else {
                    binding.tvUsernameError.setVisibility(View.GONE);
                }
                color = Color.GRAY;
            } else {
                binding.tvUsernameError.setVisibility(View.GONE);
                color = yellow;
            }
            binding.tilUsername.setStartIconTintList(ColorStateList.valueOf(color));
        });

        binding.etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int color;
                if(!hasFocus){
                    color = Color.GRAY;
                } else {
                    color = yellow;
                }
                binding.tilPassword.setStartIconTintList(ColorStateList.valueOf(color));
            }
        });
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
    public void setLocale(String language) {

        Locale locale = new Locale(language);
        presenter.saveLanguage(language);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext()
                .getResources()
                .updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

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
        binding.tvMerchantCodeError.setText(R.string.merchant_code_require_6_numbers);
        binding.tvUsernameError.setText(R.string.username_require_6_characters);

    }

    private boolean fieldsIsNotEmpty() {

        if(binding.etMerchantCode.getText().length() == 0
                || binding.etUsername.getText().length() == 0
                || binding.etPassword.getText().length() == 0
        ) {
            Toast.makeText(AuthenticationActivity.this, R.string.fields_is_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}