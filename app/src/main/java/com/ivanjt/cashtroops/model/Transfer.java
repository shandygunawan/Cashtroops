package com.ivanjt.cashtroops.model;

import java.util.Date;

public class Transfer {
    public static final String PATH_NAME = "transfers";
    private String id;
    private int amount;
    private String from;
    private String to;
    private String date;

    public Transfer(String id, String from, String to, int amount, String date) {
        this.id = id;
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
