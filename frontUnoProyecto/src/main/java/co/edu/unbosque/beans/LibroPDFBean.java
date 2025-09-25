package co.edu.unbosque.beans;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.io.ByteArrayInputStream;
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
@RequestScoped
public class LibroPDFBean {

	private Integer codigo;
	private String nombre;
	private String descripcion;
	private byte[] contenidoPdf;
	private StreamedContent pdfStream;
	private UploadedFile pdfFile;


	public static class LibroPDFItem {
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
			this.contenidoPdf = file.getContent();
			this.pdfStream = DefaultStreamedContent.builder().contentType("application/pdf")
					.stream(() -> new ByteArrayInputStream(this.contenidoPdf)).build();
		} catch (Exception e) {
			
		}
	}

	public void crearLibro() {
		try {
			String url = "http://localhost:8081/libropdf/crear";
			byte[] data = (contenidoPdf == null) ? new byte[0] : contenidoPdf;
			int cod = (codigo == null) ? 0 : codigo;
			String nom = nombre == null ? "" : nombre;
			String des = descripcion == null ? "" : descripcion;
			LibroPDFService.doPostLibroPDF(url, cod, nom, des, data);
			limpiarFormulario();
			cargarLibrosPDF();
		} catch (Exception e) {
			 }
	}

	public void obtenerInformacionLibro() {
		try {
			if (codigo == null || codigo <= 0)
				return;
			HttpRequest request = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/" + codigo)).build();
			HttpResponse<String> response = LibroPDFService.httpClient.send(request,
					HttpResponse.BodyHandlers.ofString());
			String body = response.body() == null ? "" : response.body().trim();
			if (body.startsWith("{")) {
				JSONObject json = new JSONObject(body);
				this.nombre = json.optString("nombre", this.nombre);
				this.descripcion = json.optString("descripcion", this.descripcion);
				descargarPdf(); 
			}
		} catch (Exception e) {
 }
	}

	public void descargarPdf() {
		try {
			if (codigo == null || codigo <= 0)
				return;
			HttpRequest requestPdf = HttpRequest.newBuilder().GET()
					.uri(URI.create("http://localhost:8081/libropdf/pdf/" + codigo)).build();
			HttpResponse<byte[]> responsePdf = LibroPDFService.httpClient.send(requestPdf,
					HttpResponse.BodyHandlers.ofByteArray());
			FacesContext fc = FacesContext.getCurrentInstance();
			var ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"libro_"
					+ (nombre == null || nombre.isBlank() ? codigo : nombre) + ".pdf\"");
			ec.getResponseOutputStream().write(responsePdf.body());
			fc.responseComplete();
		} catch (Exception e) {
	 }
	}

	public void eliminarLibro() {
		try {
			if (codigo == null || codigo <= 0)
				return;
			String url = "http://localhost:8081/libropdf/eliminar";
			LibroPDFService.doDeleteLibroPDF(url, codigo);
			cargarLibrosPDF();
		} catch (Exception e) {
			 }
	}

	private void limpiarFormulario() {
		this.nombre = null;
		this.descripcion = null;
		this.contenidoPdf = null;
		this.pdfStream = null;
		this.pdfFile = null;
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
