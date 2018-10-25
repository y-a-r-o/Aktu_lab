package com.example.lenovo_pc.aktu_lab;

import android.database.DatabaseErrorHandler;

public class TimeslotClass {
    String date;
    int price;
    String time;

    public TimeslotClass() {
    }

    public TimeslotClass(String date, int price, String time) {
        this.date = date;
        this.price = price;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
