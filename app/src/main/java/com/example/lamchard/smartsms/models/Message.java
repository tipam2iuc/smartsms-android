package com.example.lamchard.smartsms.Models;

public class Message {

    private String textMessage;
    private String date;
    private String time;
    private boolean isMe;
    private TypeMessage type;

    public enum TypeMessage {
        LineStart,
        Conversation,
        Echec,
        Debut,
        BlockStart,
        BlockContent,
        BlockEnd
    }

    public Message(String textMessage, boolean isMe, TypeMessage type, String time, String date) {
        this.textMessage = textMessage;
        this.isMe = isMe;
        this.type = type;
        this.time = time;
        this.date = date;
    }

    public Message(String date, TypeMessage type) {
        this.textMessage = date;
        this.isMe = isMe;
        this.type = type;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Message() {
    }

    public TypeMessage getType() {
        return type;
    }

    public void setType(TypeMessage type) {
        this.type = type;
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
