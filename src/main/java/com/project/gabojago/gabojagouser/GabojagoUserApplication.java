package com.project.gabojago.gabojagouser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(@PropertySource("classpath:/env.properties"))
public class GabojagoUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(GabojagoUserApplication.class, args);
	}

}
