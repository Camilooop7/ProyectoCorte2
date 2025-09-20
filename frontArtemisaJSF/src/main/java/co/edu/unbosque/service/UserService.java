package co.edu.unbosque.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class UserService {
	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(5)).build();

	public static String doPost(String url, String correo, String contrasena) {
		try {
			// Codifica los parámetros para la URL
			String encodedCorreo = URLEncoder.encode(correo, StandardCharsets.UTF_8);
			String encodedContrasena = URLEncoder.encode(contrasena, StandardCharsets.UTF_8);

			// Construye la URL con los parámetros
			String fullUrl = url + "?correo=" + encodedCorreo + "&contrasena=" + encodedContrasena;

			// Crea la solicitud HTTP
			HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody()) // No se envía
																										// cuerpo, solo
																										// parámetros en
																										// la URL
					.uri(URI.create(fullUrl)).setHeader("User-Agent", "Java 11 HttpClient Bot")
					.header("Content-Type", "application/x-www-form-urlencoded").build();

			// Envía la solicitud y obtiene la respuesta
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode() + "\n" + response.body();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return "500\nError en la conexión con el servidor: " + e.getMessage();
		}
	}
}
