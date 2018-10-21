package com.example.lenovo_pc.aktu_lab;

public class LabcatClass {
    String title;
    String bg;

    public LabcatClass(String title, String bg) {
        this.title = title;
        this.bg = bg;
    }

    public LabcatClass() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }
}
