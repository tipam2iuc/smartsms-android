package com.example.lamchard.smartsms.Models;

import java.io.Serializable;

public class Contact implements Serializable {

    private String Name;
    private String lastMessage;
    private String timeToLastMessage;
    private String Phone;
    private int Photo;

    public Contact(String name, String phone, int photo) {
        Name = name;
        Phone = phone;
        Photo = photo;
    }

    public Contact(String name, String phone, int photo, String lastMessage, String timeToLastMessage){
        this(name,phone,photo);
        this.lastMessage = lastMessage;
        this.timeToLastMessage = timeToLastMessage;
    }

    public Contact() {}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getPhoto() {
        return Photo;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTimeToLastMessage() {
        return timeToLastMessage;
    }

    public void setTimeToLastMessage(String timeToLastMessage) {
        this.timeToLastMessage = timeToLastMessage;
    }
}
