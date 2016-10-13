package com.bros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BootSessionRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootSessionRedisApplication.class, args);
	}
}
