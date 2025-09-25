package co.edu.unbosque.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * Servicio para cargar ejemplos de extensiones de calendario desde un archivo
 * de propiedades. Permite leer y parsear ejemplos de extensiones definidos en
 * un archivo .properties.
 */
@Named
@ApplicationScoped
public class ExtenderService {

	/**
	 * Nombre del archivo de propiedades que contiene los ejemplos de extensiones.
	 */
	private static final String FILE_NAME = "schedule-extender-examples.properties";

	/**
	 * Carga los ejemplos de extensiones desde el archivo de propiedades.
	 *
	 * @return Mapa con los ejemplos de extensiones, indexados por su clave.
	 */
	public Map<String, ExtenderExample> createExtenderExamples() {
		Properties properties = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_NAME);
		if (in == null) {
			in = ExtenderService.class.getResourceAsStream(FILE_NAME);
		}
		if (in == null) {
			in = ExtenderService.class.getResourceAsStream("/" + FILE_NAME);
		}
		if (in == null) {
			System.err.println("[ExtenderService] No se encontró " + FILE_NAME + " en el classpath.");
			return new HashMap<>();
		}
		try (InputStream is = in) {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<>();
		}
		Map<String, ExtenderExample> extenderExamples = new HashMap<>();
		for (String key : properties.stringPropertyNames()) {
			if (key.endsWith(".name")) {
				String baseKey = key.substring(0, key.length() - 5);
				ExtenderExample ex = new ExtenderExample(baseKey, properties);
				if (notBlank(ex.getName()) && notBlank(ex.getValue())) {
					extenderExamples.put(baseKey, ex);
				}
			}
		}
		return extenderExamples;
	}

	/**
	 * Verifica si una cadena no es nula ni vacía.
	 *
	 * @param s Cadena a verificar.
	 * @return {@code true} si la cadena no es nula ni vacía.
	 */
	private boolean notBlank(String s) {
		return s != null && !s.trim().isEmpty();
	}

	/**
	 * Clase interna que representa un ejemplo de extensión.
	 */
	public static class ExtenderExample {
		private final String key;
		private final String name;
		private final String details;
		private final String html;
		private final String link;
		private final String value;

		/**
		 * Constructor de la clase.
		 *
		 * @param key Clave del ejemplo.
		 * @param p   Propiedades desde las que se extraen los valores.
		 */
		public ExtenderExample(String key, Properties p) {
			this.key = key;
			this.name = p.getProperty(key + ".name");
			this.details = p.getProperty(key + ".details");
			this.html = p.getProperty(key + ".html");
			this.link = p.getProperty(key + ".link");
			this.value = p.getProperty(key + ".value");
		}

		/**
		 * Obtiene la clave del ejemplo.
		 *
		 * @return Clave del ejemplo.
		 */
		public String getKey() {
			return key;
		}

		/**
		 * Obtiene el nombre del ejemplo.
		 *
		 * @return Nombre del ejemplo.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Obtiene los detalles del ejemplo.
		 *
		 * @return Detalles del ejemplo.
		 */
		public String getDetails() {
			return details;
		}

		/**
		 * Obtiene el HTML asociado al ejemplo.
		 *
		 * @return HTML del ejemplo.
		 */
		public String getHtml() {
			return html;
		}

		/**
		 * Obtiene el enlace asociado al ejemplo.
		 *
		 * @return Enlace del ejemplo.
		 */
		public String getLink() {
			return link;
		}

		/**
		 * Obtiene el valor asociado al ejemplo.
		 *
		 * @return Valor del ejemplo.
		 */
		public String getValue() {
			return value;
		}
	}
}
