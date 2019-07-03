package com.agi.masterUserService.beans;

import com.agi.masterUserService.model.Group;

import java.util.List;
import java.util.UUID;

public class UserBean {
    private UUID id;
    String email;

    List<Group> groups;

    public UserBean() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
