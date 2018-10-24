package com.example.lenovo_pc.aktu_lab;

import android.database.DatabaseErrorHandler;

public class TimeSlotClass {
    String Date;
    String Time;
    int Price;

    public TimeSlotClass() {
    }
    public TimeSlotClass(String date, String time, int price) {
        Date = date;
        Time = time;
        Price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
