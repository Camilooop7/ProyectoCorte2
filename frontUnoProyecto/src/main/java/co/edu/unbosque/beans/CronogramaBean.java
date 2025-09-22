package co.edu.unbosque.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.service.CronogramaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

@Named("cronogramaBean")
@ViewScoped
public class CronogramaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private String link;
	private LocalDate fecha;

	private List<String> listado = new ArrayList<>();

	private ScheduleModel scheduleprime = new DefaultScheduleModel();

	private static final String BASE_CREATE = "http://localhost:8081/cronograma/crear";
	private static final String BASE_ROOT = "http://localhost:8081";
	private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_DATE;

	private static final Pattern DATE_P = Pattern.compile("\\b(\\d{4}-\\d{2}-\\d{2})\\b");
	private static final Pattern URL_P = Pattern.compile("\\bhttps?://\\S+\\b", Pattern.CASE_INSENSITIVE);
	private static final Pattern NOMBRE_LABEL_P = Pattern.compile("(?i)\\b(nombre|titulo)\\s*[:=]\\s*([^,;|}]+)");
	private static final Pattern LINK_LABEL_P = Pattern.compile("(?i)\\b(link|url)\\s*[:=]\\s*(https?://\\S+)");
	private static final Pattern FECHA_LABEL_P = Pattern
			.compile("(?i)\\b(fecha|date)\\s*[:=]\\s*(\\d{4}-\\d{2}-\\d{2})");

	@PostConstruct
	public void init() {
		
		try {
			listar(); 
		} catch (Exception ignore) {
		}
	}

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
				// limpiar campos
				nombre = "";
				link = "";
				fecha = null;

				listar();
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
					rebuildCalModel();
					show("201", "Se cargaron " + listado.size() + " registros.");
				} else {
					scheduleprime = new DefaultScheduleModel();
					show("204", "No hay cronogramas para mostrar.");
				}
			} else if ("204".equals(code)) {
				scheduleprime = new DefaultScheduleModel();
				show("406", "No hay cronogramas para mostrar.");
			} else {
				scheduleprime = new DefaultScheduleModel();
				show("500", body.isBlank() ? "Error al listar cronogramas" : body);
			}
		} catch (Exception e) {
			e.printStackTrace();
			show("500", "Error al listar cronogramas: " + e.getMessage());
		}
	}

	private void rebuildCalModel() {
		DefaultScheduleModel model = new DefaultScheduleModel();

		for (String line : listado) {
			Parsed p = parseLine(line);
			if (p.fecha == null)
				continue;

			String title = !isBlank(p.nombre) ? p.nombre : sanitizeTitle(line);

			
			LocalDateTime start = p.fecha.atTime(9, 0);
			LocalDateTime end = p.fecha.atTime(10, 0);

			DefaultScheduleEvent<?> evt = DefaultScheduleEvent.builder().title(title).startDate(start) 
																										
					.endDate(end) 
					.overlapAllowed(true).build();

			if (!isBlank(p.link)) {
				evt.setUrl(p.link); 
			}

			model.addEvent(evt);
		}

		this.scheduleprime = model;
	}

	private Parsed parseLine(String line) {
		Parsed out = new Parsed();

		if (isBlank(line))
			return out;

		Matcher mNombre = NOMBRE_LABEL_P.matcher(line);
		if (mNombre.find()) {
			out.nombre = trimClean(mNombre.group(2));
		}
		Matcher mLink = LINK_LABEL_P.matcher(line);
		if (mLink.find()) {
			out.link = trimClean(mLink.group(2));
		}
		Matcher mFecha = FECHA_LABEL_P.matcher(line);
		if (mFecha.find()) {
			try {
				out.fecha = LocalDate.parse(mFecha.group(2), ISO);
			} catch (Exception ignore) {
			}
		}

		if (isBlank(out.link)) {
			Matcher mUrl = URL_P.matcher(line);
			if (mUrl.find())
				out.link = trimClean(mUrl.group());
		}
		if (out.fecha == null) {
			Matcher mDate = DATE_P.matcher(line);
			if (mDate.find()) {
				try {
					out.fecha = LocalDate.parse(mDate.group(1), ISO);
				} catch (Exception ignore) {
				}
			}
		}
		if (isBlank(out.nombre)) {
			String name = line.replaceAll("(?i)\\b(nombre|titulo|link|url|fecha|date)\\s*[:=]", " ")
					.replaceAll("\\{\\}|\\[\\]|[{}\\[\\]]", " ").replaceAll(URL_P.pattern(), " ")
					.replaceAll(DATE_P.pattern(), " ").replaceAll("[,;|]", " ").trim();
			out.nombre = sanitizeTitle(name);
		}

		return out;
	}

	private String sanitizeTitle(String s) {
		if (isBlank(s))
			return "Evento";
		String t = s.replaceAll("\\s+", " ").trim();
		if (t.length() > 80)
			t = t.substring(0, 77) + "...";
		return t;
	}

	private String trimClean(String s) {
		if (s == null)
			return null;
		return s.replaceAll("[\"']", "").trim();
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
		this.listado = listado != null ? listado : new ArrayList<>();
		rebuildCalModel();
	}

	public ScheduleModel getCalModel() {
		return scheduleprime;
	}

	private static class Parsed {
		String nombre;
		String link;
		LocalDate fecha;
	}
}
