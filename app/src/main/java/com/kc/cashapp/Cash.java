package com.kc.cashapp;

/**
 * Created by 5078682 on 5/18/2016.
 */
public class Cash {

private long id;

private String category;

private double amount;

private int date;

//private int date;


    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Cash{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
