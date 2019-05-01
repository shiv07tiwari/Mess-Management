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
    private Integer fromDate;
    @SerializedName("toDate")
    @Expose
    private Integer toDate;
    private Integer amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFromDate() {
        return fromDate;
    }

    public void setFromDate(Integer fromDate) {
        this.fromDate = fromDate;
    }

    public Integer getToDate() {
        return toDate;
    }

    public void setToDate(Integer toDate) {
        this.toDate = toDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Rebate(String name, Integer fromDate, Integer toDate) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.amount = (toDate-fromDate)*600;
    }
}
