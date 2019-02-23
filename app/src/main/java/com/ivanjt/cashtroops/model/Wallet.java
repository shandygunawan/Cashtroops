package com.ivanjt.cashtroops.model;

public class Wallet {
    public static final String PATH_NAME = "wallets";
    private String cashTag;
    private long amount;

    public Wallet() {
    }

    public Wallet(String cashTag, long amount) {
        this.cashTag = cashTag;
        this.amount = amount;
    }

    public String getCashTag() {
        return cashTag;
    }

    public void setCashTag(String cashTag) {
        this.cashTag = cashTag;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
