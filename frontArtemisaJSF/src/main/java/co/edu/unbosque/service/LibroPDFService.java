package co.edu.unbosque.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LibroPDFService {
	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(5)).build();

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
}