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
 * Servicio para registrar administradores en el backend. Envía solicitudes POST
 * con los datos del administrador.
 */
public class AdminRegistroService {

	/** Cliente HTTP configurado para realizar solicitudes. */
	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(5)).build();

	/**
	 * Envía una solicitud POST para registrar un administrador.
	 *
	 * @param url        URL del endpoint de registro.
	 * @param nombre     Nombre del administrador.
	 * @param documento  Documento del administrador.
	 * @param correo     Correo del administrador.
	 * @param contrasena Contraseña del administrador.
	 * @param rol        Rol del administrador.
	 * @param cargo      Cargo del administrador.
	 * @return Respuesta del servidor con el código de estado y el cuerpo.
	 */
	public static String doPostAdmin(String url, String nombre, String documento, String correo, String contrasena,
			String rol, String cargo) {
		try {
			String encodedNombre = URLEncoder.encode(nombre, StandardCharsets.UTF_8);
			String encodedDocumento = URLEncoder.encode(documento, StandardCharsets.UTF_8);
			String encodedCorreo = URLEncoder.encode(correo, StandardCharsets.UTF_8);
			String encodedContrasena = URLEncoder.encode(contrasena, StandardCharsets.UTF_8);
			String encodedRol = URLEncoder.encode(rol, StandardCharsets.UTF_8);
			String encodedCargo = URLEncoder.encode(cargo, StandardCharsets.UTF_8);

			String fullUrl = url + "?nombre=" + encodedNombre + "&documento=" + encodedDocumento + "&correo="
					+ encodedCorreo + "&contrasena=" + encodedContrasena + "&rol=" + encodedRol + "&cargo="
					+ encodedCargo;

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
