package co.edu.unbosque.proyectocorte;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase para inicializar la aplicación Spring Boot en un entorno de servlet.
 * Extiende {@link SpringBootServletInitializer} para soportar despliegues en
 * contenedores de servlet externos.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación Spring Boot para ser ejecutada en un contenedor de
	 * servlet.
	 *
	 * @param application Constructor de la aplicación Spring Boot.
	 * @return El constructor de la aplicación configurado con la clase principal.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProyectocorteApplication.class);
	}
}
