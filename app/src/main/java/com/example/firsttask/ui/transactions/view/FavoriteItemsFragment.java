package com.example.firsttask.ui.transactions.view;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firsttask.R;
import com.example.firsttask.databinding.FragmentFavoriteItemsBinding;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.ItemAdapter;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;

import java.util.ArrayList;

public class FavoriteItemsFragment extends Fragment /*implements Transactions.Fragment */{

    private FragmentFavoriteItemsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteItemsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    /*    @Override
    public void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array) {

//        ItemAdapter itemAdapter = new ItemAdapter(array);
//        binding.rvTransactions.setLayoutManager(new LinearLayoutManager(getActivity()));
//        binding.rvTransactions.setAdapter(itemAdapter);

    }*/

}