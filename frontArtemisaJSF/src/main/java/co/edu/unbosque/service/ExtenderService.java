package co.edu.unbosque.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * Servicio encargado de cargar y administrar ejemplos de extensores desde un archivo de propiedades.
 * Proporciona métodos para obtener ejemplos de extensores configurados en el sistema.
 */
@Named
@ApplicationScoped
public class ExtenderService {

    /** Nombre del archivo de propiedades que contiene los ejemplos de extensores. */
    private static final String FILE_NAME = "schedule-extender-examples.properties";

    /**
     * Crea un mapa de ejemplos de extensores a partir del archivo de propiedades.
     * Cada ejemplo contiene información relevante como nombre, detalles, HTML, enlace y valor.
     *
     * @return Mapa con la clave base del extensor como llave y el objeto ExtenderExample como valor.
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
     * Verifica si una cadena no es nula ni está vacía después de quitar espacios.
     * @param s la cadena a verificar
     * @return true si la cadena no es nula ni vacía, false en caso contrario
     */
    private boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    /**
     * Clase estática que representa un ejemplo de extensor con diferentes atributos.
     */
    public static class ExtenderExample {
        /** Clave base del ejemplo. */
        private final String key;
        /** Nombre del ejemplo. */
        private final String name;
        /** Detalles adicionales del ejemplo. */
        private final String details;
        /** Fragmento HTML asociado al ejemplo. */
        private final String html;
        /** Enlace relacionado al ejemplo. */
        private final String link;
        /** Valor o código del ejemplo. */
        private final String value;

        /**
         * Construye un ejemplo de extensor a partir de la clave y las propiedades.
         * @param key clave base del ejemplo
         * @param p objeto Properties con los datos del ejemplo
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
         * Obtiene la clave base del ejemplo.
         * @return clave base
         */
        public String getKey() {
            return key;
        }

        /**
         * Obtiene el nombre del ejemplo.
         * @return nombre del ejemplo
         */
        public String getName() {
            return name;
        }

        /**
         * Obtiene los detalles adicionales del ejemplo.
         * @return detalles del ejemplo
         */
        public String getDetails() {
            return details;
        }

        /**
         * Obtiene el fragmento HTML asociado al ejemplo.
         * @return html del ejemplo
         */
        public String getHtml() {
            return html;
        }

        /**
         * Obtiene el enlace relacionado al ejemplo.
         * @return enlace del ejemplo
         */
        public String getLink() {
            return link;
        }

        /**
         * Obtiene el valor o código del ejemplo.
         * @return valor del ejemplo
         */
        public String getValue() {
            return value;
        }
    }
}