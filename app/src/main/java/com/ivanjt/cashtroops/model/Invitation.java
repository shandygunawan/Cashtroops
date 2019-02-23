package com.ivanjt.cashtroops.model;

public class Invitation {
    public static final String PATH_NAME = "invitations";
    private String id;
    private String groupId;
    private String userId;
    private String status;

    public Invitation() {
    }

    public Invitation(String id, String groupId, String userId, String status) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.status = status;
    }
}
