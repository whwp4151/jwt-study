package com.codetest.jwtstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JwtStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtStudyApplication.class, args);
	}

}
