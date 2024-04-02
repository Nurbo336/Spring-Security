package com.example.test;

import com.example.test.entity.Role;
import com.example.test.entity.User;
import com.example.test.enums.Status;
import com.example.test.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication

public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}
	@Bean
	CommandLineRunner run(UserService userService) {
		return arg -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "Admin", Status.ACTIVE,"admin", "12345", new ArrayList<>()));
			userService.saveUser(new User(null, "User1",Status.ACTIVE, "user1", "qwerty", new ArrayList<>()));
			userService.saveUser(new User(null, "User2",Status.ACTIVE, "user2", "1111", new ArrayList<>()));

			userService.addRoleToUser("admin", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("user1", "ROLE_USER");
			userService.addRoleToUser("user2", "ROLE_USER");
		};
	}


}
