package com.example.firsttask.ui.transactions.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.firsttask.R;
import com.example.firsttask.databinding.ActivityDisplayDataBinding;
import com.example.firsttask.ui.authentication.presenter.AuthenticationPresenter;
import com.example.firsttask.ui.authentication.view.AuthenticationActivity;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.ItemAdapter;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;

import java.util.ArrayList;

public class DisplayDataActivity extends AppCompatActivity implements Transactions.View {

    private AuthenticationPresenter authenticationPresenter = new AuthenticationPresenter();
    private TransactionsPresenter transactionsPresenter = new TransactionsPresenter(DisplayDataActivity.this);

    ActivityDisplayDataBinding binding;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = binding.getRoot();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!authenticationPresenter.isAuthenticated()) {
            startActivity(new Intent(DisplayDataActivity.this, AuthenticationActivity.class));
        } else {
            transactionsPresenter.getTransactionRecent();
        }

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticationPresenter.logout();
                startActivity(new Intent(DisplayDataActivity.this, AuthenticationActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        authenticationPresenter.logout();
    }

    @Override
    public void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array) {

        ItemAdapter itemAdapter = new ItemAdapter(array);
        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTransactions.setAdapter(itemAdapter);

    }

}