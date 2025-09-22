package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.service.ProblemaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("problemaBean")
@RequestScoped
public class ProblemaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String titulo;
	private String dificultad;
	private String tema;
	private String juez;
	private String link;

	private List<String> listado = new ArrayList<>();

	private static final String BASE_CREATE = "http://localhost:8081/problema/crear";
	private static final String BASE_ROOT = "http://localhost:8081";

	// ================= Acciones CRUD =================

	public void doCreate() {
		try {
			if (isBlank(titulo) || isBlank(dificultad) || !dificultad.matches("\\d+") || isBlank(tema) || isBlank(juez)
					|| isBlank(link)) {
				showSticky("406", "Campos inválidos (verifica título, dificultad numérica, tema, juez y link).");
				return;
			}

			String respuesta = ProblemaService.crear(BASE_CREATE, titulo, dificultad, tema, juez, link);
			String[] data = respuesta.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String msg = (data.length > 1) ? data[1] : "Sin cuerpo de respuesta";

			showSticky(code, msg);

			if ("201".equals(code) || "200".equals(code)) {
				// limpiar
				titulo = "";
				dificultad = "";
				tema = "";
				juez = "";
				link = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			showSticky("500", "Error al crear problema: " + e.getMessage());
		}
	}

	public void listarProblemas() {
		listado.clear();

		String resp = ProblemaService.mostrarTexto(BASE_ROOT);
		String[] data = resp.split("\n", 2);
		String code = (data.length > 0) ? data[0] : "500";
		String body = (data.length > 1) ? data[1] : "";

		if ("202".equals(code) || "200".equals(code)) {
			if (body != null && !body.isBlank()) {
				for (String line : body.split("\\R")) {
					if (!line.isBlank()) {
						listado.add(line.trim());
					}
				}
				showSticky("201", "Se cargaron " + listado.size() + " registros.");
			} else {
				showSticky("204", "No hay problemas para mostrar.");
			}
		} else if ("204".equals(code)) {
			showSticky("406", "No hay problemas para mostrar.");
		} else {
			showSticky("500", body.isBlank() ? "Error al listar problemas" : body);
		}
	}

	// ====== Acciones por fila (para usar desde la tabla) ======

	public void ver(String row) {
		showSticky("201", "Fila seleccionada: " + row);
	}

	public void eliminar(String row) {
		// Intentamos obtener el ID de la fila (primer número, preferiblemente al
		// inicio)
		Long id = extraerId(row);
		if (id == null) {
			showSticky("406", "No se encontró ID en la fila para eliminar.");
			return;
		}
		try {
			String resp = ProblemaService.eliminar(BASE_ROOT, id);
			String[] data = resp.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String body = (data.length > 1) ? data[1] : "Sin cuerpo de respuesta";

			// Éxito típico: 200 OK, 202 Accepted, 204 No Content
			if ("200".equals(code) || "202".equals(code) || "204".equals(code)) {
				listado.remove(row);
				showSticky("201", "Eliminado ID=" + id + (body.isBlank() ? "" : (" | " + body)));
			} else {
				showSticky("500", "No se pudo eliminar (HTTP " + code + "): " + body);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showSticky("500", "Error al eliminar: " + e.getMessage());
		}
	}

	// ================= Helpers =================

	private boolean isBlank(String s) {
		return s == null || s.isBlank();
	}

	private void showSticky(String code, String content) {
		FacesMessage.Severity severity;
		String summary;
		switch (code) {
		case "201":
		case "200":
			severity = FacesMessage.SEVERITY_INFO;
			summary = "Éxito";
			break;
		case "406":
		case "204":
			severity = FacesMessage.SEVERITY_WARN;
			summary = "Advertencia";
			break;
		default:
			severity = FacesMessage.SEVERITY_ERROR;
			summary = "Error";
			break;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, content));
	}

	/**
	 * Extrae el primer ID numérico de la cadena de una fila. 1) Intenta al inicio
	 * de la línea: "123 - Título - ..." 2) Si no, busca el primer número "aislado"
	 * en la cadena (evitamos capturar dígitos de URLs).
	 */
	private Long extraerId(String row) {
		if (row == null)
			return null;

		// (1) ID al inicio de la línea
		Matcher mStart = Pattern.compile("^\\s*(\\d+)").matcher(row);
		if (mStart.find()) {
			try {
				return Long.parseLong(mStart.group(1));
			} catch (NumberFormatException ignore) {
			}
		}

		// (2) Primer número "aislado" que no esté dentro de http(s)://... (heurística
		// simple)
		// Busca números que no estén precedidos por letras o '/', y no formen parte
		// evidente de una URL
		Matcher mAny = Pattern.compile("(?<![A-Za-z/])(\\d+)(?![A-Za-z/])").matcher(row);
		while (mAny.find()) {
			String cand = mAny.group(1);
			// Evita números muy largos típicos de hashes, pero acepta IDs normales
			if (cand.length() <= 12) {
				try {
					return Long.parseLong(cand);
				} catch (NumberFormatException ignore) {
				}
			}
		}

		return null;
	}

	// ================= Getters / Setters =================

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
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

	public List<String> getListado() {
		return listado;
	}

	public void setListado(List<String> listado) {
		this.listado = listado;
	}
}