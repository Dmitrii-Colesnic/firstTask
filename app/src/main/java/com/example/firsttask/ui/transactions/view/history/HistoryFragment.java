package com.example.firsttask.ui.transactions.view.history;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firsttask.App;
import com.example.firsttask.R;
import com.example.firsttask.databinding.FragmentHistoryBinding;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.adapter.ParentItemAdapter;
import com.example.firsttask.ui.transactions.adapter.entities.ParentTransactionDescription;
import com.example.firsttask.ui.transactions.adapter.entities.TransactionDescription;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HistoryFragment extends Fragment implements Transactions.Fragment {

    private FragmentHistoryBinding binding;

    private final TransactionsPresenter presenter = new TransactionsPresenter(HistoryFragment.this);


    ImageView ivCalendar;

    private Dialog loadingDialog;

    private Dialog calendarDialog;

    private ParentItemAdapter parentItemAdapter;

    private String pSearch = "";

    private Integer pStatus = 0;

    private String pStartDate = "";

    private String pEndDate = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        ivCalendar = getActivity().findViewById(R.id.iv_calendar);
        ivCalendar.setVisibility(View.VISIBLE);

        setCalendar();

        if (presenter.datesExist()) {
            pStartDate = presenter.getStartDate();
            pEndDate = presenter.getEndDate();
            binding.rvParentTransaction.setVisibility(View.VISIBLE);
            binding.ivCalendar50.setVisibility(View.GONE);
            presenter.getTransactionsHistory(pStartDate, pEndDate, pStatus.toString(), pSearch);
        } else {
            binding.rvParentTransaction.setVisibility(View.GONE);
            binding.ivCalendar50.setVisibility(View.VISIBLE);
        }

//        binding.ivCalendar50.setOnClickListener(v -> {
//            setCalendarDialog();
//        });

        initializingFilterButtons();

        return binding.getRoot();
    }

    private void setCalendar() {

        final FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder
                .dateRangePicker();
        builder.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                .build();

        builder.setTheme(R.style.MaterialCalendarTheme);

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        materialDatePicker.setCancelable(false);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(fm, "Tag_picker");

                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
//                Get the selected DATE RANGE
                    Pair selectedDates = (Pair) materialDatePicker.getSelection();
//              then obtain the startDate & endDate from the range
                    final Pair<Date, Date> rangeDate = new Pair<>(new Date((Long) selectedDates.first), new Date((Long) selectedDates.second));
//              assigned variables
                    Date startDate = rangeDate.first;
                    Date endDate = rangeDate.second;
//              Format the dates in ur desired display mode
                    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
//              Display it by setText
                    Log.e("date 1", simpleFormat.format(startDate));
                    Log.e("date 2", simpleFormat.format(endDate));

                    pStartDate = simpleFormat.format(startDate);
                    pEndDate = simpleFormat.format(endDate);

                    presenter.setStartDate(pStartDate);
                    presenter.setEndDate(pEndDate);

                    binding.rvParentTransaction.setVisibility(View.VISIBLE);
                    binding.ivCalendar50.setVisibility(View.GONE);
                    presenter.getTransactionsHistory(pStartDate, pEndDate, pStatus.toString(), pSearch);

                });
            }
        };

        ivCalendar.setOnClickListener(listener);
        binding.ivCalendar50.setOnClickListener(listener);

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
                    presenter.getTransactionsHistory(pStartDate, pEndDate, pStatus.toString(), pSearch);
                    return true;
                }

                return false;
            }
        });


        binding.etSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

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
                                        presenter.getTransactionsHistory(pStartDate, pEndDate, pStatus.toString(), pSearch);
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

            presenter.getTransactionsHistory(pStartDate, pEndDate, pStatus.toString(), pSearch);
        });

        binding.btnFilterPending.setOnClickListener(v -> {
            pStatus = 2;

            binding.btnFilterPending.setBackgroundResource(R.drawable.button_filter_background_clicked);
            binding.btnFilterPending.setTextColor(Color.WHITE);

            binding.btnFilterAll.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterAll.setTextColor(Color.GRAY);

            binding.btnFilterPaid.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterPaid.setTextColor(Color.GRAY);

            presenter.getTransactionsHistory(pStartDate, pEndDate, pStatus.toString(), pSearch);
        });

        binding.btnFilterPaid.setOnClickListener(v -> {
            pStatus = 1;

            binding.btnFilterPaid.setBackgroundResource(R.drawable.button_filter_background_clicked);
            binding.btnFilterPaid.setTextColor(Color.WHITE);

            binding.btnFilterPending.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterPending.setTextColor(Color.GRAY);

            binding.btnFilterAll.setBackgroundResource(R.drawable.button_filter_background);
            binding.btnFilterAll.setTextColor(Color.GRAY);

            presenter.getTransactionsHistory(pStartDate, pEndDate, pStatus.toString(), pSearch);
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
            if (App.isNetworkAvailable()) {
                dialog.dismiss();
                presenter.getTransactionsHistory(pStartDate, pEndDate, pStatus.toString(), pSearch);
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

        if (tvTitle.getText() == App.getContext().getResources().getString(R.string.copy_link)) {

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

//    private void setCalendarDialog(){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.calendar_dialog, null);
//        builder.setView(dialogView);
//
//        DateRangeCalendarView calendar = new DateRangeCalendarView(getActivity());
//        calendar.setBackgroundResource(R.drawable.calendar_background);
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//
////        final FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
//
////        new SlyCalendarDialog()
////                .setSingle(false)
////                .setCallback(new CallbackTest(getActivity()))
////                .show(fm, "TAG_SLYCALENDAR");
//
//    }
}