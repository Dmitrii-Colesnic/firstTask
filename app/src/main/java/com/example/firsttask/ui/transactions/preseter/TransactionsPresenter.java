package com.example.firsttask.ui.transactions.preseter;

import android.os.AsyncTask;
import android.util.Log;

import com.example.firsttask.R;
import com.example.firsttask.data.App;
import com.example.firsttask.data.retrofit.entities.ReturnObject;
import com.example.firsttask.data.retrofit.entities.UserResponse;
import com.example.firsttask.data.roomdatabase.TransactionDatabase;
import com.example.firsttask.data.roomdatabase.TransactionEntity;
import com.example.firsttask.ui.authentication.model.sharedpref.SharedPrefTokenStorage;
import com.example.firsttask.ui.transactions.Transactions;
import com.example.firsttask.ui.transactions.preseter.entities.TransactionDescription;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

                            transaction.setName(item.getName());
                            transaction.setDescription(item.getDescription());
                            transaction.setFee(item.getFee().toString());
                            transaction.setIsChecked(R.drawable.ic_heart_is_not_checked);

                            //setIsChecked
                            //find if it exist in db
//                            db.getTransactionDao().ifRowIsExistByDescriptionField(transaction.getDescription())
//                                    .subscribeOn(Schedulers.newThread())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(new SingleObserver<Boolean>() {
//                                        @Override
//                                        public void onSubscribe(Disposable d) {
//                                            Log.e("Existing at db", "onSubscribe");
//                                        }
//
//                                        @Override
//                                        public void onSuccess(Boolean aBoolean) {
//                                            Log.e("Existing at db", "onSuccess");
//                                            if (aBoolean) {
//                                                Log.e("Existing at db", "exist");
//                                                transaction.setIsChecked(R.drawable.ic_heart_is_checked);
//                                            } else {
//                                                Log.e("Existing at db", "don't exist");
//                                                transaction.setIsChecked(R.drawable.ic_heart_is_not_checked);
//                                            }
//
//
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//                                            Log.e("Existing at db", "onError");
//
//                                        }
//                                    });

                            //setImage
                            if (item.getType() == 2) {
                                transaction.setImage(R.drawable.ic_money_type2);
                            } else {
                                transaction.setImage(R.drawable.ic_love_type3);
                            }

                            //setData
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                Date d = sdf.parse(item.getDateTransaction());
                                transaction.setTime(output.format(d));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            //setAmount
                            Float amount = item.getAmount().floatValue() / 100;
                            transaction.setAmount(amount.toString());

                            transactions.add(transaction);
                        }
                        //here I have full transactions array from internet

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
                                            for (TransactionEntity itemEntity : transactionEntities) {
                                                if (itemTransaction.getDescription().equals(itemEntity.getDescription())) {
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
    public void getDataFromDB() {

        db.getTransactionDao().fetchAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<TransactionEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<TransactionEntity> transactionEntities) {
                        Log.e("fetch all data from db", "onSuccess");

                        ArrayList<TransactionDescription> transactions = new ArrayList();
                        for (TransactionEntity entity : transactionEntities){
                            transactions.add(new TransactionDescription(
                                    entity.getName(),
                                    entity.getDescription(),
                                    entity.getTime(),
                                    entity.getAmount(),
                                    entity.getFee(),
                                    entity.getImage(),
                                    entity.getImageIsChecked()
                            ));
                        }

                        fragment.setUpListOfDataIntoRecyclerView(transactions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("fetch all data from db", "onError");
                    }
                });

    }

    @Override
    public int changeIsChecked(TransactionDescription transaction) {
        //todo: logic for replace isChecked image, and for adding item to database

        TransactionEntity transactionEntity = new TransactionEntity(
                transaction.getName(),
                transaction.getDescription(),
                transaction.getTime(),
                transaction.getAmount(),
                transaction.getFee(),
                transaction.getImage(),
                transaction.getIsChecked()
        );

        if (transaction.getIsChecked() == R.drawable.ic_heart_is_not_checked) {
            //TODO:
            // if entity isn't in db,
            //      insert into db
            //      change isChecked to ic_heart_is_checked
            // else
            //      change isChecked to ic_heart_is_checked
            transaction.setIsChecked(R.drawable.ic_heart_is_checked);
            transactionEntity.setImageIsChecked(R.drawable.ic_heart_is_checked);
            new InsertTask(this, transactionEntity, fragment).execute();
        } else {
            transaction.setIsChecked(R.drawable.ic_heart_is_not_checked);
            transactionEntity.setImageIsChecked(R.drawable.ic_heart_is_not_checked);
            new DeleteTask(this, transactionEntity, fragment).execute();
        }

        return transaction.getIsChecked();
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
            objectReference.get().db.getTransactionDao().deleteByDescription(entity.getDescription());
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
        sharedPrefTokenStorage.delete();
        view.navigateToAuthenticateActivity();
    }

}

