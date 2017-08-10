package com.jmb.springfactory;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringFactoryApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(SpringFactoryApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	  return new ModelMapper();
	}
}
