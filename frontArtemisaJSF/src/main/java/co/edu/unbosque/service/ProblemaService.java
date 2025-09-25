package co.edu.unbosque.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Servicio encargado de gestionar problemas mediante peticiones HTTP al backend.
 * Permite crear, mostrar y eliminar problemas mediante métodos estáticos.
 */
public class ProblemaService {

    /** Cliente HTTP para gestionar las solicitudes. */
    private static final HttpClient HTTP = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(6))
            .build();

    /**
     * Codifica un string para ser usado en una URL.
     * @param s el string a codificar
     * @return el string codificado en UTF-8
     */
    private static String enc(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }

    /**
     * Construye el cuerpo del formulario para crear un problema.
     * @param titulo título del problema
     * @param dificultad dificultad del problema
     * @param tema tema principal del problema
     * @param juez juez asociado al problema
     * @param link enlace al problema
     * @return cuerpo del formulario codificado
     */
    private static String formBody(String titulo, int dificultad, String tema, String juez, String link) {
        return "titulo=" + enc(titulo) +
               "&dificultad=" + dificultad +
               "&tema=" + enc(tema) +
               "&juez=" + enc(juez) +
               "&link=" + enc(link);
    }

    /**
     * Envía una solicitud para crear un nuevo problema.
     * @param urlCrear URL para la creación del problema
     * @param titulo título del problema
     * @param dificultad dificultad del problema
     * @param tema tema principal del problema
     * @param juez juez asociado al problema
     * @param link enlace al problema
     * @return respuesta del servidor con el código de estado y el cuerpo
     */
    public static String crear(String urlCrear, String titulo, int dificultad, String tema, String juez, String link) {
        String body = formBody(titulo, dificultad, tema, juez, link);

        HttpRequest reqForm = HttpRequest.newBuilder()
                .uri(URI.create(urlCrear))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "*/*")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> res = HTTP.send(reqForm, HttpResponse.BodyHandlers.ofString());
            int sc = res.statusCode();

            if (sc == 405 || sc == 415) {
                String fullUrl = urlCrear + "?" + body;
                HttpRequest fallback = HttpRequest.newBuilder()
                        .uri(URI.create(fullUrl))
                        .timeout(Duration.ofSeconds(10))
                        .header("Accept", "*/*")
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<String> res2 = HTTP.send(fallback, HttpResponse.BodyHandlers.ofString());
                return res2.statusCode() + "\n" + res2.body();
            }

            return sc + "\n" + res.body();

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
     * Solicita la lista de problemas en formato texto plano.
     * @param baseRoot URL raíz del servicio
     * @return respuesta del servidor con el código de estado y el cuerpo
     */
    public static String mostrarTexto(String baseRoot) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(baseRoot + "/problema/mostrar"))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "text/plain,*/*;q=0.8")
                    .GET()
                    .build();

            HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
            return res.statusCode() + "\n" + res.body();

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
     * Envía una solicitud para eliminar un problema por su ID.
     * @param baseRoot URL raíz del servicio
     * @param id identificador del problema a eliminar
     * @return respuesta del servidor con el código de estado y el cuerpo
     */
    public static String eliminar(String baseRoot, long id) {
        try {
            String url = baseRoot + "/problema/eliminar?id=" + id;
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "*/*")
                    .DELETE()
                    .build();

            HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
            return res.statusCode() + "\n" + res.body();

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