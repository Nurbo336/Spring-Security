package com.example.test.service;

import com.example.test.entity.Role;
import com.example.test.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();

    User createUser(User user);
    User blockUser(String username);
    User unlockUser(String username);
    void deleteUser(String username);
}
