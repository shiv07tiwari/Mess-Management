package com.example.admin.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feedback {

    @SerializedName("rollNo")
    @Expose
    private String rollNo;
    @SerializedName("mess")
    @Expose
    private String mess;
    @SerializedName("currDate")
    @Expose
    private String currentDate;
    @SerializedName("timeSlot")
    @Expose
    private String timeSlot;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("complaint")
    @Expose
    private String complaint;

    public Feedback(String rollNo, String mess, String currentDate, String timeSlot, Integer rating, String complaint) {
        this.rollNo = rollNo;
        this.mess = mess;
        this.currentDate = currentDate;
        this.timeSlot = timeSlot;
        this.rating = rating;
        this.complaint = complaint;
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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
