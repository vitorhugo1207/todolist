package com.vitor.todolist.user;

import lombok.Data;

@Data // add getters and setters
public class User {

    private String username;
    private String name;
    private String password;

    // Constructors
    public User() {
    }

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
    
}
