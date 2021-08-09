package com.jct.rvv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "com.*")
public class RvvApplication {

	public static void main(String[] args) {
		SpringApplication.run(RvvApplication.class, args);
	}

}
