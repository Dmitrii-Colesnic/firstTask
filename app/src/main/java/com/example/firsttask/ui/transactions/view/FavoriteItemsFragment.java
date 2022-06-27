package com.example.firsttask.ui.transactions.view;

import android.app.AlertDialog;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firsttask.R;
import com.example.firsttask.databinding.FragmentFavoriteItemsBinding;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.ItemAdapter;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;

import java.util.ArrayList;

public class FavoriteItemsFragment extends Fragment implements Transactions.Fragment {

    private FragmentFavoriteItemsBinding binding;

    private final TransactionsPresenter transactionsPresenter = new TransactionsPresenter(FavoriteItemsFragment.this);

    private AlertDialog loadingDialog;

    ItemAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteItemsBinding.inflate(inflater, container, false);

        transactionsPresenter.getDataFromDB();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array) {

        if(array.isEmpty()){
            binding.tvNoArticles.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoArticles.setVisibility(View.GONE);
            itemAdapter = new ItemAdapter(array, this);
            binding.rvTransactions.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.rvTransactions.setAdapter(itemAdapter);
        }

    }

    @Override
    public int changeIsChecked(TransactionDescription transaction) {
        return transactionsPresenter.changeIsChecked(transaction);
    }

    @Override
    public void showToast(String toastText) {
        Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProgressDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(R.layout.loading_dialog).setCancelable(false);

        loadingDialog = builder.create();
        loadingDialog.show();

    }

    @Override
    public void dismissProgressDialog() {
        loadingDialog.dismiss();
    }


}