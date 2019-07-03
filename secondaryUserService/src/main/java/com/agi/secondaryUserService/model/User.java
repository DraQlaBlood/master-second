package com.agi.secondaryUserService.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;


@Document(collection ="user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    private UUID id;
    String email;

    List<Group> groups;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(UUID id, String email, List<Group> groups) {
        this.id = id;
        this.email = email;
        this.groups = groups;
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
