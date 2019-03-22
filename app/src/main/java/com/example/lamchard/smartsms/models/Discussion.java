package com.example.lamchard.smartsms.Models;

public class Discussion {

    private String name;
    private String phoneNumber;
    private String lastMessage;
    private String timeForLastMessage;


    public Discussion(String name, String lastMessage, String timeForLastMessage) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.timeForLastMessage = timeForLastMessage;
    }

    public Discussion(String name, String phoneNumber ,String lastMessage, String timeForLastMessage) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.timeForLastMessage = timeForLastMessage;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTimeForLastMessage() {
        return timeForLastMessage;
    }

    public void setTimeForLastMessage(String timeForLastMessage) {
        this.timeForLastMessage = timeForLastMessage;
    }
}
