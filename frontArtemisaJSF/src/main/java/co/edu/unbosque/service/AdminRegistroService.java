package co.edu.unbosque.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Servicio para el registro de administradores.
 * Permite enviar solicitudes HTTP para crear nuevos administradores en el sistema.
 */
public class AdminRegistroService {

    /** Cliente HTTP reutilizado para las peticiones. */
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5)).build();

    /**
     * Envía una solicitud POST para registrar un nuevo administrador.
     * Los datos del administrador se envían como parámetros en la URL.
     * 
     * @param url URL base para el registro del administrador
     * @param nombre nombre del administrador
     * @param documento documento de identificación
     * @param correo correo electrónico
     * @param contrasena contraseña
     * @param rol rol asignado
     * @param cargo cargo del administrador
     * @return respuesta del servidor con el código de estado y el cuerpo de la respuesta
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