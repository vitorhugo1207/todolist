package com.vitor.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

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
    // ResponseEntity to return http status codes, erros, etc
    public ResponseEntity create(@RequestBody User user) { // @RequestBody tell to spring date will be at body mensage
        var userName = this.userRepository.findByUsername(user.getUsername());

        if (userName != null) {
            System.out.println("User already exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist");
        }

        var passwordCrypted = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()); // .toCharArray() transform password to Character Array

        user.setPassword(passwordCrypted);

        var userCreated = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
