package com.example.firsttask.ui.transactions.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


public class DataActivity extends AppCompatActivity implements Transactions.View {

    private AuthenticationPresenter authenticationPresenter = new AuthenticationPresenter();
    private TransactionsPresenter transactionsPresenter = new TransactionsPresenter(DataActivity.this);

    ActivityDataBinding binding;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView = binding.bottomNavigation;

        // drawer layout instance to toggle the menu icon to open drawer and back button to close drawer
        drawerLayout = binding.getRoot();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = binding.navigationView;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_logout:
                        transactionsPresenter.logout();
                }

                return true;
            }
        });

        if (!authenticationPresenter.isAuthenticated()) {
            startActivity(new Intent(DataActivity.this, AuthenticationActivity.class));
        } else {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, new AllItemsFragment()).commit();
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return selectFragment();
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
        if (bottomNavigationView.getSelectedItemId() == R.id.item_list) {
            fragmentTransaction.replace(R.id.fragment_place, new AllItemsFragment());
            fragmentTransaction.commit();
            return true;
        } else if (bottomNavigationView.getSelectedItemId() == R.id.item_favorites) {
            fragmentTransaction.replace(R.id.fragment_place, new FavoriteItemsFragment());
            fragmentTransaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateToAuthenticateActivity() {
        startActivity(new Intent(DataActivity.this, AuthenticationActivity.class));
    }

    public boolean selectFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        switch (bottomNavigationView.getSelectedItemId()) {
//
//            case R.id.item_list:
//                fragmentTransaction.replace(R.id.fragment_place, new AllItemsFragment()).commit();
//                return true;
//
//            case R.id.item_favorites:
//                fragmentTransaction.replace(R.id.fragment_place, new FavoriteItemsFragment()).commit();
//                return false;
//
//        }

        if (bottomNavigationView.getSelectedItemId() == R.id.item_favorites) {
            fragmentTransaction.replace(R.id.fragment_place, new AllItemsFragment()).commit();
            return true;

        } else if (bottomNavigationView.getSelectedItemId() == R.id.item_list) {
            fragmentTransaction.replace(R.id.fragment_place, new FavoriteItemsFragment()).commit();
            return true;
        }

        return false;
    }

}