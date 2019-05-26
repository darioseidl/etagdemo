package at.darioseidl.etagdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class EtagdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtagdemoApplication.class, args);
	}
}
