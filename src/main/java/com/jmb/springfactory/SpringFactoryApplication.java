package com.jmb.springfactory;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class SpringFactoryApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(SpringFactoryApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	  return new ModelMapper();
	}
}
