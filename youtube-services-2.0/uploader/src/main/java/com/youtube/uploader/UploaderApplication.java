package com.youtube.uploader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UploaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploaderApplication.class, args);
	}

}
