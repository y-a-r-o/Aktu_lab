package com.example.lenovo_pc.aktu_lab;

public class TimeClass {
    String purpose;
    long timestamp;

    public TimeClass() {
    }

    public TimeClass(String purpose, long timestamp) {
        this.purpose = purpose;
        this.timestamp = timestamp;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
