package com.example.firsttask.ui.transactions.view.chart;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelWhirligigChart implements Parcelable {
    private String title;
    private String quantityName1;
    private Integer quantity1;
    private String quantityName2;
    private Integer quantity2;
    private String quantityName3;
    private Integer quantity3;
    private String quantityName4;
    private Integer quantity4;
    private String quantityName5;
    private Integer quantity5;
    private String quantityName6;
    private Integer quantity6;
    private String quantityName7;
    private Integer quantity7;

    public ModelWhirligigChart(String title, String quantityName1, Integer quantity1, String quantityName2, Integer quantity2, String quantityName3, Integer quantity3, String quantityName4, Integer quantity4, String quantityName5, Integer quantity5, String quantityName6, Integer quantity6, String quantityName7, Integer quantity7) {
        this.title = title;
        this.quantityName1 = quantityName1;
        this.quantity1 = quantity1;
        this.quantityName2 = quantityName2;
        this.quantity2 = quantity2;
        this.quantityName3 = quantityName3;
        this.quantity3 = quantity3;
        this.quantityName4 = quantityName4;
        this.quantity4 = quantity4;
        this.quantityName5 = quantityName5;
        this.quantity5 = quantity5;
        this.quantityName6 = quantityName6;
        this.quantity6 = quantity6;
        this.quantityName7 = quantityName7;
        this.quantity7 = quantity7;
    }

    protected ModelWhirligigChart(Parcel in) {
        title = in.readString();
        quantityName1 = in.readString();
        if (in.readByte() == 0) {
            quantity1 = null;
        } else {
            quantity1 = in.readInt();
        }
        quantityName2 = in.readString();
        if (in.readByte() == 0) {
            quantity2 = null;
        } else {
            quantity2 = in.readInt();
        }
        quantityName3 = in.readString();
        if (in.readByte() == 0) {
            quantity3 = null;
        } else {
            quantity3 = in.readInt();
        }
        quantityName4 = in.readString();
        if (in.readByte() == 0) {
            quantity4 = null;
        } else {
            quantity4 = in.readInt();
        }
        quantityName5 = in.readString();
        if (in.readByte() == 0) {
            quantity5 = null;
        } else {
            quantity5 = in.readInt();
        }
        quantityName6 = in.readString();
        if (in.readByte() == 0) {
            quantity6 = null;
        } else {
            quantity6 = in.readInt();
        }
        quantityName7 = in.readString();
        if (in.readByte() == 0) {
            quantity7 = null;
        } else {
            quantity7 = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(quantityName1);
        if (quantity1 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity1);
        }
        dest.writeString(quantityName2);
        if (quantity2 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity2);
        }
        dest.writeString(quantityName3);
        if (quantity3 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity3);
        }
        dest.writeString(quantityName4);
        if (quantity4 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity4);
        }
        dest.writeString(quantityName5);
        if (quantity5 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity5);
        }
        dest.writeString(quantityName6);
        if (quantity6 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity6);
        }
        dest.writeString(quantityName7);
        if (quantity7 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity7);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelWhirligigChart> CREATOR = new Creator<ModelWhirligigChart>() {
        @Override
        public ModelWhirligigChart createFromParcel(Parcel in) {
            return new ModelWhirligigChart(in);
        }

        @Override
        public ModelWhirligigChart[] newArray(int size) {
            return new ModelWhirligigChart[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getQuantityName1() {
        return quantityName1;
    }

    public Integer getQuantity1() {
        return quantity1;
    }

    public String getQuantityName2() {
        return quantityName2;
    }

    public Integer getQuantity2() {
        return quantity2;
    }

    public String getQuantityName3() {
        return quantityName3;
    }

    public Integer getQuantity3() {
        return quantity3;
    }

    public String getQuantityName4() {
        return quantityName4;
    }

    public Integer getQuantity4() {
        return quantity4;
    }

    public String getQuantityName5() {
        return quantityName5;
    }

    public Integer getQuantity5() {
        return quantity5;
    }

    public String getQuantityName6() {
        return quantityName6;
    }

    public Integer getQuantity6() {
        return quantity6;
    }

    public String getQuantityName7() {
        return quantityName7;
    }

    public Integer getQuantity7() {
        return quantity7;
    }
}
