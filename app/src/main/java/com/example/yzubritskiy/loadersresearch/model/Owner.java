package com.example.yzubritskiy.loadersresearch.model;

import java.util.Date;
import java.util.List;

/**
 * Created by yzubritskiy on 5/14/2017.
 */

public class Owner {

    private String firstName;
    private String secondName;

    public Owner(String firstName, String secondName, Date birthDate, long id) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.id = id;
    }

    private Date birthDate;
    private List<Car> cars;
    private long id;

    public Owner(String firstName, String secondName, Date birthDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
