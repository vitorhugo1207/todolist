package com.vitor.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired // tell to spring manage
    private IUserRepository userRepository;

    // Constructor
    public UserController() {
    }

    // Methods
    @PostMapping("/")
    public User create(@RequestBody User user) {
        // System.out.println(user.getName());
        var userCreated = this.userRepository.save(user);
        return userCreated;
    }
}
