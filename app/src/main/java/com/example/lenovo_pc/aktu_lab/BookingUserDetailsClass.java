package com.example.lenovo_pc.aktu_lab;

public class BookingUserDetailsClass {
    String name;
    String rollno;
    String college;
    String phone;
    String account_email;
    String account_uid;

    public BookingUserDetailsClass() {
    }

    public BookingUserDetailsClass(String name, String rollno, String college, String phone, String account_email, String account_uid) {
        this.name = name;
        this.rollno = rollno;
        this.college = college;
        this.phone = phone;
        this.account_email = account_email;
        this.account_uid = account_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount_email() {
        return account_email;
    }

    public void setAccount_email(String account_email) {
        this.account_email = account_email;
    }

    public String getAccount_uid() {
        return account_uid;
    }

    public void setAccount_uid(String account_uid) {
        this.account_uid = account_uid;
    }
}
