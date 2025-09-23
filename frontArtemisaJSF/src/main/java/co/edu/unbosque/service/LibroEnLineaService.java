package co.edu.unbosque.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LibroEnLineaService {

	public static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(30)).build();

	/**
	 * Envía una solicitud POST al backend para crear un libro en línea.
	 *
	 * @param url         URL del endpoint.
	 * @param codigo      Código del libro.
	 * @param nombre      Nombre del libro.
	 * @param descripcion Descripción del libro.
	 * @param link        Enlace del libro.
	 * @return String con el código de respuesta y mensaje del servidor.
	 */
	public static String doPostLibroEnLinea(String url, int codigo, String nombre, String descripcion, String link) {
		try {
			// Construye el cuerpo de la solicitud como form-urlencoded
			String requestBody = "codigo=" + codigo + "&nombre=" + java.net.URLEncoder.encode(nombre, "UTF-8")
					+ "&descripcion=" + java.net.URLEncoder.encode(descripcion, "UTF-8") + "&link="
					+ java.net.URLEncoder.encode(link, "UTF-8");

			// Crea la solicitud HTTP
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
					.header("Content-Type", "application/x-www-form-urlencoded")
					.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

			// Envía la solicitud y obtiene la respuesta
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode() + "\n" + response.body();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "500\nError al enviar el libro en línea: " + e.getMessage();
		}
	}

	/**
	 * Obtiene la información de un libro en línea en formato JSON desde el backend.
	 *
	 * @param id Código del libro.
	 * @return String con la respuesta JSON del servidor.
	 * @throws IOException          Si hay un error en la conexión.
	 * @throws InterruptedException Si la solicitud es interrumpida.
	 */
	public static String getLibroInfoById(int id) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8081/libroenlinea/" + id))
				.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}
	public static String doDeleteLibroPDF(String url, int codigo) {
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + "/" + codigo)).DELETE().build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode() + "\n" + response.body();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "500\nError al eliminar el libro: " + e.getMessage();
		}
	}
}
