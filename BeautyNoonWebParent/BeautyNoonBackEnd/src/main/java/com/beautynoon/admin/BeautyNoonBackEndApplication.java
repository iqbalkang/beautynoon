package com.beautynoon.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.beautynoon.common")
public class BeautyNoonBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeautyNoonBackEndApplication.class, args);
	}

}
