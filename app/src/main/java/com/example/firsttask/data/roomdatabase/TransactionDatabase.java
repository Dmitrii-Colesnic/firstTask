package com.example.firsttask.data.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { TransactionEntity.class }, version = 1)
public abstract class TransactionDatabase extends RoomDatabase {

    public abstract TransactionDao getTransactionDao();

    private static TransactionDatabase transactionDB;

    public static TransactionDatabase getInstance(Context context) {
        if (null == transactionDB) {
            transactionDB = buildDatabaseInstance(context);
        }
        return transactionDB;
    }

    private static TransactionDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                        TransactionDatabase.class,
                        "test1")/*
                .allowMainThreadQueries()*/.build();
    }

}
