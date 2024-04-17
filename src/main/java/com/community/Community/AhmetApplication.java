package com.community.Community;

import Users.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class AhmetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AhmetApplication.class, args);
	}

	@GetMapping
	public List<User> hello() {
		return List.of(
				new User(1,
						"surname",
						"email",
						"password",
						"username",
						"name",
						true,
						true)
				);
	}
}
