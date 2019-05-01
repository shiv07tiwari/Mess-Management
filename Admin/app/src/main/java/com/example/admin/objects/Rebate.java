package com.example.admin.objects;

import android.util.Log;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rebate {

    @SerializedName("rollNo")
    @Expose
    private String name;
    @SerializedName("fromDate")
    @Expose
    private Long fromDate;
    @SerializedName("toDate")
    @Expose
    private Long toDate;
    private long amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFromDate() {
        return fromDate;
    }

    //public void setFromDate(Integer fromDate) {
       // this.fromDate = fromDate;
    //}

    public Long getToDate() {
        return toDate;
    }


    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Rebate(String name, Long fromDate, Long toDate) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.amount = (toDate-fromDate)*600;
    }
}
