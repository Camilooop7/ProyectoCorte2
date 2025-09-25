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
 * Servicio para gestionar cronogramas. Permite crear, listar y eliminar eventos
 * en el cronograma.
 */
public class CronogramaService {

	/** Cliente HTTP configurado para realizar solicitudes. */
	private static final HttpClient HTTP = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(5)).build();

	/**
	 * Codifica una cadena para ser usada en una URL.
	 *
	 * @param s Cadena a codificar.
	 * @return Cadena codificada.
	 */
	private static String enc(String s) {
		return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
	}

	/**
	 * Construye el cuerpo de un formulario para crear un evento.
	 *
	 * @param nombre   Nombre del evento.
	 * @param link     Enlace del evento.
	 * @param fechaISO Fecha del evento en formato ISO.
	 * @return Cuerpo del formulario codificado.
	 */
	private static String formBody(String nombre, String link, String fechaISO) {
		return "nombre=" + enc(nombre) + "&link=" + enc(link) + "&fecha=" + enc(fechaISO);
	}

	/**
	 * Crea un nuevo evento en el cronograma.
	 *
	 * @param urlCrear URL del endpoint para crear eventos.
	 * @param nombre   Nombre del evento.
	 * @param link     Enlace del evento.
	 * @param fechaISO Fecha del evento en formato ISO.
	 * @return Respuesta del servidor con el código de estado y el cuerpo.
	 */
	public static String crear(String urlCrear, String nombre, String link, String fechaISO) {
		String body = formBody(nombre, link, fechaISO);
		HttpRequest reqForm = HttpRequest.newBuilder().uri(URI.create(urlCrear)).timeout(Duration.ofSeconds(8))
				.header("Content-Type", "application/x-www-form-urlencoded").header("Accept", "*/*")
				.POST(HttpRequest.BodyPublishers.ofString(body)).build();
		try {
			HttpResponse<String> res = HTTP.send(reqForm, HttpResponse.BodyHandlers.ofString());
			int sc = res.statusCode();
			// Fallback si el backend no acepta form body
			if (sc == 405 || sc == 415) {
				String fullUrl = urlCrear + "?" + body;
				HttpRequest fallback = HttpRequest.newBuilder().uri(URI.create(fullUrl)).timeout(Duration.ofSeconds(8))
						.header("Accept", "*/*").POST(HttpRequest.BodyPublishers.noBody()).build();
				HttpResponse<String> res2 = HTTP.send(fallback, HttpResponse.BodyHandlers.ofString());
				return res2.statusCode() + "\n" + (res2.body() == null ? "" : res2.body());
			}
			return sc + "\n" + (res.body() == null ? "" : res.body());
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			return "500\nOperación interrumpida";
		} catch (IOException e) {
			return "500\nError en la conexión con el servidor: " + e.getMessage();
		} catch (IllegalArgumentException e) {
			return "500\nURL inválida: " + e.getMessage();
		}
	}

	/**
	 * Lista todos los eventos del cronograma.
	 *
	 * @param baseRoot URL base del servicio.
	 * @return Respuesta del servidor con el código de estado y el cuerpo.
	 */
	public static String mostrarTexto(String baseRoot) {
		try {
			HttpRequest req = HttpRequest.newBuilder().uri(URI.create(baseRoot + "/cronograma/mostrar"))
					.timeout(Duration.ofSeconds(8)).header("Accept", "text/plain,*/*;q=0.8").GET().build();
			HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
			return res.statusCode() + "\n" + (res.body() == null ? "" : res.body());
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			return "500\nOperación interrumpida";
		} catch (IOException e) {
			return "500\nError en la conexión con el servidor: " + e.getMessage();
		} catch (IllegalArgumentException e) {
			return "500\nURL inválida: " + e.getMessage();
		}
	}

	/**
	 * Elimina un evento del cronograma por su ID.
	 *
	 * @param baseRoot URL base del servicio.
	 * @param id       ID del evento a eliminar.
	 * @return Respuesta del servidor con el código de estado y el cuerpo.
	 */
	public static String eliminar(String baseRoot, Long id) {
		try {
			String url = baseRoot + "/cronograma/eliminar?id=" + id;
			HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(8))
					.header("Accept", "*/*").DELETE().build();
			HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
			return res.statusCode() + "\n" + (res.body() == null ? "" : res.body());
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			return "500\nOperación interrumpida";
		} catch (IOException e) {
			return "500\nError en la conexión con el servidor: " + e.getMessage();
		} catch (IllegalArgumentException e) {
			return "500\nURL inválida: " + e.getMessage();
		}
	}
}
