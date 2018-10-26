package com.example.lenovo_pc.aktu_lab;

public class CardClass {
    String cardimage;
    String college_name;
    String price_tag;
    String name;
    int key;

    public CardClass() {
    }

    public CardClass(String cardimage, String college_name, String price_tag, String name, int key) {
        this.cardimage = cardimage;
        this.college_name = college_name;
        this.price_tag = price_tag;
        this.name = name;
        this.key = key;
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

    public String getPrice_tag() {
        return price_tag;
    }

    public void setPrice_tag(String price_tag) {
        this.price_tag = price_tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
