package com.example.firsttask.ui.transactions.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.firsttask.R;
import com.example.firsttask.databinding.FragmentHistoryBinding;
import com.example.firsttask.ui.transactions.adapter.ParentItemAdapter;
import com.example.firsttask.ui.transactions.adapter.entities.ParentTransactionDescription;
import com.example.firsttask.ui.transactions.adapter.entities.TransactionDescription;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;

import java.util.ArrayList;


public class HistoryFragment extends Fragment implements Transactions.Fragment {

    private FragmentHistoryBinding binding;

    private final TransactionsPresenter transactionsPresenter = new TransactionsPresenter(HistoryFragment.this);

    private Dialog loadingDialog;

    private ParentItemAdapter parentItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        transactionsPresenter.getTransactionsHistory();

        return binding.getRoot();
    }

    @Override
    public void setUpListOfDataIntoParentRecyclerView(ArrayList<ParentTransactionDescription> array) {

        parentItemAdapter = new ParentItemAdapter(array, this);
        binding.rvParentTransaction.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvParentTransaction.setAdapter(parentItemAdapter);

    }

    @Override
    public void navigateToActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }

    @Override
    public void getTransactionDetails(String transactionKey) {

        Intent intent = new Intent(getActivity(), InvoiceDetailsActivity.class);
        intent.putExtra("transactionKey", transactionKey);
        startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array) {

    }

    @Override
    public void changeIsChecked(TransactionDescription transaction, int position) {
    }

    @Override
    public void showToast(String toastText) {
        Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProgressDialog() {
        loadingDialog = new Dialog(getActivity());
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        loadingDialog.dismiss();
    }
}