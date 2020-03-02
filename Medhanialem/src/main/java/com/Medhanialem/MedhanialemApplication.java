package com.Medhanialem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MedhanialemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedhanialemApplication.class, args);
	}

}
