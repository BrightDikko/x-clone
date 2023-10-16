package com.xclonebackend.xclone;

import com.xclonebackend.xclone.models.ApplicationUser;
import com.xclonebackend.xclone.models.Role;
import com.xclonebackend.xclone.repositories.RoleRepository;
import com.xclonebackend.xclone.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class XCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(XCloneApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserService userService) {
		return args -> {
			roleRepository.save(Role.builder()
					.roleId(1)
					.authority("USER")
					.build());
		};
	}

}
