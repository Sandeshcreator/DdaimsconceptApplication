package com.learn.dams;

import com.learn.dams.entities.Closure;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DdaimsconceptApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdaimsconceptApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public Closure closure(){
		return new Closure();
	}

}
