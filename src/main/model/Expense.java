package main.model;

import java.util.Date;

public class Expense {
    private Date date;
    private String what;
    private String  category;
    private String who;
    private Double cost;

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Expense(Date date, String what, String category, String who, Double cost) {
        this.date = date;
        this.what = what;
        this.category = category;
        this.who = who;
        this.cost = cost;
    }
}
