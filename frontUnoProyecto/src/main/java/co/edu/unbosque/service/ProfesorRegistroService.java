package co.edu.unbosque.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Servicio para registrar profesores en el backend. Envía solicitudes POST con
 * los datos del profesor.
 */
public class ProfesorRegistroService {

	/** Cliente HTTP configurado para realizar solicitudes. */
	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(5)).build();

	/**
	 * Envía una solicitud POST para registrar un profesor.
	 *
	 * @param url          URL del endpoint de registro.
	 * @param nombre       Nombre del profesor.
	 * @param documento    Documento del profesor.
	 * @param correo       Correo del profesor.
	 * @param contrasena   Contraseña del profesor.
	 * @param rol          Rol del profesor.
	 * @param departamento Departamento del profesor.
	 * @return Respuesta del servidor con el código de estado y el cuerpo.
	 */
	public static String doPostProfesor(String url, String nombre, String documento, String correo, String contrasena,
			String rol, String departamento) {
		try {
			String encodedNombre = URLEncoder.encode(nombre, StandardCharsets.UTF_8);
			String encodedDocumento = URLEncoder.encode(documento, StandardCharsets.UTF_8);
			String encodedCorreo = URLEncoder.encode(correo, StandardCharsets.UTF_8);
			String encodedContrasena = URLEncoder.encode(contrasena, StandardCharsets.UTF_8);
			String encodedRol = URLEncoder.encode(rol, StandardCharsets.UTF_8);
			String encodedDepartamento = URLEncoder.encode(departamento, StandardCharsets.UTF_8);

			String fullUrl = url + "?nombre=" + encodedNombre + "&documento=" + encodedDocumento + "&correo="
					+ encodedCorreo + "&contrasena=" + encodedContrasena + "&rol=" + encodedRol + "&departamento="
					+ encodedDepartamento;

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
