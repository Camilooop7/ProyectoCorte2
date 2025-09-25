package co.edu.unbosque.beans;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped; // <-- CAMBIO: ViewScoped de JSF
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;

import java.io.ByteArrayInputStream;
import java.io.Serializable; // <-- necesario para @ViewScoped
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import co.edu.unbosque.service.LibroPDFService;

@Named("libroPDFBean")
@ViewScoped
public class LibroPDFBean implements Serializable {

	private static final long serialVersionUID = 1L; 

	private Integer codigo;
	private String nombre;
	private String descripcion;

	private byte[] contenidoPdf; 
	private StreamedContent pdfStream;
	private transient UploadedFile pdfFile; 

	public static class LibroPDFItem implements Serializable {
		private static final long serialVersionUID = 1L;
		private int codigo;
		private String nombre;
		private String descripcion;

		public int getCodigo() {
			return codigo;
		}

		public String getNombre() {
			return nombre;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setCodigo(int codigo) {
			this.codigo = codigo;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
	}

	private final List<LibroPDFItem> librosPDF = new ArrayList<>();

	public List<LibroPDFItem> getLibrosPDF() {
		return librosPDF;
	}

	public void cargarLibrosPDF() {
		try {
			HttpRequest req = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8081/libropdf")).build();
			HttpResponse<String> resp = LibroPDFService.httpClient.send(req, HttpResponse.BodyHandlers.ofString());

			librosPDF.clear();
			String body = resp.body() == null ? "[]" : resp.body().trim();
			if (!body.startsWith("["))
				return;

			JSONArray arr = new JSONArray(body);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				LibroPDFItem it = new LibroPDFItem();
				it.setCodigo(o.optInt("codigo"));
				it.setNombre(o.optString("nombre"));
				it.setDescripcion(o.optString("descripcion"));
				librosPDF.add(it);
			}
		} catch (Exception e) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Error cargando", e.getMessage());
		}
	}

	public void handleFileUploadPdf(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			if (file == null || file.getContent() == null)
				throw new IllegalArgumentException("Archivo PDF vacío.");
			String ct = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
			if (!ct.contains("pdf"))
				throw new IllegalArgumentException("Formato no soportado (solo PDF).");

			this.pdfFile = file;
			this.contenidoPdf = file.getContent(); // <-- se mantiene gracias a @ViewScoped
			this.pdfStream = DefaultStreamedContent.builder().contentType("application/pdf")
					.stream(() -> new ByteArrayInputStream(this.contenidoPdf)).build();

			addMsg(FacesMessage.SEVERITY_INFO, "PDF cargado", "Tamaño: " + this.contenidoPdf.length + " bytes");
		} catch (Exception e) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Error subiendo PDF", e.getMessage());
		}
	}

	public void crearLibro() {
		try {
			if (codigo == null || codigo <= 0) {
				addMsg(FacesMessage.SEVERITY_WARN, "Código requerido", "Ingresa un código válido.");
				return;
			}
			if (nombre == null || nombre.isBlank()) {
				addMsg(FacesMessage.SEVERITY_WARN, "Nombre requerido", "Ingresa el nombre del libro.");
				return;
			}
			if (contenidoPdf == null || contenidoPdf.length == 0) {
				addMsg(FacesMessage.SEVERITY_ERROR, "PDF no cargado", "Sube un archivo PDF antes de crear.");
				return;
			}

			String url = "http://localhost:8081/libropdf/crear";
			int cod = codigo;
			String nom = nombre;
			String des = (descripcion == null) ? "" : descripcion;

			LibroPDFService.doPostLibroPDF(url, cod, nom, des, contenidoPdf);

			addMsg(FacesMessage.SEVERITY_INFO, "Libro creado", "Se creó el libro PDF correctamente.");
			limpiarFormulario();
			cargarLibrosPDF();
		} catch (Exception e) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Error creando libro", e.getMessage());
		}
	}

	public void obtenerInformacionLibro() {
		try {
			if (codigo == null || codigo <= 0) {
				addMsg(FacesMessage.SEVERITY_WARN, "Código inválido", "Ingresa un ID de libro válido.");
				return;
			}
			HttpRequest request = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/" + codigo)).build();
			HttpResponse<String> response = LibroPDFService.httpClient.send(request,
					HttpResponse.BodyHandlers.ofString());

			int status = response.statusCode();
			if (status != 200) {
				addMsg(FacesMessage.SEVERITY_ERROR, "No se encontró el libro",
						"HTTP " + status + " al consultar metadatos (ID " + codigo + ").");
				return;
			}
			String body = response.body() == null ? "" : response.body().trim();
			if (!body.startsWith("{")) {
				addMsg(FacesMessage.SEVERITY_ERROR, "Respuesta inesperada",
						"El backend no devolvió JSON para el libro " + codigo + ".");
				return;
			}

			JSONObject json = new JSONObject(body);
			this.nombre = json.optString("nombre", String.valueOf(codigo));
			this.descripcion = json.optString("descripcion", "");

			descargarPdf();
		} catch (Exception e) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Error consultando libro", e.getMessage());
		}
	}

	public void descargarPdf() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (codigo == null || codigo <= 0) {
				addMsg(FacesMessage.SEVERITY_WARN, "Código inválido", "Ingresa un ID de libro válido.");
				return;
			}
			HttpRequest requestPdf = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/pdf/" + codigo)).build();

			HttpResponse<byte[]> resp = LibroPDFService.httpClient.send(requestPdf,
					HttpResponse.BodyHandlers.ofByteArray());

			int status = resp.statusCode();
			if (status != 200) {
				addMsg(FacesMessage.SEVERITY_ERROR, "No se pudo descargar",
						"HTTP " + status + " al descargar PDF (ID " + codigo + ").");
				return;
			}

			byte[] bytes = resp.body() != null ? resp.body() : new byte[0];
			if (bytes.length == 0) {
				addMsg(FacesMessage.SEVERITY_ERROR, "PDF vacío",
						"El servidor devolvió 0 bytes para el ID " + codigo + ".");
				return;
			}

			if (!parecePdf(bytes)) {
				addMsg(FacesMessage.SEVERITY_WARN, "Cabecera no estándar",
						"El archivo no inicia con %PDF-, se intentará descargar igualmente.");
			}

			var ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			String safeName = (nombre == null || nombre.isBlank()) ? String.valueOf(codigo)
					: nombre.replaceAll("[\\r\\n\"\\\\/:*?<>|]+", "_");
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"libro_" + safeName + ".pdf\"");
			ec.setResponseHeader("Content-Length", String.valueOf(bytes.length));
			ec.setResponseHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");

			try (var out = ec.getResponseOutputStream()) {
				out.write(bytes);
				out.flush();
			}
			fc.responseComplete();
		} catch (Exception e) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Excepción descargando PDF", e.getMessage());
		}
	}

	private boolean parecePdf(byte[] b) {
		if (b.length < 4)
			return false;
		int lim = Math.min(16, b.length - 3);
		for (int i = 0; i < lim; i++) {
			if (b[i] == '%' && b[i + 1] == 'P' && b[i + 2] == 'D' && b[i + 3] == 'F')
				return true;
		}
		return false;
	}

	private void limpiarFormulario() {
		this.nombre = null;
		this.descripcion = null;
		this.contenidoPdf = null;
		this.pdfStream = null;
		this.pdfFile = null;
	}

	private void addMsg(FacesMessage.Severity sev, String sum, String det) {
		try {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, sum, det));
		} catch (Exception ignore) {
		}
	}

	// getters / setters
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
