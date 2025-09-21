package co.edu.unbosque.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class RegistroService {
	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(5)).build();

	public static String doPost(String url, String nombre, String documento, String correo, String contrasena,
			String rol, String carrera, String semestre) {
		try {
			String encodedNombre = URLEncoder.encode(nombre, StandardCharsets.UTF_8);
			String encodedDocumento = URLEncoder.encode(documento, StandardCharsets.UTF_8);
			String encodedCorreo = URLEncoder.encode(correo, StandardCharsets.UTF_8);
			String encodedContrasena = URLEncoder.encode(contrasena, StandardCharsets.UTF_8);
			String encodedRol = URLEncoder.encode(rol, StandardCharsets.UTF_8);
			String encodedCarrera = URLEncoder.encode(carrera, StandardCharsets.UTF_8);
			String encodedSemestre = URLEncoder.encode(semestre, StandardCharsets.UTF_8);

			String fullUrl = url + "?nombre=" + encodedNombre + "&documento=" + encodedDocumento + "&correo="
					+ encodedCorreo + "&contrasena=" + encodedContrasena + "&rol=" + encodedRol + "&carrera="
					+ encodedCarrera + "&semestre=" + encodedSemestre;

			HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody())
					.uri(URI.create(fullUrl)).setHeader("User-Agent", "Java 11 HttpClient Bot")
					.header("Content-Type", "application/x-www-form-urlencoded").build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode() + "\n" + response.body();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return "500\nError en la conexión con el servidor: " + e.getMessage();
		}
	}
}