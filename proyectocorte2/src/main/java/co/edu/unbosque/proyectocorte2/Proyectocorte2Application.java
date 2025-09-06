package co.edu.unbosque.proyectocorte2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Proyectocorte2Application {

	public static void main(String[] args) {
		SpringApplication.run(Proyectocorte2Application.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();

	}
}
