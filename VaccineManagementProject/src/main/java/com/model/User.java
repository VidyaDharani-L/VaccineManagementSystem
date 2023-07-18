package com.model;

import java.time.LocalDate;

public class User {
    private String name;
    private int age;
    private long aadhar;
    private LocalDate firstDose;
    private LocalDate secondDose;

    public User(String name, int age, long aadhar, LocalDate firstDose, LocalDate secondDose) {
        this.name = name;
        this.age = age;
        this.aadhar = aadhar;
        this.firstDose = firstDose;
        this.secondDose = secondDose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getAadhar() {
        return aadhar;
    }

    public void setAadhar(long aadhar) {
        this.aadhar = aadhar;
    }

    public LocalDate getFirstDose() {
        return firstDose;
    }

    public void setFirstDose(LocalDate localDate) {
        this.firstDose = localDate;
    }

    public LocalDate getSecondDose() {
        return secondDose;
    }

    public void setSecondDose(LocalDate secondDose) {
        this.secondDose = secondDose;
    }
}
