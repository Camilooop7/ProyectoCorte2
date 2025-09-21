package co.edu.unbosque.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.service.CronogramaService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("cronogramaBean")
@ViewScoped
public class CronogramaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private String link;
	private LocalDate fecha; 

	private List<String> listado = new ArrayList<>();

	private static final String BASE_CREATE = "http://localhost:8081/cronograma/crear";
	private static final String BASE_ROOT = "http://localhost:8081";
	private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_DATE;


	public void doCreate() {
		try {
			if (isBlank(nombre) || isBlank(link) || fecha == null) {
				show("406", "Campos inválidos. Verifica nombre, link y fecha (yyyy-MM-dd).");
				return;
			}
			if (!isUrl(link)) {
				show("406", "El link debe iniciar con http:// o https://");
				return;
			}

			String fechaISO = fecha.format(ISO);

			String resp = CronogramaService.crear(BASE_CREATE, nombre, link, fechaISO);
			String[] data = resp.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String body = (data.length > 1) ? data[1] : "Sin cuerpo de respuesta";

			show(code, body);

			if ("201".equals(code) || "200".equals(code)) {
				nombre = "";
				link = "";
				fecha = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			show("500", "Error al crear cronograma: " + e.getMessage());
		}
	}

	public void listar() {
		listado.clear();
		try {
			String resp = CronogramaService.mostrarTexto(BASE_ROOT);
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
					show("201", "Se cargaron " + listado.size() + " registros.");
				} else {
					show("204", "No hay cronogramas para mostrar.");
				}
			} else if ("204".equals(code)) {
				show("406", "No hay cronogramas para mostrar.");
			} else {
				show("500", body.isBlank() ? "Error al listar cronogramas" : body);
			}
		} catch (Exception e) {
			e.printStackTrace();
			show("500", "Error al listar cronogramas: " + e.getMessage());
		}
	}


	private boolean isBlank(String s) {
		return s == null || s.isBlank();
	}

	private boolean isUrl(String s) {
		if (s == null)
			return false;
		String lower = s.toLowerCase();
		return lower.startsWith("http://") || lower.startsWith("https://");
	}

	private void show(String code, String content) {
		FacesMessage.Severity sev;
		String summary;
		switch (code) {
		case "201":
		case "200":
			sev = FacesMessage.SEVERITY_INFO;
			summary = "Éxito";
			break;
		case "406":
		case "204":
			sev = FacesMessage.SEVERITY_WARN;
			summary = "Advertencia";
			break;
		default:
			sev = FacesMessage.SEVERITY_ERROR;
			summary = "Error";
			break;
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, summary, content));
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public List<String> getListado() {
		return listado;
	}

	public void setListado(List<String> listado) {
		this.listado = listado;
	}
}
