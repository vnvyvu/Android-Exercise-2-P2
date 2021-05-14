package com.vyvu.th2b2.model;

import java.io.Serializable;

public class QuyenGop implements Serializable {
    private int id;
    private int city;
    private String name, date;
    private double price;

    public QuyenGop(int id, String name, int city, double price, String date) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.price = price;
        this.date = date;
    }

    public QuyenGop(String name, int city, double price, String date) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
