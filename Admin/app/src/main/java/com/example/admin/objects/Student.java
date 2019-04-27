package com.example.admin.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("rollNo")
    @Expose
    private String rollNo;

    @SerializedName("mess")
    @Expose
    private String mess;


    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("verified")
    @Expose
    private String isVerified;

    @SerializedName("IFSCCode")
    @Expose
    private String ifscCode;

    @SerializedName("bankName")
    @Expose
    private String bankName;

    @SerializedName("bankBranch")
    @Expose
    private String bankBranch;

    @SerializedName("accountHolderName")
    @Expose
    private String accountHolder;

    public Student(String name, String rollNo, String mess, String password, String email, String isVerified, String ifscCode, String bankName, String bankBranch, String accountHolder) {
        this.name = name;
        this.rollNo = rollNo;
        this.mess = mess;
        this.password = password;
        this.email = email;
        this.isVerified = isVerified;
        this.ifscCode = ifscCode;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.accountHolder = accountHolder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }
}
