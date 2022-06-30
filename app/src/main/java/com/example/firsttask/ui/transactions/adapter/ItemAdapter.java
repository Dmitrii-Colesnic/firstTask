package com.example.firsttask.ui.transactions.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.adapter.entities.TransactionDescription;
import com.example.firsttask.databinding.ItemDescriptionBinding;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<TransactionDescription> array;
    private Transactions.Fragment fragment;

    public void setChecked(int position, int isChecked) {
        array.get(position).setIsChecked(isChecked);
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvDescription;
        private final TextView tvTime;
        private final TextView tvAmount;
        private final TextView tvFee;
        private final ImageView iv;
        private final ImageView ivIsChecked;
        private final ConstraintLayout constraintLayout;

        public ViewHolder(ItemDescriptionBinding binding) {
            super(binding.getRoot());

            this.tvName = binding.tvName;
            this.tvDescription = binding.tvDescription;
            this.tvTime = binding.tvTime;
            this.tvAmount = binding.tvAmount;
            this.tvFee = binding.tvFee;
            this.iv = binding.iv;
            this.ivIsChecked = binding.ivIsChecked;
            this.constraintLayout = binding.clMainLayout;
        }
    }

    public ItemAdapter(ArrayList<TransactionDescription> dataSet,
                       Transactions.Fragment fragment) {
        array = dataSet;
        this.fragment = fragment;
    }

    public ItemAdapter(ArrayList<TransactionDescription> array) {
        this.array = array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDescriptionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
//        if (!payloads.isEmpty()) {
////            holder.ivIsChecked.setImageResource((Integer)payloads.get(0));
////            array.get(position).setIsChecked((Integer)payloads.get(0));
//        } else {
//            super.onBindViewHolder(holder, position, payloads);
//        }
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TransactionDescription transaction = array.get(position);

        int pos = position;

        String name;
        if(transaction.getName().length() > 16){
            name = transaction.getName().substring(0,16) + "...";
        } else {
            name = transaction.getName();
        }
        holder.tvName.setText(name);

        String description;
        if(transaction.getDescription() != null){

            if(transaction.getDescription().length() > 16){
                description = transaction.getDescription().substring(0,16) + "...";
            } else {
                description = transaction.getDescription();
            }
            holder.tvDescription.setText(description);

        } else {
            holder.tvDescription.setVisibility(View.GONE);
        }

        holder.tvTime.setText(transaction.getTime());
        holder.tvAmount.setText(transaction.getAmount());
        holder.tvFee.setText(transaction.getFee());
        holder.iv.setImageResource(transaction.getImage());
        if(transaction.getIsChecked() == 0){
            holder.ivIsChecked.setVisibility(View.GONE);
        } else {
            holder.ivIsChecked.setVisibility(View.VISIBLE);
            holder.ivIsChecked.setImageResource(transaction.getIsChecked());
        }

        holder.ivIsChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int newImageIsChecked = fragment.changeIsChecked(transaction);
//                array.get(pos).setIsChecked(newImageIsChecked);
//                holder.ivIsChecked.setImageResource(newImageIsChecked);
                if(fragment != null){
                    fragment.changeIsChecked(transaction, pos);
                }
            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment != null){
                    fragment.getTransactionDetails(transaction.getTransactionKey());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

}

