package com.hocviec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// @SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HocviecApplication {

	public static void main(String[] args) {
		SpringApplication.run(HocviecApplication.class, args);
	}

}
