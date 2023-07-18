package com.model;

import java.time.LocalDate;

public class Vaccine {
    private String name;
    private int initial;
    private int balance;
    private LocalDate dateReceived;

    public Vaccine(String name, int initial, int balance, LocalDate currentDate) {
        this.name = name;
        this.initial = initial;
        this.balance = balance;
        this.dateReceived = currentDate;
    }

    public String getName() {
        return name;
    }

    public void setInitial(int initial) {
    this.initial = initial;
  }

  public int getInitial() {
        return initial;
    }

    public int getBalance() {
        return balance;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
