package co.edu.unbosque.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ExtenderService {

	private static final String FILE_NAME = "schedule-extender-examples.properties";

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

	private boolean notBlank(String s) {
		return s != null && !s.trim().isEmpty();
	}

	public static class ExtenderExample {
		private final String key;
		private final String name;
		private final String details;
		private final String html;
		private final String link;
		private final String value;

		public ExtenderExample(String key, Properties p) {
			this.key = key;
			this.name = p.getProperty(key + ".name");
			this.details = p.getProperty(key + ".details");
			this.html = p.getProperty(key + ".html");
			this.link = p.getProperty(key + ".link");
			this.value = p.getProperty(key + ".value");
		}

		public String getKey() {
			return key;
		}

		public String getName() {
			return name;
		}

		public String getDetails() {
			return details;
		}

		public String getHtml() {
			return html;
		}

		public String getLink() {
			return link;
		}

		public String getValue() {
			return value;
		}
	}
}