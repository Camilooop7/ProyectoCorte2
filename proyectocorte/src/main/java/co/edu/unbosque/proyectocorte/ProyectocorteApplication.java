package co.edu.unbosque.proyectocorte;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de la aplicación Spring Boot. Configura y ejecuta la
 * aplicación, además de definir un bean para ModelMapper.
 */
@SpringBootApplication
public class ProyectocorteApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 *
	 * @param args Argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProyectocorteApplication.class, args);
	}

	/**
	 * Crea y devuelve una instancia de ModelMapper como bean de Spring. ModelMapper
	 * se utiliza para mapear objetos DTO a entidades y viceversa.
	 *
	 * @return Instancia de ModelMapper.
	 */
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
