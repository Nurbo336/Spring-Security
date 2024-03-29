package com.example.test.entity;

import com.example.test.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Status userStatus;
    private String username;
    private String password;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
