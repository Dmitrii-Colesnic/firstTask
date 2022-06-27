package com.example.firsttask.ui.transactions;

import com.example.firsttask.ui.transactions.preseter.ItemAdapter;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;

import java.util.ArrayList;

public interface Transactions {

    interface View {
        
        void navigateToAuthenticateActivity();

    }

    interface Fragment {

        void setUpListOfDataIntoRecyclerView(ArrayList<TransactionDescription> array);
        void changeIsChecked(TransactionDescription transaction, int position);
        void showToast(String toastText);
        void setProgressDialog();
        void dismissProgressDialog();

    }

    interface Presenter {

        void getData();
        void changeIsChecked(TransactionDescription transaction, ItemAdapter adapter, int position);
        void getDataFromDB();

    }

}
