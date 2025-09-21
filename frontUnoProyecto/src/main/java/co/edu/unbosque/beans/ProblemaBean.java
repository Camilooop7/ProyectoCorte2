package co.edu.unbosque.beans;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.service.ProblemaService;
import co.edu.unbosque.service.RegistroService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("problemaBean")
@RequestScoped
public class ProblemaBean {

	private String titulo;
	private String dificultad;
	private String tema;
	private String juez;
	private String link;

	private List<String> listado = new ArrayList<>();

	private static final String BASE_CREATE = "http://localhost:8081/problema/crear";
	private static final String BASE_ROOT = "http://localhost:8081";

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

			if ("201".equals(code)) {
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

		if ("202".equals(code)) {
			for (String line : body.split("\\R")) {
				if (!line.isBlank())
					listado.add(line.trim());
			}
			showSticky("201", "Se cargaron " + listado.size() + " registros.");
		} else if ("204".equals(code)) {
			showSticky("406", "No hay problemas para mostrar.");
		} else {
			showSticky("500", body.isBlank() ? "Error al listar problemas" : body);
		}
	}

	private boolean isBlank(String s) {
		return s == null || s.isBlank();
	}

	private void showSticky(String code, String content) {
		FacesMessage.Severity severity;
		String summary;
		switch (code) {
		case "201":
			severity = FacesMessage.SEVERITY_INFO;
			summary = "Éxito";
			break;
		case "406":
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
