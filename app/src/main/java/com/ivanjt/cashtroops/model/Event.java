package com.ivanjt.cashtroops.model;

public class Event {
    public static final String PATH_NAME = "events";
    private String id;
    private String groupId;
    private String date;
    private long targetAmount;
    private String title;
    private String description;

    public Event() {
    }

    public Event(String id, String groupId, String date, long targetAmount, String title, String description) {
        this.id = id;
        this.groupId = groupId;
        this.date = date;
        this.targetAmount = targetAmount;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(long targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
