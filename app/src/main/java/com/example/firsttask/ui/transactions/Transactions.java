package com.example.firsttask.ui.transactions;

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
    }

    interface Fragment {

        void setUpListOfDataIntoParentRecyclerView(ArrayList<ParentTransactionDescription> array);
        void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array);
        void changeIsChecked(TransactionDescription transaction, int position);
        void getTransactionDetails(String transactionKey);

        void setProgressDialog();
        void dismissProgressDialog();

        void showToast(String toastText);

        void navigateToActivity(Class<?> cls);

    }

    interface Presenter {

        void getData();
        void changeIsChecked(TransactionDescription transaction, ItemAdapter adapter, int position);
        void getDataFromDB();
        void getTransactionsHistory();
        void getTransactionDetails(String transactionKey);

    }

}
