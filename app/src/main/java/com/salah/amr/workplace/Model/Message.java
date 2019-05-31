package com.salah.amr.workplace.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 12/9/2017.
 */

public class Message {
   private  String text;
    private int userId;
    private long timeStamp;

    public Message(String text, int userId, long timeStamp) {
        this.text = text;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }

    public Message() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", timeStamp=" + timeStamp +
                ", userId=" + userId +
                '}';
    }
}
