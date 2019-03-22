package com.example.lamchard.smartsms.Models;

import java.io.Serializable;

public class Discussion implements Serializable {

    private String phoneNumber;
    private String message;
    private String time;
    private String date;
    private String type;


    public Discussion(String number, String message, String time) {
        this.phoneNumber = number;
        this.message = message;
        this.time = time;
    }

    public Discussion(String number, String message, String time, String date, String type) {
        this.phoneNumber = number;
        this.message = message;
        this.time= time;
        this.type = type;
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
