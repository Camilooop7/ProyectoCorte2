
package co.edu.unbosque.beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.service.CronogramaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("cronogramaBean")
@RequestScoped
public class CronogramaBean {

	private String nombre;
	private String link;
	private String fecha;
	private List<String> listado = new ArrayList<>();

	private static final String BASE_CREATE = "http://localhost:8081/cronograma/crear";
	private static final String BASE_ROOT = "http://localhost:8081";

	public void doCreate() {
		try {
			if (isBlank(nombre) || isBlank(link) || isBlank(fecha) || !isFechaISO(fecha)) {
				show("406", "Campos inválidos. Verifica nombre, link y fecha (yyyy-MM-dd).");
				return;
			}

			String resp = CronogramaService.crear(BASE_CREATE, nombre, link, fecha);
			String[] data = resp.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String body = (data.length > 1) ? data[1] : "Sin cuerpo de respuesta";

			show(code, body);

			if ("201".equals(code)) {
				nombre = "";
				link = "";
				fecha = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			show("500", "Error al crear cronograma: " + e.getMessage());
		}
	}

	public void listar() {
		listado.clear();

		String resp = CronogramaService.mostrarTexto(BASE_ROOT);
		String[] data = resp.split("\n", 2);
		String code = (data.length > 0) ? data[0] : "500";
		String body = (data.length > 1) ? data[1] : "";

		if ("202".equals(code)) {
			for (String line : body.split("\\R")) {
				if (!line.isBlank())
					listado.add(line.trim());
			}
			show("201", "Se cargaron " + listado.size() + " registros.");
		} else if ("204".equals(code)) {
			show("406", "No hay cronogramas para mostrar.");
		} else {
			show("500", body.isBlank() ? "Error al listar cronogramas" : body);
		}
	}


	private boolean isBlank(String s) {
		return s == null || s.isBlank();
	}

	private boolean isFechaISO(String s) {
		try {
			LocalDate.parse(s, DateTimeFormatter.ISO_DATE); 
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void show(String code, String content) {
		FacesMessage.Severity sev;
		String summary;
		switch (code) {
		case "201":
			sev = FacesMessage.SEVERITY_INFO;
			summary = "Éxito";
			break;
		case "406":
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List<String> getListado() {
		return listado;
	}

	public void setListado(List<String> listado) {
		this.listado = listado;
	}
}
