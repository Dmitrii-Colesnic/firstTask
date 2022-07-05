package com.example.firsttask.ui.transactions.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttask.databinding.ParentItemDescriptionBinding;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.adapter.entities.ParentTransactionDescription;

import java.util.ArrayList;

public class ParentItemAdapter extends RecyclerView.Adapter<ParentItemAdapter.ViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<ParentTransactionDescription> array;
    private Transactions.Fragment fragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView etDate;
        private final RecyclerView rvTransactions;

        public ViewHolder(ParentItemDescriptionBinding binding){
            super(binding.getRoot());

            this.etDate = binding.tvDate;
            this.rvTransactions = binding.rvTransactions;
        }

    }

    public ParentItemAdapter(ArrayList<ParentTransactionDescription> array, Transactions.Fragment fragment) {
        this.array = array;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ParentItemDescriptionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParentTransactionDescription item = array.get(position);

        holder.etDate.setText(item.getDate());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rvTransactions.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
//        layoutManager.setInitialPrefetchItemCount(item.getTransactionDescriptions().size());

        ItemAdapter itemAdapter = new ItemAdapter(item.getTransactionDescriptions(), fragment);

        Log.e("ParentAdapter", "itemAdapter");

        holder.rvTransactions.setLayoutManager(layoutManager);
        holder.rvTransactions.setAdapter(itemAdapter);
        holder.rvTransactions.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

}
