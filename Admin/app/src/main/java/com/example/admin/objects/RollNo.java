package com.example.admin.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RollNo {
    @SerializedName("rollNo")
    @Expose
    private String rollNo;

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public RollNo(String rollNo) {
        this.rollNo = rollNo;
    }
}
