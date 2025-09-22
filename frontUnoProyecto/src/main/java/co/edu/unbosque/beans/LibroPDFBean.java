package co.edu.unbosque.beans;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
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
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Named("libroPDFBean")
@RequestScoped
public class LibroPDFBean {
	private Integer codigo;
	private String nombre;
	private String descripcion;
	private byte[] contenidoPdf;
	private StreamedContent pdfStream;
	private UploadedFile pdfFile;

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
			if (codigo == null || codigo <= 0 || isBlank(nombre) || isBlank(descripcion) || contenidoPdf == null
					|| contenidoPdf.length == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "Todos los campos y archivos son obligatorios."));
				return;
			}
			String url = "http://localhost:8081/libropdf/crear";
			String respuesta = LibroPDFService.doPostLibroPDF(url, codigo, nombre, descripcion, contenidoPdf);
			if (respuesta.startsWith("201") || respuesta.startsWith("200")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Libro creado con éxito."));
				limpiarFormulario();
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

	public void eliminarLibro() {
		try {
			if (codigo == null || codigo <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código del libro es obligatorio."));
				return;
			}
			String url = "http://localhost:8081/libropdf/eliminar";
			String respuesta = LibroPDFService.doDeleteLibroPDF(url, codigo);
			if (respuesta.startsWith("200")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Libro eliminado con éxito."));
				limpiarFormulario();
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "Error al eliminar el libro: " + respuesta));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error inesperado: " + e.getMessage()));
		}
	}

	private void limpiarFormulario() {
		this.nombre = null;
		this.descripcion = null;
		this.contenidoPdf = null;
		this.pdfStream = null;
		this.pdfFile = null;
	}

	private boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	// Getters y Setters
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

	public byte[] getContenidoPdf() {
		return contenidoPdf;
	}

	public void setContenidoPdf(byte[] contenidoPdf) {
		this.contenidoPdf = contenidoPdf;
	}

	public StreamedContent getPdfStream() {
		return pdfStream;
	}

	public UploadedFile getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(UploadedFile pdfFile) {
		this.pdfFile = pdfFile;
	}
}
