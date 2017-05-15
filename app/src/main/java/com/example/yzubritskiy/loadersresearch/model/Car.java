package com.example.yzubritskiy.loadersresearch.model;

/**
 * Created by yzubritskiy on 5/14/2017.
 */

public class Car {
    private int number;
    private int year;
    private String model;
    private long ownerId;
    private long id;

    public Car(int number, int year, String model, long ownerId, long id) {
        this.number = number;
        this.year = year;
        this.model = model;
        this.ownerId = ownerId;
        this.id = id;
    }

    public Car(int number, int year, String model) {
        this.number = number;
        this.year = year;
        this.model = model;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Car(int number, int year, String model, long ownerId) {

        this.number = number;
        this.year = year;
        this.model = model;
        this.ownerId = ownerId;
    }
}
