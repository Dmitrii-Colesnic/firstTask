package com.example.firsttask.ui.transactions.preseter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;
import com.example.firsttask.databinding.ItemDescriptionBinding;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<TransactionDescription> array;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvDescription;
        private final TextView tvTime;
        private final TextView tvAmount;
        private final TextView tvFee;
        private final ImageView iv;


        public ViewHolder(ItemDescriptionBinding binding) {
            super(binding.getRoot());

            this.tvName = binding.tvName;
            this.tvDescription = binding.tvDescription;
            this.tvTime = binding.tvTime;
            this.tvAmount = binding.tvAmount;
            this.tvFee = binding.tvFee;
            this.iv = binding.iv;
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

    public ItemAdapter(ArrayList<TransactionDescription> dataSet) {
        array = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDescriptionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(array.get(position).getName());
        holder.tvDescription.setText(array.get(position).getDescription());
        holder.tvTime.setText(array.get(position).getTime());
        holder.tvAmount.setText(array.get(position).getAmount());
        holder.tvFee.setText(array.get(position).getFee());
        holder.iv.setImageResource(array.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return array.size();
    }
}

