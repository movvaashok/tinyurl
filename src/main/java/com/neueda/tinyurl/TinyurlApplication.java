package com.neueda.tinyurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.neueda.utility","com.neueda.controllers","com.neueda.dao"})
public class TinyurlApplication {
	public static void main(String[] args) {
		SpringApplication.run(TinyurlApplication.class, args);
	}

}
