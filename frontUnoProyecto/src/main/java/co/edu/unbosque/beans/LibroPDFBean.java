package co.edu.unbosque.beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import org.json.JSONObject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import co.edu.unbosque.service.LibroPDFService;

import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Named("libroPDFBean")
@RequestScoped
public class LibroPDFBean implements Serializable {

	private int codigo;
	private String nombre;
	private String descripcion;
	private byte[] imagen;
	private byte[] contenidoPdf;
	private UploadedFile imagenFile;
	private UploadedFile pdfFile;

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		String fileName = file.getFileName().toLowerCase();

		if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
			this.imagenFile = file;
			this.imagen = file.getContent();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Imagen subida: " + fileName));
		} else if (fileName.endsWith(".pdf")) {
			this.pdfFile = file;
			this.contenidoPdf = file.getContent();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "PDF subido: " + fileName));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Tipo de archivo no soportado: " + fileName));
		}
	}

	public void crearLibro() {
		if (this.codigo == 0 || this.nombre == null || this.descripcion == null || this.imagen == null
				|| this.contenidoPdf == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Todos los campos son obligatorios."));
			return;
		}

		try {
			String url = "http://localhost:8081/libropdf/crear";
			String respuesta = LibroPDFService.doPostLibroPDF(url, this.codigo, this.nombre, this.descripcion,
					this.imagen, this.contenidoPdf);

			if (respuesta.startsWith("201") || respuesta.startsWith("200")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Libro creado con éxito."));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "Error al crear el libro: " + respuesta));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error inesperado: " + e.getMessage()));
		}
	}

	public void descargarPdf() {
		try {
			HttpRequest requestPdf = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/pdf/" + codigo)).build();
			HttpResponse<byte[]> responsePdf = LibroPDFService.httpClient.send(requestPdf,
					HttpResponse.BodyHandlers.ofByteArray());

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.responseReset();
			externalContext.setResponseContentType("application/pdf");
			externalContext.setResponseHeader("Content-Disposition",
					"attachment; filename=\"libro_" + nombre + ".pdf\"");
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
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código del libro es obligatorio."));
				return;
			}

			HttpRequest request = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/" + codigo)).build();
			HttpResponse<String> response = LibroPDFService.httpClient.send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.body().trim().startsWith("{")) {
				JSONObject jsonObject = new JSONObject(response.body());
				this.nombre = jsonObject.optString("nombre", "Nombre no disponible");
				this.descripcion = jsonObject.optString("descripcion", "Descripción no disponible");
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Advertencia", "El libro con código " + codigo + " no existe."));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No se pudo obtener la información del libro: " + e.getMessage()));
		}
	}

	public void handleFileUploadImagen(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			if (file != null && file.getContent() != null) {
				this.imagen = file.getContent();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Imagen subida: " + file.getFileName()));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Error al subir la imagen: " + e.getMessage()));
		}
	}

	public void handleFileUploadPdf(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			if (file != null && file.getContent() != null) {
				this.contenidoPdf = file.getContent();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "PDF subido: " + file.getFileName()));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al subir el PDF: " + e.getMessage()));
		}
	}

	// Getters y Setters
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public byte[] getContenidoPdf() {
		return contenidoPdf;
	}

	public void setContenidoPdf(byte[] contenidoPdf) {
		this.contenidoPdf = contenidoPdf;
	}

	public UploadedFile getImagenFile() {
		return imagenFile;
	}

	public void setImagenFile(UploadedFile imagenFile) {
		this.imagenFile = imagenFile;
	}

	public UploadedFile getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(UploadedFile pdfFile) {
		this.pdfFile = pdfFile;
	}
}
