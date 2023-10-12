package com.vitor.todolist.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data // add getters and setters 
@Entity(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String username;
    private String name;
    private String password;

    // Store when data was created
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Constructors
    public User() {
    }

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
    
}
