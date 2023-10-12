package com.vitor.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    // Constructor
    public UserController() {
    }

    // Methods 
    @PostMapping("/")
    public void create(@RequestBody User user) {
        System.out.println(user.getName());
    }
}
