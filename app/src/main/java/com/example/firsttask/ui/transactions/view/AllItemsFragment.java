package com.example.firsttask.ui.transactions.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firsttask.R;
import com.example.firsttask.data.roomdatabase.TransactionDatabase;
import com.example.firsttask.data.roomdatabase.TransactionEntity;
import com.example.firsttask.databinding.FragmentAllItemsBinding;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.ItemAdapter;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;

import java.util.ArrayList;

public class AllItemsFragment extends Fragment implements Transactions.Fragment {

    private FragmentAllItemsBinding binding;

    private TransactionsPresenter transactionsPresenter = new TransactionsPresenter(AllItemsFragment.this);

    private AlertDialog loadingDialog;

    ItemAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllItemsBinding.inflate(inflater, container, false);

        transactionsPresenter.getData();

        return binding.getRoot();
    }

    @Override
    public void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array) {

        itemAdapter = new ItemAdapter(array,this);
        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvTransactions.setAdapter(itemAdapter);

    }

    @Override
    public void changeIsChecked(TransactionDescription transaction, int position) {
        transactionsPresenter.changeIsChecked(transaction, itemAdapter, position);
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