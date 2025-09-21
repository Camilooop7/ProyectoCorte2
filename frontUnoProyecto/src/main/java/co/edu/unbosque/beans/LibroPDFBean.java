package co.edu.unbosque.beans;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.json.JSONObject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import co.edu.unbosque.service.LibroPDFService;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Named("libroPDFBean")
@ViewScoped
public class LibroPDFBean implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private Integer codigo;
	private String nombre;
	private String descripcion;

	private byte[] imagen; 
	private byte[] contenidoPdf; 

	private String imageBase64; 
	private StreamedContent pdfStream; 

	private UploadedFile imagenFile;
	private UploadedFile pdfFile;

	

	public void handleFileUploadImagen(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			if (file == null || file.getContent() == null) {
				throw new IllegalArgumentException("Archivo de imagen vacío.");
			}
			String ct = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
			if (!(ct.contains("jpeg") || ct.contains("jpg") || ct.contains("png"))) {
				throw new IllegalArgumentException("Formato no soportado (solo jpg/png).");
			}

			this.imagenFile = file;
			this.imagen = file.getContent();
			this.imageBase64 = "data:" + ct + ";base64," + Base64.getEncoder().encodeToString(this.imagen);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Imagen subida: " + file.getFileName()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No se pudo cargar la imagen: " + e.getMessage()));
		}
	}

	public void handleFileUploadPdf(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			if (file == null || file.getContent() == null) {
				throw new IllegalArgumentException("Archivo PDF vacío.");
			}
			String ct = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
			if (!ct.contains("pdf")) {
				throw new IllegalArgumentException("Formato no soportado (solo PDF).");
			}

			this.pdfFile = file;
			this.contenidoPdf = file.getContent();

			this.pdfStream = DefaultStreamedContent.builder().contentType("application/pdf")
					.stream(() -> new ByteArrayInputStream(this.contenidoPdf)).build();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "PDF subido: " + file.getFileName()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al subir el PDF: " + e.getMessage()));
		}
	}

	

	public void crearLibro() {
		try {
			if (codigo == null || codigo <= 0 || isBlank(nombre) || isBlank(descripcion) || imagen == null
					|| imagen.length == 0 || contenidoPdf == null || contenidoPdf.length == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "Todos los campos y archivos son obligatorios."));
				return;
			}

			String url = "http://localhost:8081/libropdf/crear";
			String respuesta = LibroPDFService.doPostLibroPDF(url, codigo, nombre, descripcion, imagen, contenidoPdf);

			if (respuesta.startsWith("201") || respuesta.startsWith("200")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Libro creado con éxito."));
				limpiarFormulario(); // opcional
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
			if (codigo == null || codigo <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Código inválido."));
				return;
			}

			HttpRequest requestPdf = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/pdf/" + codigo)).build();

			HttpResponse<byte[]> responsePdf = LibroPDFService.httpClient.send(requestPdf,
					HttpResponse.BodyHandlers.ofByteArray());

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.responseReset();
			externalContext.setResponseContentType("application/pdf");
			externalContext.setResponseHeader("Content-Disposition",
					"attachment; filename=\"libro_" + (isBlank(nombre) ? codigo : nombre) + ".pdf\"");
			externalContext.getResponseOutputStream().write(responsePdf.body());
			facesContext.responseComplete();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No se pudo descargar el PDF: " + e.getMessage()));
		}
	}

	public void obtenerInformacionLibro() {
		try {
			if (codigo == null || codigo <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código del libro es obligatorio."));
				return;
			}

			HttpRequest request = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/" + codigo)).build();

			HttpResponse<String> response = LibroPDFService.httpClient.send(request,
					HttpResponse.BodyHandlers.ofString());

			String body = response.body() == null ? "" : response.body().trim();
			if (body.startsWith("{")) {
				JSONObject jsonObject = new JSONObject(body);
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
		descargarPdf();
	}

	

	private void limpiarFormulario() {
		this.nombre = null;
		this.descripcion = null;

		this.imagen = null;
		this.contenidoPdf = null;

		this.imageBase64 = null;
		this.pdfStream = null;

		this.imagenFile = null;
		this.pdfFile = null;
	}

	private boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
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

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	public StreamedContent getPdfStream() {
		return pdfStream;
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
