package co.edu.unbosque.beans;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.json.JSONObject;
import org.primefaces.PrimeFaces;

import co.edu.unbosque.service.LibroEnLineaService;

@Named("libroEnLineaBean")
@RequestScoped
public class LibroEnLineaBean {

	private Integer codigo;
	private String nombre;
	private String descripcion;
	private String link;

	public void crearLibro() {
		try {
			if (codigo == null || codigo <= 0 || isBlank(nombre) || isBlank(descripcion) || isBlank(link)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Todos los campos son obligatorios."));
				return;
			}

			String url = "http://localhost:8081/libroenlinea/crear";
			String respuesta = LibroEnLineaService.doPostLibroEnLinea(url, codigo, nombre, descripcion, link);

			if (respuesta.startsWith("201") || respuesta.startsWith("200")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Libro en línea creado con éxito."));
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

	public void obtenerInformacionLibro() {
		try {
			if (codigo == null || codigo <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código del libro es obligatorio."));
				return;
			}

			// Obtiene la información del libro desde el backend
			String respuesta = LibroEnLineaService.getLibroInfoById(codigo);
			String body = respuesta == null ? "" : respuesta.trim();

			if (body.startsWith("{")) {
				JSONObject jsonObject = new JSONObject(body);
				this.nombre = jsonObject.optString("nombre", "Nombre no disponible");
				this.descripcion = jsonObject.optString("descripcion", "Descripción no disponible");
				this.link = jsonObject.optString("link", "");

				// Si se obtuvo el enlace correctamente, ejecuta JavaScript para abrirlo
				if (!this.link.isEmpty()) {
					PrimeFaces.current().executeScript("window.open('" + this.link + "', '_blank');");
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Advertencia", "El libro no tiene un enlace válido."));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Advertencia", "El libro con código " + codigo + " no existe."));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"No se pudo obtener la información del libro: " + e.getMessage()));
		}
	}
	public void eliminarLibro() {
		try {
			if (codigo == null || codigo <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código del libro es obligatorio."));
				return;
			}
			String url = "http://localhost:8081/libroenlinea/eliminar";
			String respuesta = LibroEnLineaService.doDeleteLibroPDF(url, codigo);
			if (respuesta.startsWith("200")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Libro eliminado con éxito."));
				limpiarFormulario(); // Opcional: limpiar el formulario después de eliminar
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
		this.codigo = null;
		this.nombre = null;
		this.descripcion = null;
		this.link = null;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
