package com.example.test.service;

import com.example.test.entity.Role;
import com.example.test.entity.User;
import com.example.test.enums.Status;
import com.example.test.repository.RoleRepo;
import com.example.test.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username)     {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {

        return userRepo.findAll();
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserStatus(Status.ACTIVE);
        return userRepo.save(user);
    }

    @Override
    public User blockUser(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("User not found with username: " + username);
        }
        user.setUserStatus(Status.BLOCKED);


        return userRepo.save(user);
    }

    @Override
    public User unlockUser(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("User not found with username: " + username);
        }
        if (user.getUserStatus() == Status.BLOCKED){

            user.setUserStatus(Status.ACTIVE);
        }

        return userRepo.save(user);

    }

    @Override
    public void deleteUser(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new NullPointerException("User not found with username: " + username);
        }
        userRepo.delete(user);
    }
    @Override
    public boolean isUserActive(User user) {
        String username = user.getUsername();
        User user1 = userRepo.findByUsername(username);
        return user1 != null && user1.getUserStatus() == Status.ACTIVE;
    }
}