package com.example.redis.streams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedisStreamsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisStreamsExampleApplication.class, args);
	}

}
