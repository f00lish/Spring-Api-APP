package com.icy9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.icy9"})
public class SpringApiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringApiAppApplication.class, args);
	}
}
