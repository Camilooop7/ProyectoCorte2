package co.edu.unbosque.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Servicio encargado de gestionar libros PDF mediante peticiones HTTP al backend.
 * Permite crear, consultar y eliminar libros PDF a través de métodos estáticos.
 */
public class LibroPDFService {

    /** Cliente HTTP utilizado para enviar las solicitudes. */
    public static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(30)).build();

    /**
     * Envía una solicitud POST al backend para crear un libro PDF.
     *
     * @param url         URL del endpoint.
     * @param codigo      Código del libro.
     * @param nombre      Nombre del libro.
     * @param descripcion Descripción del libro.
     * @param archivoPdf  Contenido del PDF en bytes.
     * @return String con el código de respuesta y mensaje del servidor.
     */
    public static String doPostLibroPDF(String url, int codigo, String nombre, String descripcion, byte[] archivoPdf) {
        try {
            // Boundary para multipart/form-data
            String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";

            // Construye el cuerpo de la solicitud
            String requestBody = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"codigo\"\r\n\r\n"
                    + codigo + "\r\n" + "--" + boundary + "\r\n"
                    + "Content-Disposition: form-data; name=\"nombre\"\r\n\r\n" + nombre + "\r\n" + "--" + boundary
                    + "\r\n" + "Content-Disposition: form-data; name=\"descripcion\"\r\n\r\n" + descripcion + "\r\n"
                    + "--" + boundary + "\r\n"
                    + "Content-Disposition: form-data; name=\"archivoPdf\"; filename=\"libro_" + codigo + ".pdf\"\r\n"
                    + "Content-Type: application/pdf\r\n\r\n";

            String footer = "\r\n--" + boundary + "--\r\n";

            // Combina el cuerpo y el archivo PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            outputStream.write(archivoPdf);
            outputStream.write(footer.getBytes(StandardCharsets.UTF_8));

            byte[] body = outputStream.toByteArray();

            // Crea la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(body)).build();

            // Envía la solicitud y obtiene la respuesta
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() + "\n" + response.body();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "500\nError al enviar el libro PDF: " + e.getMessage();
        }
    }

    /**
     * Obtiene el contenido de un PDF desde el backend.
     *
     * @param id Código del libro.
     * @return byte[] con el contenido del PDF.
     * @throws IOException          Si hay un error en la conexión.
     * @throws InterruptedException Si la solicitud es interrumpida.
     */
    public static byte[] getPdfContentById(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8081/libropdf/pdf/" + id))
                .build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        return response.body();
    }

    /**
     * Obtiene la información de un libro en formato JSON desde el backend.
     *
     * @param id Código del libro.
     * @return String con la respuesta JSON del servidor.
     * @throws IOException          Si hay un error en la conexión.
     * @throws InterruptedException Si la solicitud es interrumpida.
     */
    public static String getLibroInfoById(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8081/libropdf/" + id))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * Envía una solicitud DELETE al backend para eliminar un libro PDF por su código.
     *
     * @param url    URL del endpoint.
     * @param codigo Código del libro a eliminar.
     * @return String con el código de respuesta y mensaje del servidor.
     */
    public static String doDeleteLibroPDF(String url, int codigo) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + "/" + codigo)).DELETE().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() + "\n" + response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "500\nError al eliminar el libro PDF: " + e.getMessage();
        }
    }
}