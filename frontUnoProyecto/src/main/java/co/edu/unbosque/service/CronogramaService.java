package co.edu.unbosque.service;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
public class CronogramaService {
	  private static final HttpClient HTTP = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_1_1)
	            .connectTimeout(Duration.ofSeconds(5))
	            .build();

	    private static String enc(String s) {
	        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
	    }


	    public static String crear(String urlCrear,
	                               String nombre,
	                               String link,
	                               String fechaISO ) {
	        try {
	            String fullUrl = urlCrear
	                    + "?nombre=" + enc(nombre)
	                    + "&link="   + enc(link)
	                    + "&fecha="  + enc(fechaISO);

	            HttpRequest req = HttpRequest.newBuilder()
	                    .uri(URI.create(fullUrl))
	                    .header("Content-Type", "application/x-www-form-urlencoded")
	                    .POST(HttpRequest.BodyPublishers.noBody())
	                    .build();

	            HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
	            return res.statusCode() + "\n" + res.body();

	        } catch (IOException | InterruptedException e) {
	            return "500\nError en la conexión con el servidor: " + e.getMessage();
	        }
	    }


	    public static String mostrarTexto(String baseRoot) {
	        try {
	            HttpRequest req = HttpRequest.newBuilder()
	                    .uri(URI.create(baseRoot + "/cronograma/mostrar"))
	                    .GET()
	                    .build();

	            HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
	            return res.statusCode() + "\n" + res.body();

	        } catch (IOException | InterruptedException e) {
	            return "500\nError en la conexión con el servidor: " + e.getMessage();
	        }
	    }
	}

