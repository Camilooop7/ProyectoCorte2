package co.edu.unbosque.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class ProblemaService {
	   private static final HttpClient HTTP = HttpClient.newBuilder()
	            .version(HttpClient.Version.HTTP_1_1)
	            .connectTimeout(Duration.ofSeconds(5))
	            .build();

	    private static String enc(String s) {
	        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
	    }


	    public static String crear(String baseUrlEj,
	                               String titulo,
	                               String dificultad, 
	                               String tema,
	                               String juez,
	                               String link) {
	        try {
	            String fullUrl = baseUrlEj
	                    + "?titulo=" + enc(titulo)
	                    + "&dificultad=" + enc(dificultad)
	                    + "&tema=" + enc(tema)
	                    + "&juez=" + enc(juez)
	                    + "&link=" + enc(link);

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


	    public static String mostrarTexto(String baseRootEj) {
	        try {
	            HttpRequest req = HttpRequest.newBuilder()
	                    .uri(URI.create(baseRootEj + "/problema/mostrar"))
	                    .GET()
	                    .build();

	            HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
	            return res.statusCode() + "\n" + res.body();

	        } catch (IOException | InterruptedException e) {
	            return "500\nError en la conexión con el servidor: " + e.getMessage();
	        }
	    }

	    
	    public static String eliminar(String baseRootEj, Long id) {
	        try {
	            String fullUrl = baseRootEj + "/problema/eliminar?id=" + id;

	            HttpRequest req = HttpRequest.newBuilder()
	                    .uri(URI.create(fullUrl))
	                    .DELETE()
	                    .build();

	            HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
	            return res.statusCode() + "\n" + res.body();

	        } catch (IOException | InterruptedException e) {
	            return "500\nError en la conexión con el servidor: " + e.getMessage();
	        }
	    }
	}

