package com.community.Community;

import com.community.Community.Repositories.UserRepository;
import com.community.Community.models.Users.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class AhmetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AhmetApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository) {
		return args -> {
			User existingAdmin = userRepository.findByUsername("adminusername");
			if (existingAdmin == null) {
				User adminUser = User.builder()
						.name("Admin")
						.surname("User")
						.email("admin@example.com")
						.password("admin") // Consider using a password encoder
						.username("admin")
						.build();
				userRepository.save(adminUser);
				System.out.println("Admin user created!");
			} else {
				System.out.println("Admin user already exists.");
			}
		};
	}
}
