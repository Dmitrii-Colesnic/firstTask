package com.example.firsttask.ui.transactions.view;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firsttask.databinding.FragmentAllItemsBinding;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.ItemAdapter;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;

import java.util.ArrayList;

public class AllItemsFragment extends Fragment implements Transactions.Fragment {

    private FragmentAllItemsBinding binding;

    private TransactionsPresenter transactionsPresenter = new TransactionsPresenter(AllItemsFragment.this);

    private ItemAdapter itemAdapter;

    public AllItemsFragment() {}

    public AllItemsFragment(ItemAdapter itemAdapter) {
        this.itemAdapter = itemAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllItemsBinding.inflate(inflater, container, false);

        transactionsPresenter.getData();

        return binding.getRoot();
    }

    @Override
    public void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array) {

        ItemAdapter itemAdapter = new ItemAdapter(array);
        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvTransactions.setAdapter(itemAdapter);

    }

    @Override
    public void changeIsChecked(TransactionDescription transaction) {
        transactionsPresenter.changeIsChecked(transaction);
    }

}