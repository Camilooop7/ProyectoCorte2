package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import co.edu.unbosque.service.ProblemaService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Bean de vista para gestionar problemas de programación.
 */
@Named("problemaBean")
@ViewScoped
public class ProblemaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Título del problema. */
	private String titulo;

	/** Dificultad del problema. */
	private int dificultad;

	/** Tema del problema. */
	private String tema;

	/** Juez del problema. */
	private String juez;

	/** Enlace del problema. */
	private String link;

	/** ID del problema a eliminar. */
	private Long deleteId;

	/** Lista de problemas en formato texto. */
	private List<String> listado = new ArrayList<>();

	/** Lista de problemas parseados. */
	private List<ProblemaRow> lista = new ArrayList<>();

	/** URL base para crear problemas. */
	private static final String BASE_CREATE = "http://localhost:8081/problema/crear";

	/** URL base del servicio. */
	private static final String BASE_ROOT = "http://localhost:8081";

	/**
	 * Crea un nuevo problema.
	 */
	public void crear() {
		try {
			String resp = ProblemaService.crear(BASE_CREATE, titulo, dificultad, tema, juez, link);
			String[] data = resp.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String body = (data.length > 1) ? data[1] : "Sin respuesta";
			if ("201".equals(code) || "200".equals(code)) {
				showInfo("Problema creado con éxito");
				limpiar();
				listarProblemas();
			} else {
				showError(body);
			}
		} catch (Exception e) {
			showError("Error al crear: " + e.getMessage());
		}
	}

	/**
	 * Lista todos los problemas desde el backend.
	 */
	public void listarProblemas() {
		listado.clear();
		lista.clear();
		try {
			String resp = ProblemaService.mostrarTexto(BASE_ROOT);
			String[] data = resp.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String body = (data.length > 1) ? data[1] : "";
			if ("202".equals(code) || "200".equals(code)) {
				if (body != null && !body.isBlank()) {
					for (String raw : body.split("\\R")) {
						if (raw.isBlank())
							continue;
						listado.add(raw.trim());
						ProblemaRow row = parseLinea(raw);
						if (row != null)
							lista.add(row);
					}
					showInfo("Se cargaron " + lista.size() + " problemas.");
				} else {
					showWarn("No hay problemas para mostrar.");
				}
			} else {
				showError(body.isBlank() ? "Error al listar problemas" : body);
			}
		} catch (Exception e) {
			showError("Error al listar: " + e.getMessage());
		}
	}

	/**
	 * Elimina un problema por su ID.
	 */
	public void eliminarById() {
		if (deleteId == null || deleteId <= 0) {
			showWarn("Ingresa un ID válido para eliminar.");
			return;
		}
		eliminar(deleteId);
		deleteId = null;
	}

	/**
	 * Elimina un problema por su ID.
	 * 
	 * @param id ID del problema a eliminar.
	 */
	private void eliminar(Long id) {
		try {
			String resp = ProblemaService.eliminar(BASE_ROOT, id);
			String[] data = resp.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String body = (data.length > 1) ? data[1] : "";
			if ("200".equals(code)) {
				showInfo(body.isBlank() ? "Problema eliminado con éxito" : body);
				listarProblemas();
			} else {
				showWarn(body.isBlank() ? "No se pudo eliminar (¿ID inexistente?)." : body);
			}
		} catch (Exception e) {
			showError("Error al eliminar: " + e.getMessage());
		}
	}

	/**
	 * Parsea una línea de texto en un objeto {@link ProblemaRow}.
	 * 
	 * @param raw Línea de texto a parsear.
	 * @return Objeto {@link ProblemaRow} o {@code null} si no es válido.
	 */
	private ProblemaRow parseLinea(String raw) {
		if (raw == null)
			return null;
		String s = raw.trim();
		if (s.toLowerCase().startsWith("admin:")) {
			s = s.substring(6).trim();
		}
		Pattern p = Pattern.compile(
				"(?i)id\\s*=\\s*(\\d+).*?titulo\\s*=\\s*([^,\\]]+).*?dificultad\\s*=\\s*(\\d+).*?tema\\s*=\\s*([^,\\]]*).*?juez\\s*=\\s*([^,\\]]*).*?link\\s*=\\s*([^,\\]]+)");
		Matcher m = p.matcher(s);
		if (m.find()) {
			Long id = tryLong(m.group(1));
			String titulo = safe(m.group(2));
			Integer dificultad = tryInt(m.group(3));
			String tema = safe(m.group(4));
			String juez = safe(m.group(5));
			String link = safe(m.group(6));
			return new ProblemaRow(id, titulo, dificultad, tema, juez, link);
		}
		return null;
	}

	/**
	 * Convierte una cadena a {@link Long} o devuelve {@code null}.
	 * 
	 * @param v Cadena a convertir.
	 * @return {@link Long} o {@code null} si no es válido.
	 */
	private Long tryLong(String v) {
		try {
			return Long.parseLong(v.trim());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Convierte una cadena a {@link Integer} o devuelve {@code null}.
	 * 
	 * @param v Cadena a convertir.
	 * @return {@link Integer} o {@code null} si no es válido.
	 */
	private Integer tryInt(String v) {
		try {
			return Integer.parseInt(v.trim());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Limpia una cadena de texto.
	 * 
	 * @param v Cadena a limpiar.
	 * @return Cadena limpia o cadena vacía si es nula.
	 */
	private String safe(String v) {
		return v == null ? "" : v.trim();
	}

	/**
	 * Limpia los campos del formulario.
	 */
	private void limpiar() {
		titulo = "";
		dificultad = 0;
		tema = "";
		juez = "";
		link = "";
	}

	/**
	 * Muestra un mensaje de información.
	 * 
	 * @param msg Mensaje a mostrar.
	 */
	private void showInfo(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", msg));
	}

	/**
	 * Muestra un mensaje de advertencia.
	 * 
	 * @param msg Mensaje a mostrar.
	 */
	private void showWarn(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
	}

	/**
	 * Muestra un mensaje de error.
	 * 
	 * @param msg Mensaje a mostrar.
	 */
	private void showError(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
	}

	// Getters y Setters
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getJuez() {
		return juez;
	}

	public void setJuez(String juez) {
		this.juez = juez;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Long getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(Long deleteId) {
		this.deleteId = deleteId;
	}

	public List<String> getListado() {
		return listado;
	}

	public void setListado(List<String> listado) {
		this.listado = listado;
	}

	public List<ProblemaRow> getLista() {
		return lista;
	}

	public void setLista(List<ProblemaRow> lista) {
		this.lista = lista;
	}

	/**
	 * Clase interna que representa un problema.
	 */
	public static class ProblemaRow {
		private Long id;
		private String titulo;
		private Integer dificultad;
		private String tema;
		private String juez;
		private String link;

		public ProblemaRow() {
		}

		public ProblemaRow(Long id, String titulo, Integer dificultad, String tema, String juez, String link) {
			this.id = id;
			this.titulo = titulo;
			this.dificultad = dificultad;
			this.tema = tema;
			this.juez = juez;
			this.link = link;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public Integer getDificultad() {
			return dificultad;
		}

		public void setDificultad(Integer dificultad) {
			this.dificultad = dificultad;
		}

		public String getTema() {
			return tema;
		}

		public void setTema(String tema) {
			this.tema = tema;
		}

		public String getJuez() {
			return juez;
		}

		public void setJuez(String juez) {
			this.juez = juez;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}
	}
}
