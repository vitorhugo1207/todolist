package com.vitor.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * JpaRepository<User, UUID>
 * User -> Class
 * UUID -> ID Type
 */
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username); // Search in database if exist a identical username, check UserControler
}
