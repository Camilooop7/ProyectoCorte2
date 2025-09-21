package co.edu.unbosque.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;

public class LibroPDFService {
	public static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(30)).build();

	public static byte[] getImagenById(Long id) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("http://localhost:8081/libro/pdf/descargar-imagen/" + id)).build();
		HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
		return response.body();
	}

	public static byte[] getPdfContentById(Long id) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("http://localhost:8081/libro/pdf/descargar-pdf/" + id)).build();
		HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
		return response.body();
	}

	public static String doPostLibroPDF(String url, int codigo, String nombre, String descripcion, byte[] imagen,
			byte[] archivoPdf) {
		try {
			// Generar un boundary único para el multipart
			String boundary = UUID.randomUUID().toString();

			// Construir el cuerpo de la solicitud como multipart/form-data
			StringBuilder requestBodyBuilder = new StringBuilder();

			// Añadir parámetros de texto
			requestBodyBuilder.append("--").append(boundary).append("\r\n");
			requestBodyBuilder.append("Content-Disposition: form-data; name=\"codigo\"\r\n\r\n");
			requestBodyBuilder.append(codigo).append("\r\n");

			requestBodyBuilder.append("--").append(boundary).append("\r\n");
			requestBodyBuilder.append("Content-Disposition: form-data; name=\"nombre\"\r\n\r\n");
			requestBodyBuilder.append(nombre).append("\r\n");

			requestBodyBuilder.append("--").append(boundary).append("\r\n");
			requestBodyBuilder.append("Content-Disposition: form-data; name=\"descripcion\"\r\n\r\n");
			requestBodyBuilder.append(descripcion).append("\r\n");

			// Añadir imagen
			requestBodyBuilder.append("--").append(boundary).append("\r\n");
			requestBodyBuilder.append("Content-Disposition: form-data; name=\"imagen\"; filename=\"imagen.jpg\"\r\n");
			requestBodyBuilder.append("Content-Type: image/jpeg\r\n\r\n");

			// Añadir PDF
			requestBodyBuilder.append("--").append(boundary).append("\r\n");
			requestBodyBuilder
					.append("Content-Disposition: form-data; name=\"archivoPdf\"; filename=\"libro.pdf\"\r\n");
			requestBodyBuilder.append("Content-Type: application/pdf\r\n\r\n");

			// Cerrar el multipart
			requestBodyBuilder.append("--").append(boundary).append("--\r\n");

			// Crear el BodyPublisher para enviar los datos binarios
			HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofInputStream(
					() -> new MultipartInputStream(requestBodyBuilder.toString().getBytes(StandardCharsets.UTF_8),
							imagen, archivoPdf, boundary));

			HttpRequest request = HttpRequest.newBuilder().POST(bodyPublisher).uri(URI.create(url))
					.setHeader("User-Agent", "Java 11 HttpClient Bot")
					.header("Content-Type", "multipart/form-data; boundary=" + boundary).build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode() + "\n" + response.body();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return "500\nError en la conexión con el servidor: " + e.getMessage();
		}
	}

	// Clase interna para manejar el InputStream de multipart/form-data
	private static class MultipartInputStream extends InputStream {
		private final byte[] part1;
		private final byte[] part2;
		private final byte[] part3;
		private final String boundary;
		private int state = 0;
		private int pos = 0;

		public MultipartInputStream(byte[] part1, byte[] imagen, byte[] pdf, String boundary) {
			this.part1 = part1;
			this.part2 = imagen;
			this.part3 = pdf;
			this.boundary = boundary;
		}

		@Override
		public int read() throws IOException {
			switch (state) {
			case 0:
				if (pos < part1.length) {
					return part1[pos++];
				} else {
					state++;
					pos = 0;
					return '\r';
				}
			case 1:
				if (pos < part2.length) {
					return part2[pos++];
				} else {
					state++;
					pos = 0;
					return '\r';
				}
			case 2:
				if (pos < part3.length) {
					return part3[pos++];
				} else {
					state++;
					pos = 0;
					return -1;
				}
			default:
				return -1;
			}
		}
	}
}
