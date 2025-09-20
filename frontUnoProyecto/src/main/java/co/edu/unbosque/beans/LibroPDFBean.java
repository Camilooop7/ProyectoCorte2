package co.edu.unbosque.beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Named("libroPDFBean")
@RequestScoped
public class LibroPDFBean implements Serializable {
    private long id;
    private String nombre;
    private String descripcion;
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public void descargarPdf() {
        try {
            // Obtener información del libro
            obtenerInformacionLibro();

            // Descargar el PDF
            HttpRequest requestPdf = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/libropdf/pdf/" + id))
                    .build();
            HttpResponse<byte[]> responsePdf = httpClient.send(requestPdf, HttpResponse.BodyHandlers.ofByteArray());

            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().responseReset();
            facesContext.getExternalContext().setResponseContentType("application/pdf");
            facesContext.getExternalContext().setResponseHeader("Content-Disposition",
                    "attachment; filename=\"libro_" + id + ".pdf\"");
            facesContext.getExternalContext().getResponseOutputStream().write(responsePdf.body());
            facesContext.getExternalContext().getResponseOutputStream().close();
            facesContext.responseComplete();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo descargar el PDF: " + e.getMessage()));
        }
    }

    public void obtenerInformacionLibro() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/libropdf/" + id))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Parsear la respuesta JSON usando org.json.JSONObject
            JSONObject jsonObject = new JSONObject(response.body());
            this.nombre = jsonObject.optString("nombre", "Nombre no disponible");
            this.descripcion = jsonObject.optString("descripcion", "Descripción no disponible");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo obtener la información del libro: " + e.getMessage()));
        }
    }

    public StreamedContent obtenerImagen() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://localhost:8080/libropdf/imagen/" + id))
                    .build();
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            InputStream inputStream = new ByteArrayInputStream(response.body());
            return DefaultStreamedContent.builder()
                    .name("imagen_" + id + ".jpg")
                    .contentType("image/jpeg")
                    .stream(() -> inputStream)
                    .build();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo cargar la imagen: " + e.getMessage()));
            return null;
        }
    }

    // Getters y setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
