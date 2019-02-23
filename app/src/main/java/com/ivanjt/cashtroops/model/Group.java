package com.ivanjt.cashtroops.model;

import java.util.HashMap;
import java.util.Map;

public class Group {
    public static String PATH_NAME = "groups";
    private String id;
    private String name;
    private String cashTag;
    private int dayAutoDebet;
    private String adminId;
    private Map<String, Boolean> members;

    public Group(String id, String name, String cashTag, int dayAutoDebet, String adminId) {
        this.id = id;
        this.name = name;
        this.cashTag = cashTag;
        this.dayAutoDebet = dayAutoDebet;
        this.adminId = adminId;
        members = new HashMap<>();
    }

    public Group() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getDayAutoDebet() {
        return dayAutoDebet;
    }

    public void setDayAutoDebet(int dayAutoDebet) {
        this.dayAutoDebet = dayAutoDebet;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void addMember(String id) {
        members.put(id, true);
    }

    public void deleteMember(String id) {
        members.remove(id);
    }

    public Map<String, Boolean> getMembers() {
        return members;
    }
}
