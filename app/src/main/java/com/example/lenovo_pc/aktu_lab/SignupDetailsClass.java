package com.example.lenovo_pc.aktu_lab;

public class SignupDetailsClass {
    String uid;
    String email;

    public SignupDetailsClass(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public SignupDetailsClass() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
