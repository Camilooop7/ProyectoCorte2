package co.edu.unbosque.beans;

import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.primefaces.PrimeFaces;

import co.edu.unbosque.service.LibroEnLineaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped; 
import jakarta.inject.Named;

@Named("libroEnLineaBean")
@ViewScoped
public class LibroEnLineaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private String nombre;
	private String descripcion;
	private String link;

	public static class LibroEnLineaItem implements Serializable {
		private static final long serialVersionUID = 1L;
		private int codigo;
		private String nombre;
		private String descripcion;
		private String link;

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

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}
	}

	private final List<LibroEnLineaItem> librosOnline = new ArrayList<>();

	@PostConstruct
	public void init() {
		cargarLibrosOnline();
	}

	public List<LibroEnLineaItem> getLibrosOnline() {
		return librosOnline;
	}

	public void crearLibro() {
		try {
			String url = "http://localhost:8081/libroenlinea/crear";
			LibroEnLineaService.doPostLibroEnLinea(url, (codigo == null ? 0 : codigo), (nombre == null ? "" : nombre),
					(descripcion == null ? "" : descripcion), (link == null ? "" : link));
			limpiarFormulario();
			cargarLibrosOnline();
			addMsg(FacesMessage.SEVERITY_INFO, "Creado", "Libro en línea creado");
		} catch (Exception e) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Error creando libro", e.getMessage());
		}
	}

	public void obtenerInformacionLibro() {
		try {
			if (codigo == null || codigo <= 0) {
				addMsg(FacesMessage.SEVERITY_WARN, "Código requerido", "Ingresa un ID válido.");
				return;
			}
			String body = LibroEnLineaService.getLibroInfoById(codigo);
			if (body != null && body.trim().startsWith("{")) {
				JSONObject o = new JSONObject(body.trim());
				this.nombre = o.optString("nombre", nombre);
				this.descripcion = o.optString("descripcion", descripcion);
				this.link = o.optString("link", link);

				if (this.link != null && !this.link.isBlank()) {
					if (PrimeFaces.current().isAjaxRequest()) {
						PrimeFaces.current().executeScript("window.open('" + this.link + "', '_blank');");
					} else {
						FacesContext.getCurrentInstance().getExternalContext().redirect(this.link);
					}
				} else {
					addMsg(FacesMessage.SEVERITY_WARN, "Sin enlace", "Este libro no tiene link.");
				}
			} else {
				addMsg(FacesMessage.SEVERITY_WARN, "No encontrado", "No se encontró el libro " + codigo);
			}
		} catch (Exception e) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Error obteniendo libro", e.getMessage());
		}
	}

	public void eliminarLibro() {
		try {
			if (codigo == null || codigo <= 0) {
				addMsg(FacesMessage.SEVERITY_WARN, "Código requerido", "Ingresa un ID válido.");
				return;
			}
			String url = "http://localhost:8081/libroenlinea/eliminar";
			LibroEnLineaService.doDeleteLibroPDF(url, codigo);
			cargarLibrosOnline();
			addMsg(FacesMessage.SEVERITY_INFO, "Eliminado", "Libro " + codigo + " eliminado");
		} catch (Exception e) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Error eliminando", e.getMessage());
		}
	}

	public void cargarLibrosOnline() {
		try {
			HttpRequest req = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8081/libroenlinea"))
					.build();

			HttpResponse<String> resp = LibroEnLineaService.httpClient.send(req, HttpResponse.BodyHandlers.ofString());

			librosOnline.clear();
			String body = (resp.body() == null ? "[]" : resp.body().trim());
			if (!body.startsWith("[")) {
				addMsg(FacesMessage.SEVERITY_WARN, "Respuesta inválida", "Se esperaba un arreglo JSON");
				return;
			}

			JSONArray arr = new JSONArray(body);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				LibroEnLineaItem it = new LibroEnLineaItem();
				it.setCodigo(o.optInt("codigo"));
				it.setNombre(o.optString("nombre"));
				it.setDescripcion(o.optString("descripcion"));
				it.setLink(o.optString("link"));
				librosOnline.add(it);
			}
		} catch (Exception e) {
			addMsg(FacesMessage.SEVERITY_ERROR, "No se pudo cargar", e.getMessage());
		}
	}

	private void limpiarFormulario() {
		this.codigo = null;
		this.nombre = null;
		this.descripcion = null;
		this.link = null;
	}

	private void addMsg(FacesMessage.Severity sev, String sum, String det) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, sum, det));
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
