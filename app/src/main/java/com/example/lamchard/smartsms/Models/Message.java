package com.example.lamchard.smartsms.Models;

public class Message {

    private String textMessage;
    private boolean isMe;

    public Message(String textMessage, boolean isMe) {
        this.textMessage = textMessage;
        this.isMe = isMe;
    }

    public Message() {
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
