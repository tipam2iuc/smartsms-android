package com.example.lamchard.smartsms.Models;

public class Contact {

    private String Name;
    private String Phone;
    private int Photo;

    public Contact(String name, String phone, int photo) {
        Name = name;
        Phone = phone;
        Photo = photo;
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
}
