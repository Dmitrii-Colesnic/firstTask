package com.example.firsttask.ui.transactions;

import android.view.View;

import com.example.firsttask.ui.transactions.adapter.ItemAdapter;
import com.example.firsttask.ui.transactions.adapter.entities.ParentTransactionDescription;
import com.example.firsttask.ui.transactions.adapter.entities.TransactionDescription;
import com.example.firsttask.ui.transactions.view.entities.InvoiceDetails;

import java.util.ArrayList;

public interface Transactions {

    interface View {

        // DataActivity
        void navigateToAuthenticateActivity();

        // InvoiceDetailsActivity
        void setProgressDialog();
        void dismissProgressDialog();
        void setDetailsData(InvoiceDetails invoiceDetails);
        void showToast(String toastText);

        void showNoInternetDialog();
    }

    interface Fragment {

        void setUpListOfDataIntoParentRecyclerView(ArrayList<ParentTransactionDescription> array);
        void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array);
        void changeIsChecked(TransactionDescription transaction, int position);
        void getTransactionDetails(String transactionKey);
        void getTransactionLink(String transactionKey);
        void linkDialog(String title ,String message);

        void setProgressDialog();
        void dismissProgressDialog();

        void showToast(String toastText);

        void navigateToActivity(Class<?> cls);

        void showNoInternetDialog();
    }

    interface Presenter {

        void getData();
        void changeIsChecked(TransactionDescription transaction, ItemAdapter adapter, int position);
        void getDataFromDB();
        void getTransactionsHistory(String startDate, String endDate, String pStatus, String pSearch);
        void getTransactionDetails(String transactionKey);
        void getTransactionLink(String transactionKey);
        void getPDF();

        boolean datesExist();
        String getStartDate();
        String getEndDate();
        void setStartDate(String startDate);
        void setEndDate(String endDate);
        void deleteStartDate();
        void deleteEndDate();
    }

}
