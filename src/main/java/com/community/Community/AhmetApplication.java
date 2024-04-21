package com.community.Community;

import com.community.Community.Repositories.UserRepository;
import com.community.Community.Services.IUserService;
import com.community.Community.dto.UserDto;
import com.community.Community.models.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class AhmetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AhmetApplication.class, args);
	}


}
