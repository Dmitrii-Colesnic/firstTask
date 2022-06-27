package com.example.firsttask.ui.transactions.preseter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;
import com.example.firsttask.databinding.ItemDescriptionBinding;
import com.example.firsttask.ui.transactions.view.AllItemsFragment;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<TransactionDescription> array;
    private Transactions.Fragment fragment;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvDescription;
        private final TextView tvTime;
        private final TextView tvAmount;
        private final TextView tvFee;
        private final ImageView iv;
        private final ImageView ivIsChecked;

        public ViewHolder(ItemDescriptionBinding binding) {
            super(binding.getRoot());

            this.tvName = binding.tvName;
            this.tvDescription = binding.tvDescription;
            this.tvTime = binding.tvTime;
            this.tvAmount = binding.tvAmount;
            this.tvFee = binding.tvFee;
            this.iv = binding.iv;
            this.ivIsChecked = binding.ivIsChecked;
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvDescription() {
            return tvDescription;
        }

        public TextView getTvTime() {
            return tvTime;
        }

        public TextView getTvAmount() {
            return tvAmount;
        }

        public TextView getTvFee() {
            return tvFee;
        }

        public ImageView getIv() {
            return iv;
        }
    }

    public ItemAdapter(ArrayList<TransactionDescription> dataSet,
                       Transactions.Fragment fragment) {
        array = dataSet;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDescriptionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            holder.ivIsChecked.setImageResource((Integer) payloads.get(0));
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        int pos = position;

        TransactionDescription transaction = new TransactionDescription(
                array.get(position).getName(),
                array.get(position).getDescription(),
                array.get(position).getTime(),
                array.get(position).getAmount(),
                array.get(position).getFee(),
                array.get(position).getImage(),
                array.get(position).getIsChecked()
        );

        holder.tvName.setText(transaction.getName());
        holder.tvDescription.setText(transaction.getDescription());
        holder.tvTime.setText(transaction.getTime());
        holder.tvAmount.setText(transaction.getAmount());
        holder.tvFee.setText(transaction.getFee());
        holder.iv.setImageResource(transaction.getImage());
        holder.ivIsChecked.setImageResource(transaction.getIsChecked());

        holder.ivIsChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newImageIsChecked = fragment.changeIsChecked(transaction);
                array.get(pos).setIsChecked(newImageIsChecked);
                holder.ivIsChecked.setImageResource(newImageIsChecked);

            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

}

