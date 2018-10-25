package com.example.lenovo_pc.aktu_lab;

public class LabDetailsClass {
    String address;
    String cardimage;
    String college_name;
    String description;
    String image1;
    String image2;
    String image3;
    int key;
    String name;
    String price_tag;
    int seats;
    String college_key;

    public LabDetailsClass() {
    }

    public LabDetailsClass(String address, String cardimage, String college_name, String description, String image1, String image2, String image3, int key, String name, String price_tag, int seats, String college_key) {
        this.address = address;
        this.cardimage = cardimage;
        this.college_name = college_name;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.key = key;
        this.name = name;
        this.price_tag = price_tag;
        this.seats = seats;
        this.college_key = college_key;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardimage() {
        return cardimage;
    }

    public void setCardimage(String cardimage) {
        this.cardimage = cardimage;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice_tag() {
        return price_tag;
    }

    public void setPrice_tag(String price_tag) {
        this.price_tag = price_tag;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getCollege_key() {
        return college_key;
    }

    public void setCollege_key(String college_key) {
        this.college_key = college_key;
    }
}

