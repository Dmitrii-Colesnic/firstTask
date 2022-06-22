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
        
    }

    interface Presenter {

        void getData();

    }

}
