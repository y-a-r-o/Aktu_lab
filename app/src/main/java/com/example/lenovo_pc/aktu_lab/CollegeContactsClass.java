package com.example.lenovo_pc.aktu_lab;

public class CollegeContactsClass {
   String email;
   String website;
   String phone_no;

    public CollegeContactsClass() {
    }


    public CollegeContactsClass(String email, String website, String phone_no) {
        this.email = email;
        this.website = website;
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
