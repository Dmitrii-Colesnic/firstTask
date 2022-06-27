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

    @Query("DELETE FROM `test1` WHERE description=:description")
    void deleteByDescription(String description);

    @Query("select * from `test1` ")
    Single<List<TransactionEntity>> fetchAll();

    @Query("select exists(select * from `test1` where description=:description)")
    Single<Boolean> ifRowIsExistByDescriptionField(String description);

//    @Query("select * from `transactions` where imageIsChecked=:isChecked")
//    List<TransactionEntity> fetchAllDataByIsChecked(int isChecked);

}
