package com.example.firsttask.data.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import kotlinx.coroutines.flow.Flow;

@Dao
public interface TransactionDao {

    @Insert
    void insert(TransactionEntity transaction);


    @Delete
    void delete(TransactionEntity transaction);

    @Query("select * from `test` ")
    Single<List<TransactionEntity>> fetchAll();

    @Query("select exists(select * from `test` where description=:description)")
    Single<Boolean> ifDescriptionExist(String description);

//    @Query("DELETE FROM `test` WHERE description=:description")

//    void deleteByDescription(String description);
//    @Query("select * from `transactions` where imageIsChecked=:isChecked")
//    List<TransactionEntity> fetchAllDataByIsChecked(int isChecked);

}
