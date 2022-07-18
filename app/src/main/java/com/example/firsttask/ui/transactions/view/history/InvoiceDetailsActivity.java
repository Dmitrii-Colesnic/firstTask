package com.example.firsttask.ui.transactions.view.history;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.firsttask.App;
import com.example.firsttask.R;
import com.example.firsttask.databinding.ActivityInvoiceDetailsBinding;
import com.example.firsttask.ui.authentication.view.AuthenticationActivity;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.TransactionsPresenter;
import com.example.firsttask.ui.transactions.view.DataActivity;
import com.example.firsttask.ui.transactions.view.entities.InvoiceDetails;

public class InvoiceDetailsActivity extends AppCompatActivity implements Transactions.View {

    private ActivityInvoiceDetailsBinding binding;

    private TransactionsPresenter presenter = new TransactionsPresenter(this);

    private DrawerLayout drawerLayout;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Bundle extras = getIntent().getExtras();
        if(extras != null){

            String transactionKey = extras.getString("transactionKey");
            presenter.getTransactionDetails(transactionKey);

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Something went wrong");
            builder.setMessage("Something went wrong, try again");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                }
            });
            builder.setCancelable(false);
            builder.show();

        }

        binding.ivSavePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getPDF();
            }
        });

    }

    @Override
    public void navigateToAuthenticateActivity() {
        startActivity(new Intent(InvoiceDetailsActivity.this, AuthenticationActivity.class));
        finish();
    }

    @Override
    public void showToast(String toastText) {Toast.makeText(InvoiceDetailsActivity.this, toastText, Toast.LENGTH_SHORT).show();}

    @Override
    public void setProgressDialog() {
        loadingDialog = new Dialog(InvoiceDetailsActivity.this);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void setDetailsData(InvoiceDetails invoiceDetails){
        binding.tvOrderAmount.setText(invoiceDetails.getAmount());
        binding.tvInvoiceNumber.setText(invoiceDetails.getOrderNumber());

        if(invoiceDetails.getCategories().equals("Expired")){
            binding.flCategories.setBackgroundResource(R.drawable.categories_background_gray);
        } else if (invoiceDetails.getCategories().equals("Paid")){
            binding.flCategories.setBackgroundResource(R.drawable.categories_background_green);
        } else {
            binding.flCategories.setBackgroundResource(R.drawable.categories_background_red);
        }
        binding.tvCategories.setText(invoiceDetails.getCategories());
        binding.tvSentTo.setText(invoiceDetails.getSentTo());
        binding.tvCreated.setText(invoiceDetails.getCreated());
        binding.tvExpires.setText(invoiceDetails.getExpired());
        binding.tvPaymentDate.setText(invoiceDetails.getPaymentDate());
        binding.tvCommission.setText(invoiceDetails.getCommission());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InvoiceDetailsActivity.this, DataActivity.class);
        intent.putExtra("HistoryFragment", "HistoryFragment");
        startActivity(intent);
        finish();
    }

    @Override
    public void showNoInternetDialog() {

        Dialog dialog = new Dialog(InvoiceDetailsActivity.this);
        dialog.setContentView(R.layout.no_internet_dialog);
        dialog.setCancelable(false);

        dialog.findViewById(R.id.btn_try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.isNetworkAvailable()){
                    dialog.dismiss();

                    Bundle extras = getIntent().getExtras();
                    if(extras != null){
                        String transactionKey = extras.getString("transactionKey");
                        presenter.getTransactionDetails(transactionKey);
                    } else {
                        Intent intent = new Intent(InvoiceDetailsActivity.this, DataActivity.class);
                        intent.putExtra("HistoryFragment", "HistoryFragment");
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        dialog.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        dialog.show();

    }

}