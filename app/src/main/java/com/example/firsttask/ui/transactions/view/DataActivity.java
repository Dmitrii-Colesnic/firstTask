package com.example.firsttask.ui.transactions.view;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.firsttask.App;
import com.example.firsttask.R;
import com.example.firsttask.databinding.ActivityDataBinding;
import com.example.firsttask.ui.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.ui.authentication.view.AuthenticationActivity;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;
import com.example.firsttask.ui.transactions.view.chart.CreatingChartFragment;
import com.example.firsttask.ui.transactions.view.entities.InvoiceDetails;
import com.example.firsttask.ui.transactions.view.history.HistoryFragment;
import com.example.firsttask.ui.transactions.view.transactions.AllItemsFragment;
import com.example.firsttask.ui.transactions.view.transactions.FavoriteItemsFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;


public class DataActivity extends AppCompatActivity implements Transactions.View {

    private AuthenticationPresenter authenticationPresenter = new AuthenticationPresenter();
    private TransactionsPresenter transactionsPresenter = new TransactionsPresenter(DataActivity.this);

    private ActivityDataBinding binding;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setLocale();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.e("extras", extras.toString());
            if (extras.getString("HistoryFragment") != null)
                if (extras.getString("HistoryFragment").equals("HistoryFragment")) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, new HistoryFragment()).commit();
                    binding.bottomNavigation.setVisibility(View.GONE);
                }
        } else {
            binding.bottomNavigation.setVisibility(View.VISIBLE);
        }

        // drawer layout instance to toggle the menu icon to open drawer and back button to close drawer
        drawerLayout = binding.getRoot();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("TAG_WhirligigChartFragment");
                if(fragment != null)
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.nav_logout:
                        transactionsPresenter.logout();
                        transactionsPresenter.deleteStartDate();
                        transactionsPresenter.deleteEndDate();
                        break;
                    case R.id.nav_History:
                        fragmentTransaction.replace(R.id.fragment_place, new HistoryFragment()).commit();
                        binding.bottomNavigation.setVisibility(View.GONE);
                        drawerLayout.close();
                        break;
                    case R.id.nav_Transactions:
                        fragmentTransaction.replace(R.id.fragment_place, new AllItemsFragment()).commit();
                        binding.bottomNavigation.setVisibility(View.VISIBLE);
                        drawerLayout.close();
                        break;
                    case R.id.nav_material:
                        fragmentTransaction.replace(R.id.fragment_place, new CreatingChartFragment()).commit();
                        binding.bottomNavigation.setVisibility(View.GONE);
                        drawerLayout.close();
                }

                return true;
            }
        });

//        if (!authenticationPresenter.isAuthenticated()) {
//            startActivity(new Intent(DataActivity.this, AuthenticationActivity.class));
//            finish();
//        } else {
            if (extras == null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, new AllItemsFragment()).commit();
//            }
        }

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return selectFragment(item);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (binding.bottomNavigation.getSelectedItemId() == R.id.item_list) {
            fragmentTransaction.replace(R.id.fragment_place, new AllItemsFragment()).commit();
            return true;
        } else if (binding.bottomNavigation.getSelectedItemId() == R.id.item_favorites) {
            fragmentTransaction.replace(R.id.fragment_place, new FavoriteItemsFragment()).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateToAuthenticateActivity() {
        startActivity(new Intent(DataActivity.this, AuthenticationActivity.class));
        finish();
    }

    @Override
    public void showToast(String toastText) {
        Toast.makeText(DataActivity.this, toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoInternetDialog() {

        Dialog dialog = new Dialog(DataActivity.this);
        dialog.setContentView(R.layout.no_internet_dialog);
        dialog.setCancelable(false);

        dialog.findViewById(R.id.btn_try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.isNetworkAvailable()) {
                    dialog.dismiss();
                    startActivity(new Intent(DataActivity.this, DataActivity.class));
                    finish();
                }
            }
        });

        dialog.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        dialog.show();

    }

    @Override
    public void setProgressDialog() {
    }

    @Override
    public void dismissProgressDialog() {
    }

    @Override
    public void setDetailsData(InvoiceDetails invoiceDetails) {
    }

    public boolean selectFragment(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (item.getItemId() == R.id.item_list) {
            fragmentTransaction.replace(R.id.fragment_place, new AllItemsFragment()).commit();
            return true;

        } else if (item.getItemId() == R.id.item_favorites) {
            fragmentTransaction.replace(R.id.fragment_place, new FavoriteItemsFragment()).commit();
            return true;
        }

        return false;
    }

    /**
     * Clear focus on touch outside for all EditText inputs.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void setLocale() {
        String language = authenticationPresenter.getLanguage();

//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//        Configuration config = getBaseContext().getResources().getConfiguration();
//        config.locale = locale;
//        getBaseContext()
//                .getResources()
//                .updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration, metrics);
        onConfigurationChanged(configuration);

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        MenuItem itemAllList = binding.bottomNavigation.getMenu().findItem(R.id.item_list);
        MenuItem itemFavorites = binding.bottomNavigation.getMenu().findItem(R.id.item_favorites);
        MenuItem navLogout = binding.navigationView.getMenu().findItem(R.id.nav_logout);
        MenuItem navHistory = binding.navigationView.getMenu().findItem(R.id.nav_History);
        MenuItem navTransactions = binding.navigationView.getMenu().findItem(R.id.nav_Transactions);

        binding.tvTransactionsToolbar.setText(R.string.transactions);
        itemAllList.setTitle(R.string.all_list);
        itemFavorites.setTitle(R.string.favorites);
        navLogout.setTitle(R.string.logout);
        navHistory.setTitle(R.string.history);
        navTransactions.setTitle(R.string.transactions_favorites);
    }


}