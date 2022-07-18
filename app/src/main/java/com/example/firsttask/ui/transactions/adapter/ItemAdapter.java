package com.example.firsttask.ui.transactions.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.firsttask.R;
import com.example.firsttask.databinding.ItemDescriptionBinding;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.adapter.entities.TransactionDescription;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerSwipeAdapter<ItemAdapter.ViewHolder> {

    private ArrayList<TransactionDescription> array;
    private Transactions.Fragment fragment;

//    SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public void setChecked(int position, int isChecked) {
        array.get(position).setIsChecked(isChecked);
        notifyItemChanged(position);
    }

    public ItemAdapter(ArrayList<TransactionDescription> dataSet,
                       Transactions.Fragment fragment) {
        array = dataSet;
        this.fragment = fragment;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
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

        private final SwipeLayout swipe;
        private final FrameLayout flCircleGreen;
        private final FrameLayout flCircleRed;
        private final FrameLayout flCircleGray;

        private final ConstraintLayout clSwipe;

        public ViewHolder(ItemDescriptionBinding binding /*View itemView*/) {
            super(binding.getRoot());

            this.tvName = binding.tvName;
            this.tvDescription = binding.tvDescription;
            this.tvTime = binding.tvTime;
            this.tvAmount = binding.tvAmount;
            this.tvFee = binding.tvFee;
            this.iv = binding.iv;
            this.ivIsChecked = binding.ivIsChecked;
            this.constraintLayout = binding.clMainLayout;
            this.swipe = binding.swipe;
            this.flCircleGreen = binding.flCircleGreen;
            this.flCircleRed = binding.flCircleRed;
            this.flCircleGray = binding.flCircleGray;

            this.clSwipe = binding.clSwipe;
//
//            this.tvName = itemView.findViewById(R.id.tv_name);
//            this.tvDescription = itemView.findViewById(R.id.tv_description);
//            this.tvTime = itemView.findViewById(R.id.tv_time);
//            this.tvAmount = itemView.findViewById(R.id.tv_amount);
//            this.tvFee = itemView.findViewById(R.id.tv_fee);
//            this.iv = itemView.findViewById(R.id.iv);
//            this.ivIsChecked = itemView.findViewById(R.id.iv_isChecked);
//            this.constraintLayout = itemView.findViewById(R.id.cl_main_layout);
//            this.swipeLayout = itemView.findViewById(R.id.swipe);

        }
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

//    public ItemAdapter(ArrayList<TransactionDescription> array) {
//        this.array = array;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDescriptionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_description, parent, false);
//        return new ViewHolder(view);
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

        holder.swipe.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipe.addDrag(SwipeLayout.DragEdge.Right, holder.swipe.findViewById(R.id.cl_swipe));

        if(!transaction.getName().substring(0,2).equals("In")){
            holder.flCircleGreen.setVisibility(View.GONE);
            holder.clSwipe.setMaxWidth(450);
        }

        int pos = position;

        String name;
        if (transaction.getName().length() > 16) {
            name = transaction.getName().substring(0, 16) + "...";
        } else {
            name = transaction.getName();
        }
        holder.tvName.setText(name);

        String description;
        if (transaction.getDescription() != null) {

            if (transaction.getDescription().length() > 16) {
                description = transaction.getDescription().substring(0, 16) + "...";
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
        if (transaction.getIsChecked() == 0) {
            holder.ivIsChecked.setVisibility(View.GONE);
        } else {
            holder.ivIsChecked.setVisibility(View.VISIBLE);
            holder.ivIsChecked.setImageResource(transaction.getIsChecked());
        }

        holder.ivIsChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.changeIsChecked(transaction, pos);
            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.swipe.close();
                fragment.getTransactionDetails(transaction.getTransactionKey());
            }
        });

        holder.flCircleGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.getTransactionDetails(transaction.getTransactionKey());
            }
        });

        holder.flCircleGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.swipe.close();
                fragment.getTransactionLink(transaction.getTransactionKey());
            }
        });

        mItemManger.bindView(holder.swipe, position);
//        mItemManger.setMode(Attributes.Mode.Single);
//        holder.swipe.addSwipeListener(new SwipeLayout.SwipeListener() {
//            @Override
//            public void onStartOpen(SwipeLayout layout) {
//                mItemManger.closeAllExcept(layout);
//            }
//
//            @Override
//            public void onOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onStartClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//
//            }
//
//            @Override
//            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

}

