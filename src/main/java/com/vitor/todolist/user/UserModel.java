package com.vitor.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data // add getters and setters 
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username; // set to database not let repeat
    private String name;
    private String password;

    // Store when data was created
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Constructors
    public UserModel() {
    }

    public UserModel(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
    
}
