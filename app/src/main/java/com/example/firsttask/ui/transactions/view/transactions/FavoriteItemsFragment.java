package com.example.firsttask.ui.transactions.view.transactions;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firsttask.App;
import com.example.firsttask.R;
import com.example.firsttask.databinding.FragmentFavoriteItemsBinding;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.adapter.ItemAdapter;
import com.example.firsttask.ui.transactions.adapter.entities.ParentTransactionDescription;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;
import com.example.firsttask.ui.transactions.adapter.entities.TransactionDescription;

import java.util.ArrayList;

public class FavoriteItemsFragment extends Fragment implements Transactions.Fragment {

    private FragmentFavoriteItemsBinding binding;

    private final TransactionsPresenter transactionsPresenter = new TransactionsPresenter(FavoriteItemsFragment.this);

    private Dialog loadingDialog;

    private ItemAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        binding = FragmentFavoriteItemsBinding.inflate(inflater, container, false);

        ImageView ivCalendar = getActivity().findViewById(R.id.iv_calendar);
        ivCalendar.setVisibility(View.GONE);

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
    public void changeIsChecked(TransactionDescription transaction, int position) {
        transactionsPresenter.changeIsChecked(transaction, itemAdapter, position);
    }

    @Override
    public void showToast(String toastText) {
        Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUpListOfDataIntoParentRecyclerView(ArrayList<ParentTransactionDescription> array) {

    }

    @Override
    public void navigateToActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }

    @Override
    public void getTransactionDetails(String transactionKey) {

    }

    @Override
    public void getTransactionLink(String transactionKey) {}

    @Override
    public void linkDialog(String title, String message) {}

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

    @Override
    public void showNoInternetDialog() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.no_internet_dialog);
        dialog.setCancelable(false);

        dialog.findViewById(R.id.btn_try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.isNetworkAvailable()){
                    dialog.dismiss();
                    transactionsPresenter.getDataFromDB();

                }
            }
        });

        dialog.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                System.exit(0);
            }
        });

        dialog.show();

    }

}