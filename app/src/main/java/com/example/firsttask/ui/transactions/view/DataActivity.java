package com.example.firsttask.ui.transactions.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.firsttask.R;
import com.example.firsttask.databinding.ActivityDataBinding;
import com.example.firsttask.ui.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.ui.authentication.view.AuthenticationActivity;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;
import com.example.firsttask.ui.transactions.view.entities.InvoiceDetails;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


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

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // drawer layout instance to toggle the menu icon to open drawer and back button to close drawer
        drawerLayout = binding.getRoot();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            if (extras.getString("HistoryFragment").equals("HistoryFragment")) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, new HistoryFragment()).commit();
            }
        }

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.nav_logout:
                        transactionsPresenter.logout();
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
                }

                return true;
            }
        });

        if (!authenticationPresenter.isAuthenticated()) {
            startActivity(new Intent(DataActivity.this, AuthenticationActivity.class));
            finish();
        } else {
            if(extras == null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, new AllItemsFragment()).commit();
            }
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

}