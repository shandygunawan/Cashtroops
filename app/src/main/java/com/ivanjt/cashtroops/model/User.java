package com.ivanjt.cashtroops.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    public static final String PATH_NAME = "users";
    private String id;
    private String name;
    private String cashTag;
    private long phoneNumber;
    private Map<String, Boolean> groups;

    public User() {
    }

    public User(String id, String name, String cashTag, long phoneNumber) {
        this.id = id;
        this.name = name;
        this.cashTag = cashTag;
        this.phoneNumber = phoneNumber;
        groups = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCashTag() {
        return cashTag;
    }

    public void setCashTag(String cashTag) {
        this.cashTag = cashTag;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addGroup(String id) {
        if (groups == null) {
            groups = new HashMap<>();
        }

        groups.put(id, true);
    }

    public void deleteGroup(String id) {
        groups.remove(id);
    }

    public Map<String, Boolean> getGroups() {
        return groups;
    }
}
