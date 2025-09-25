package co.edu.unbosque.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Servicio para la gestión de cronogramas.
 * Provee métodos para crear, mostrar y eliminar cronogramas a través de solicitudes HTTP.
 */
public class CronogramaService {

    /** Cliente HTTP reutilizado para las peticiones. */
    private static final HttpClient HTTP = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5)).build();

    /**
     * Codifica un string para ser usado en una URL.
     * @param s el string a codificar
     * @return el string codificado en UTF-8
     */
    private static String enc(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }

    /**
     * Construye el cuerpo del formulario para crear un cronograma.
     * @param nombre nombre del cronograma
     * @param link enlace del cronograma
     * @param fechaISO fecha en formato ISO (yyyy-MM-dd)
     * @return cuerpo del formulario codificado
     */
    private static String formBody(String nombre, String link, String fechaISO) {
        return "nombre=" + enc(nombre) + "&link=" + enc(link) + "&fecha=" + enc(fechaISO);
    }

    /**
     * Envía una solicitud para crear un nuevo cronograma.
     * @param urlCrear URL para la creación del cronograma
     * @param nombre nombre del cronograma
     * @param link enlace del cronograma
     * @param fechaISO fecha en formato ISO (yyyy-MM-dd)
     * @return respuesta del servidor con el código de estado y el cuerpo
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
     * Solicita la lista de cronogramas en formato texto plano.
     * @param baseRoot URL raíz del servicio
     * @return respuesta del servidor con el código de estado y el cuerpo
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
     * Envía una solicitud para eliminar un cronograma por su ID.
     * @param baseRoot URL raíz del servicio
     * @param id identificador del cronograma a eliminar
     * @return respuesta del servidor con el código de estado y el cuerpo
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