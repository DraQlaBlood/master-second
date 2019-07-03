package com.agi.secondaryUserService.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;


@Document(collection ="group")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {
    @Id
    private UUID id;
    private String name;

    private List<User> users ;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Group(UUID id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
