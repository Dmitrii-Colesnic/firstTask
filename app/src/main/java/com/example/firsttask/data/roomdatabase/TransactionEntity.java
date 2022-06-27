package com.example.firsttask.data.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "test")
public class TransactionEntity {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String description;


    public TransactionEntity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
