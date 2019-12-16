package com.example.phoenix.healthcare.Models;

import java.text.DateFormat;
import java.util.Date;

public class ChatModel {
    public String message;
    public String date;
    public boolean isSend;

    public ChatModel(String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
        this.date = DateFormat.getDateTimeInstance().format(new Date());
    }

    public ChatModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
