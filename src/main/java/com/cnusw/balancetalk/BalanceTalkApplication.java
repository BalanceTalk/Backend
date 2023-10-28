package com.cnusw.balancetalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BalanceTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BalanceTalkApplication.class, args);
	}

}
