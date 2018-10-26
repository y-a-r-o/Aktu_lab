package com.example.lenovo_pc.aktu_lab;

public class MyBookingClass {
    String main;
    String category;
    int key;

    public MyBookingClass(String main, String category, int key) {
        this.main = main;
        this.category = category;
        this.key = key;
    }

    public MyBookingClass() {

    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
