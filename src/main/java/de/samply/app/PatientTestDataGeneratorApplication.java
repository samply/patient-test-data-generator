package de.samply.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.samply"})
//@EnableScheduling
public class PatientTestDataGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientTestDataGeneratorApplication.class, args);
	}

}
