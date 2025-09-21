package co.edu.unbosque.beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
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

	private int codigo;
	private String nombre;
	private String descripcion;

	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(10)).build();

	public void descargarPdf() {
		try {
			HttpRequest requestPdf = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/pdf/" + codigo)).build();
			HttpResponse<byte[]> responsePdf = httpClient.send(requestPdf, HttpResponse.BodyHandlers.ofByteArray());

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.responseReset();
			externalContext.setResponseContentType("application/pdf");
			externalContext.setResponseHeader("Content-Disposition",
					"attachment; filename=\"libro" + nombre + ".pdf\"");
			externalContext.getResponseOutputStream().write(responsePdf.body());
			facesContext.responseComplete();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No se pudo descargar el PDF: " + e.getMessage()));
		}
	}

	public void obtenerInformacionLibro() {
		try {

			if (codigo == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El ID del libro es obligatorio."));
				return;
			}

			HttpRequest request = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/" + codigo)).build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			System.out.println("Respuesta del backend: " + response.body());

			if (response.body().trim().startsWith("{")) {
				JSONObject jsonObject = new JSONObject(response.body());
				this.nombre = jsonObject.optString("nombre", "Nombre no disponible");
				this.descripcion = jsonObject.optString("descripcion", "Descripción no disponible");
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Advertencia", "El libro con ID " + codigo + " no existe."));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No se pudo obtener la información del libro: " + e.getMessage()));
		}
		descargarPdf();
	}

	public StreamedContent obtenerImagen() {
		try {
			if (codigo == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El ID del libro es obligatorio."));
				return null;
			}
			HttpRequest request = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/imagen/" + codigo)).build();
			HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

			InputStream inputStream = new ByteArrayInputStream(response.body());
			return DefaultStreamedContent.builder().name("imagen_" + codigo + ".jpg").contentType("image/jpeg")
					.stream(() -> inputStream).build();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No se pudo cargar la imagen: " + e.getMessage()));
			return null;
		}
	}

	// Getters y setters

	public String getNombre() {
		return nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}
}