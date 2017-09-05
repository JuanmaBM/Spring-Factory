package com.jmb.springfactory;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class,
  DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, 
  HibernateJpaAutoConfiguration.class})
public class SpringFactoryApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(SpringFactoryApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	  return new ModelMapper();
	}
}
