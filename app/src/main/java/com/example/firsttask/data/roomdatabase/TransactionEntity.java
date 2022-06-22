package com.example.firsttask.data.roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class TransactionEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private String time;
    private String amount;
    private String fee;
    private int image;
    private int imageIsChecked;

    public TransactionEntity(int id, String name, String description, String time, String amount, String fee, int image, int imageIsChecked) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
        this.amount = amount;
        this.fee = fee;
        this.image = image;
        this.imageIsChecked = imageIsChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImageIsChecked() {
        return imageIsChecked;
    }

    public void setImageIsChecked(int imageIsChecked) {
        this.imageIsChecked = imageIsChecked;
    }
}
