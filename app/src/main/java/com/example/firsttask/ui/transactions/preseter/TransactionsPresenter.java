package com.example.firsttask.ui.transactions.preseter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.firsttask.R;
import com.example.firsttask.App;
import com.example.firsttask.data.retrofit.entities.ReturnObject;
import com.example.firsttask.data.retrofit.entities.details.DetailsResponse;
import com.example.firsttask.data.retrofit.entities.history.HistoryResponse;
import com.example.firsttask.data.retrofit.entities.history.HistoryReturnObject;
import com.example.firsttask.data.retrofit.entities.recent.UserResponse;
import com.example.firsttask.data.roomdatabase.TransactionDatabase;
import com.example.firsttask.data.roomdatabase.TransactionEntity;
import com.example.firsttask.ui.MainActivity;
import com.example.firsttask.ui.authentication.view.AuthenticationActivity;
import com.example.firsttask.ui.transactions.adapter.ItemAdapter;
import com.example.firsttask.ui.authentication.model.sharedpref.SharedPrefTokenStorage;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.adapter.entities.ParentTransactionDescription;
import com.example.firsttask.ui.transactions.adapter.entities.TransactionDescription;
import com.example.firsttask.ui.transactions.view.InvoiceDetailsActivity;
import com.example.firsttask.ui.transactions.view.entities.InvoiceDetails;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsPresenter implements Transactions.Presenter {

    private Transactions.View view;
    private Transactions.Fragment fragment;

    private final TransactionDatabase db = TransactionDatabase.getInstance(App.getContext());

    App app = new App();

    SharedPrefTokenStorage sharedPrefTokenStorage = new SharedPrefTokenStorage(App.getContext());

    public TransactionsPresenter(Transactions.View view) {
        this.view = view;
    }

    public TransactionsPresenter(Transactions.Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void getData() {

        ArrayList<TransactionDescription> transactions = new ArrayList();

        fragment.setProgressDialog();
        app.getUserService().getTransactionRecent()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("getData", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(UserResponse userResponse) {
                        Log.e("getData", "stars onSuccess");

                        for (ReturnObject item : userResponse.getReturnObject()) {

                            TransactionDescription transaction = new TransactionDescription();

                            transaction.setTransactionKey(item.getTransactionKey());
                            transaction.setName(item.getName());
                            transaction.setDescription(item.getDescription());
                            transaction.setIsChecked(R.drawable.ic_heart_is_not_checked);

                            //setImage
                            if (item.getType() == 2) {
                                transaction.setImage(R.drawable.ic_money_type2);
                            } else {
                                transaction.setImage(R.drawable.ic_love_type3);
                            }

                            //setData
                            try {
                                String input = item.getDateTransaction();
                                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                Date date = parser.parse(input);
                                SimpleDateFormat formatter = new SimpleDateFormat("MMM d,yyyy, HH:mm");
                                String formattedDate = formatter.format(date);

                                transaction.setTime(formattedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            //setAmount
                            Float amount = item.getAmount().floatValue() / 100;
                            String parseAmount = currencyFormat(amount);
                            transaction.setAmount(parseAmount);

                            //setFee
                            Float fee = item.getFee().floatValue() / 100;
                            String parseFee = currencyFormat(fee);
                            transaction.setFee(parseFee);

                            transactions.add(transaction);
                        }
                        //here I have full transactions array from internet

                        //setIsChecked
                        db.getTransactionDao().fetchAll()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<List<TransactionEntity>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        Log.e("fetch all data from db", "onSubscribe");
                                    }

                                    @Override
                                    public void onSuccess(List<TransactionEntity> transactionEntities) {
                                        Log.e("fetch all data from db", "onSuccess");
                                        //here i have full array from db
                                        for (TransactionDescription itemTransaction : transactions) {
                                            for (TransactionEntity descriptionItem : transactionEntities) {
                                                if (itemTransaction.getDescription().equals(descriptionItem.getDescription())) {
                                                    //it exist in db
                                                    Log.e("fetch all data from db", "exist");
                                                    itemTransaction.setIsChecked(R.drawable.ic_heart_is_checked);
                                                }
                                            }
                                        }

                                        fragment.dismissProgressDialog();
                                        fragment.setUpListOfDataIntoRecyclerView(transactions);
                                        Log.e("getData", "finish sequential thread");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("fetch all data from db", "onError");
                                    }
                                });

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getData", "onError");
                        Log.e("getData", e.toString());
                    }
                });

    }

    @Override
    public void getTransactionsHistory() {

        String startDate = "2022-02-01";
        String endDate = "2022-11-01";

        app.getUserService().getTransactionsHistory(startDate, endDate)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<HistoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("getTransactionsHistory", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(HistoryResponse historyResponse) {
                        Log.e("getTransactionsHistory", "onSuccess");

                        ArrayList<ParentTransactionDescription> historyTransactions = new ArrayList();
                        for (HistoryReturnObject historyItem : historyResponse.getHistoryReturnObject()) {

                            ParentTransactionDescription parentTransaction = new ParentTransactionDescription();

                            String stringDate = "date can't be define";
                            try {
                                String input = historyItem.getSortedDate();
                                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                Date date = parser.parse(input);
                                SimpleDateFormat formatter = new SimpleDateFormat("MMMM, yyyy");
                                stringDate = formatter.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            parentTransaction.setDate(stringDate);

                            ArrayList<TransactionDescription> transactions = new ArrayList();
                            for (ReturnObject item : historyItem.getReturnObjects()) {

                                TransactionDescription transaction = new TransactionDescription();

                                transaction.setTransactionKey(item.getTransactionKey());
                                transaction.setName(item.getName());
                                transaction.setDescription(item.getDescription());
                                transaction.setIsChecked(0);

                                //setImage
                                if (item.getType() == 2) {
                                    transaction.setImage(R.drawable.ic_money_type2);
                                } else {
                                    transaction.setImage(R.drawable.ic_love_type3);
                                }

                                //setData
                                try {
                                    String input = item.getDateTransaction();
                                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                    Date date = parser.parse(input);
                                    SimpleDateFormat formatter = new SimpleDateFormat("MMM d,yyyy, HH:mm");
                                    String formattedDate = formatter.format(date);

                                    transaction.setTime(formattedDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                //setAmount
                                Float amount = item.getAmount().floatValue() / 100;
                                String parseAmount = currencyFormat(amount);
                                transaction.setAmount(parseAmount);

                                //setFee
                                Float fee = item.getFee().floatValue() / 100;
                                String parseFee = currencyFormat(fee);
                                transaction.setFee(parseFee);

                                transactions.add(transaction);
                            }
                            parentTransaction.setTransactionDescriptions(transactions);

                            historyTransactions.add(parentTransaction);
                        }

                        fragment.setUpListOfDataIntoParentRecyclerView(historyTransactions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getTransactionsHistory", "onError");
                        Log.e("getTransactionsHistory", e.toString());
                    }
                });

    }

    @Override
    public void getTransactionDetails(String transactionKey) {

        view.setProgressDialog();
        app.getUserService().getDetails(transactionKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DetailsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("getTransactionDetails", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(DetailsResponse detailsResponse) {
                        Log.e("getTransactionDetails", "onSuccess");

                        InvoiceDetails invoiceDetails = new InvoiceDetails();

                        Float amount = detailsResponse.getReturnObjectDetails().getAmount().floatValue() / 100;
                        String parseAmount = currencyFormat(amount);
                        invoiceDetails.setAmount(parseAmount);

                        invoiceDetails.setOrderNumber(detailsResponse.getReturnObjectDetails().getOrderNumber());
                        invoiceDetails.setCategories(detailsResponse.getReturnObjectDetails().getCategories());

                        if (detailsResponse.getReturnObjectDetails().getSentTo() != null) {
                            invoiceDetails.setSentTo(detailsResponse.getReturnObjectDetails().getSentTo());
                        } else {
                            invoiceDetails.setSentTo("-");
                        }

                        if (detailsResponse.getReturnObjectDetails().getCreated() != null) {
                            try {
                                String input = detailsResponse.getReturnObjectDetails().getCreated();
                                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                Date date = parser.parse(input);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM HH:mm");
                                String formattedDate = formatter.format(date);

                                invoiceDetails.setCreated(formattedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            invoiceDetails.setCreated("-");
                        }

                        if (detailsResponse.getReturnObjectDetails().getExpired() != null) {
                            try {
                                String input = detailsResponse.getReturnObjectDetails().getExpired();
                                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                Date date = parser.parse(input);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM HH:mm");
                                String formattedDate = formatter.format(date);

                                invoiceDetails.setExpired(formattedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            invoiceDetails.setExpired("-");
                        }

                        if (detailsResponse.getReturnObjectDetails().getPaymentDate() != null) {
                            try {
                                String input = detailsResponse.getReturnObjectDetails().getPaymentDate();
                                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                Date date = parser.parse(input);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM HH:mm");
                                String formattedDate = formatter.format(date);

                                invoiceDetails.setPaymentDate(formattedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            invoiceDetails.setPaymentDate("-");
                        }

                        Float commission = detailsResponse.getReturnObjectDetails().getCommission().floatValue() / 100;
                        String parseCommission = currencyFormat(commission);
                        invoiceDetails.setCommission(parseCommission);

                        new CountDownTimer(2000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {}
                            @Override
                            public void onFinish() {
                                view.dismissProgressDialog();
                                view.setDetailsData(invoiceDetails);
                            }
                        }.start();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getTransactionDetails", "onError");

                    }
                });
    }

    @Override
    public void getDataFromDB() {

        ArrayList<TransactionDescription> transactions = new ArrayList();

        fragment.setProgressDialog();
        app.getUserService().getTransactionRecent()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("getData", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(UserResponse userResponse) {
                        Log.e("getData", "stars onSuccess");

                        for (ReturnObject item : userResponse.getReturnObject()) {

                            TransactionDescription transaction = new TransactionDescription();

                            transaction.setTransactionKey(item.getTransactionKey());
                            transaction.setName(item.getName());
                            transaction.setDescription(item.getDescription());
                            transaction.setIsChecked(R.drawable.ic_heart_is_not_checked);

                            //setImage
                            if (item.getType() == 2) {
                                transaction.setImage(R.drawable.ic_money_type2);
                            } else {
                                transaction.setImage(R.drawable.ic_love_type3);
                            }

                            //setData
                            try {
                                String input = item.getDateTransaction();
                                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                Date date = parser.parse(input);
                                SimpleDateFormat formatter = new SimpleDateFormat("MMM d,yyyy, HH:mm");
                                String formattedDate = formatter.format(date);

                                transaction.setTime(formattedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            //setAmount
                            Float amount = item.getAmount().floatValue() / 100;
                            String parseAmount = currencyFormat(amount);
                            transaction.setAmount(parseAmount);

                            //setFee
                            Float fee = item.getFee().floatValue() / 100;
                            String parseFee = currencyFormat(fee);
                            transaction.setFee(parseFee);

                            transactions.add(transaction);
                        }
                        //here I have full transactions array from internet

                        ArrayList<TransactionDescription> transactionsFavorites = new ArrayList();
                        //setIsChecked
                        db.getTransactionDao().fetchAll()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<List<TransactionEntity>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        Log.e("fetch all data from db", "onSubscribe");
                                    }

                                    @Override
                                    public void onSuccess(List<TransactionEntity> transactionEntities) {
                                        Log.e("fetch all data from db", "onSuccess");
                                        //here i have full array from db
                                        for (TransactionDescription itemTransaction : transactions) {
                                            for (TransactionEntity descriptionItem : transactionEntities) {
                                                if (itemTransaction.getDescription().equals(descriptionItem.getDescription())) {
                                                    //it exist in db
                                                    Log.e("fetch all data from db", "exist");
                                                    itemTransaction.setIsChecked(R.drawable.ic_heart_is_checked);
                                                    transactionsFavorites.add(itemTransaction);
                                                }
                                            }
                                        }

                                        fragment.dismissProgressDialog();
                                        fragment.setUpListOfDataIntoRecyclerView(transactionsFavorites);
                                        Log.e("getData", "finish sequential thread");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("fetch all data from db", "onError");
                                    }
                                });

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getData", "onError");
                        Log.e("getData", e.toString());
                    }
                });

    }

    @Override
    public void changeIsChecked(TransactionDescription transaction, ItemAdapter adapter, int position) {

        db.getTransactionDao().ifDescriptionExist(transaction.getDescription())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("changeIsChecked", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Log.e("changeIsChecked", "onSuccess");

                        //if exist
                        if (aBoolean) {

                            new DeleteTask(TransactionsPresenter.this, new TransactionEntity(transaction.getDescription()), fragment).execute();
                            adapter.setChecked(position, R.drawable.ic_heart_is_not_checked);

                        } else {

                            new InsertTask(TransactionsPresenter.this, new TransactionEntity(transaction.getDescription()), fragment).execute();
                            adapter.setChecked(position, R.drawable.ic_heart_is_checked);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("changeIsChecked", "onError");

                    }
                });
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<TransactionsPresenter> objectReference;

        private TransactionEntity entity;
        private Transactions.Fragment fragment;

        InsertTask(TransactionsPresenter context, TransactionEntity entity, Transactions.Fragment fragment) {
            objectReference = new WeakReference<>(context);
            this.entity = entity;
            this.fragment = fragment;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            objectReference.get().db.getTransactionDao().insert(entity);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                fragment.showToast("added successfully");
            }
        }

    }

    private static class DeleteTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<TransactionsPresenter> objectReference;
        private TransactionEntity entity;
        private Transactions.Fragment fragment;

        DeleteTask(TransactionsPresenter context, TransactionEntity entity, Transactions.Fragment fragment) {
            objectReference = new WeakReference<>(context);
            this.entity = entity;
            this.fragment = fragment;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            objectReference.get().db.getTransactionDao().delete(entity);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                fragment.showToast("deleted successfully");
            }
        }


    }

    public void logout() {
        sharedPrefTokenStorage.deleteToken();
        view.navigateToAuthenticateActivity();
    }

    private static String currencyFormat(double currency) {

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');

        DecimalFormat formatter = new DecimalFormat("###,##0.00", otherSymbols);
        return formatter.format(currency);
    }

}

