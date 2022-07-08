package com.example.firsttask.ui.transactions.view;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firsttask.App;
import com.example.firsttask.R;
import com.example.firsttask.databinding.FragmentHistoryBinding;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.adapter.ParentItemAdapter;
import com.example.firsttask.ui.transactions.adapter.entities.ParentTransactionDescription;
import com.example.firsttask.ui.transactions.adapter.entities.TransactionDescription;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HistoryFragment extends Fragment implements Transactions.Fragment {

    private FragmentHistoryBinding binding;

    private final TransactionsPresenter presenter = new TransactionsPresenter(HistoryFragment.this);

    private Dialog loadingDialog;

    private ParentItemAdapter parentItemAdapter;

    private String pSearch = "";

    private Integer pStatus = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        presenter.getTransactionsHistory("", "", pStatus.toString(), pSearch);

        initializingFilterButtons();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tvSearch, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
//                        || event.getAction() == KeyEvent.ACTION_DOWN
                ) {
                    pSearch = tvSearch.getText().toString();
                    presenter.getTransactionsHistory("", "", pStatus.toString(), pSearch);
                    return true;
                }

                return false;
            }
        });



        binding.etSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    private Timer timer = new Timer();
                    private final long DELAY = 500;

                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        pSearch = binding.etSearch.getText().toString();
                                        presenter.getTransactionsHistory("", "", pStatus.toString(), pSearch);
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );

    }

    private void initializingFilterButtons() {

        binding.btnFilterAll.setOnClickListener(v -> {
            pStatus = 0;

            binding.btnFilterAll.setBackgroundResource(R.drawable.button_filter_background_clicked);
            binding.btnFilterAll.setTextColor(Color.WHITE);

            binding.btnFilterPending.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterPending.setTextColor(Color.GRAY);

            binding.btnFilterPaid.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterPaid.setTextColor(Color.GRAY);

            presenter.getTransactionsHistory("", "", pStatus.toString(), pSearch);
        });

        binding.btnFilterPending.setOnClickListener(v -> {
            pStatus = 2;

            binding.btnFilterPending.setBackgroundResource(R.drawable.button_filter_background_clicked);
            binding.btnFilterPending.setTextColor(Color.WHITE);

            binding.btnFilterAll.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterAll.setTextColor(Color.GRAY);

            binding.btnFilterPaid.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterPaid.setTextColor(Color.GRAY);

            presenter.getTransactionsHistory("", "", pStatus.toString(), pSearch);
        });

        binding.btnFilterPaid.setOnClickListener(v -> {
            pStatus = 1;

            binding.btnFilterPaid.setBackgroundResource(R.drawable.button_filter_background_clicked);
            binding.btnFilterPaid.setTextColor(Color.WHITE);

            binding.btnFilterPending.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterPending.setTextColor(Color.GRAY);

            binding.btnFilterAll.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterAll.setTextColor(Color.GRAY);

            presenter.getTransactionsHistory("", "", pStatus.toString(), pSearch);
        });

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
    public void showNoInternetDialog() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.no_internet_dialog);
        dialog.setCancelable(false);

        dialog.findViewById(R.id.btn_try_again).setOnClickListener(v -> {
            if(App.isNetworkAvailable()){
                dialog.dismiss();
                presenter.getTransactionsHistory("", "", pStatus.toString(), pSearch);
            }
        });

        dialog.findViewById(R.id.btn_exit).setOnClickListener(v -> {
            getActivity().finish();
            System.exit(0);
        });

        dialog.show();

    }

    @Override
    public void getTransactionDetails(String transactionKey) {
        Intent intent = new Intent(getActivity(), InvoiceDetailsActivity.class);
        intent.putExtra("transactionKey", transactionKey);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void getTransactionLink(String transactionKey) {
        presenter.getTransactionLink(transactionKey);
    }

    @Override
    public void linkDialog(String title, String message) {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.copy_link__dialog);

        TextView tvTitle = dialog.findViewById(R.id.tv_copy_link_title);
        tvTitle.setText(title);

        TextView tvMessage = dialog.findViewById(R.id.tv_copy_link_message);
        tvMessage.setText(message);

        if(tvTitle.getText() == App.getContext().getResources().getString(R.string.copy_link)) {

            dialog.findViewById(R.id.btn_copy_link).setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("transaction link", message);
                clipboard.setPrimaryClip(clip);

                dialog.dismiss();
                showToast(App.getContext().getResources().getString(R.string.copied));
            });

        } else {
            Button cancel = dialog.findViewById(R.id.btn_copy_link);
            cancel.setText("cancel");
            cancel.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    @Override
    public void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array) {
    }

    @Override
    public void changeIsChecked(TransactionDescription transaction, int position) {
    }

    @Override
    public void showToast(String toastText) {Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();}

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