package co.edu.unbosque.beans;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.json.JSONObject;
import org.primefaces.PrimeFaces;
import co.edu.unbosque.service.LibroEnLineaService;

/**
 * Bean de solicitud para gestionar libros en línea.
 */
@Named("libroEnLineaBean")
@RequestScoped
public class LibroEnLineaBean {

	/** Código del libro. */
	private Integer codigo;

	/** Nombre del libro. */
	private String nombre;

	/** Descripción del libro. */
	private String descripcion;

	/** Enlace del libro. */
	private String link;

	/**
	 * Crea un nuevo libro en línea.
	 */
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

	/**
	 * Obtiene la información de un libro por su código.
	 */
	public void obtenerInformacionLibro() {
		try {
			if (codigo == null || codigo <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El código del libro es obligatorio."));
				return;
			}
			String respuesta = LibroEnLineaService.getLibroInfoById(codigo);
			String body = respuesta == null ? "" : respuesta.trim();
			if (body.startsWith("{")) {
				JSONObject jsonObject = new JSONObject(body);
				this.nombre = jsonObject.optString("nombre", "Nombre no disponible");
				this.descripcion = jsonObject.optString("descripcion", "Descripción no disponible");
				this.link = jsonObject.optString("link", "");
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

	/**
	 * Elimina un libro por su código.
	 */
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

	/**
	 * Limpia los campos del formulario.
	 */
	private void limpiarFormulario() {
		this.codigo = null;
		this.nombre = null;
		this.descripcion = null;
		this.link = null;
	}

	/**
	 * Verifica si una cadena es nula o vacía.
	 * 
	 * @param s Cadena a verificar.
	 * @return {@code true} si es nula o vacía.
	 */
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
