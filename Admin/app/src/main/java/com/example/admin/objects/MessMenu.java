package com.example.admin.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessMenu implements Serializable {

    @SerializedName("timeSlot")
    @Expose
    private Integer timeSlot;

    private String fixedMenu;


    private String mondayMenu;

    private String tuesdayMenu;

    private String wednesdayMenu;

    private String thursdayMenu;

    private String fridayMenu;

    private String saturdayMenu;

    private String sundayMenu;

    public MessMenu(Integer timeSlot, String fixedMenu, String mondayMenu, String tuesdayMenu, String wednesdayMenu, String thursdayMenu, String fridayMenu, String saturdayMenu, String sundayMenu) {
        this.timeSlot = timeSlot;
        this.fixedMenu = fixedMenu;
        this.mondayMenu = mondayMenu;
        this.tuesdayMenu = tuesdayMenu;
        this.wednesdayMenu = wednesdayMenu;
        this.thursdayMenu = thursdayMenu;
        this.fridayMenu = fridayMenu;
        this.saturdayMenu = saturdayMenu;
        this.sundayMenu = sundayMenu;
    }

    public Integer getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Integer timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getFixedMenu() {
        return fixedMenu;
    }

    public void setFixedMenu(String fixedMenu) {
        this.fixedMenu = fixedMenu;
    }

    public String getMondayMenu() {
        return mondayMenu;
    }

    public void setMondayMenu(String mondayMenu) {
        this.mondayMenu = mondayMenu;
    }

    public String getTuesdayMenu() {
        return tuesdayMenu;
    }

    public void setTuesdayMenu(String tuesdayMenu) {
        this.tuesdayMenu = tuesdayMenu;
    }

    public String getWednesdayMenu() {
        return wednesdayMenu;
    }

    public void setWednesdayMenu(String wednesdayMenu) {
        this.wednesdayMenu = wednesdayMenu;
    }

    public String getThursdayMenu() {
        return thursdayMenu;
    }

    public void setThursdayMenu(String thursdayMenu) {
        this.thursdayMenu = thursdayMenu;
    }

    public String getFridayMenu() {
        return fridayMenu;
    }

    public void setFridayMenu(String fridayMenu) {
        this.fridayMenu = fridayMenu;
    }

    public String getSaturdayMenu() {
        return saturdayMenu;
    }

    public void setSaturdayMenu(String saturdayMenu) {
        this.saturdayMenu = saturdayMenu;
    }

    public String getSundayMenu() {
        return sundayMenu;
    }

    public void setSundayMenu(String sundayMenu) {
        this.sundayMenu = sundayMenu;
    }
}
