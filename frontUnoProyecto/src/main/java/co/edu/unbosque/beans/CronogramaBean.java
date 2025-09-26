package co.edu.unbosque.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

/**
 * Bean de vista para gestionar cronogramas de eventos. Permite crear, listar,
 * eliminar y visualizar eventos en un calendario PrimeFaces.
 */
@Named("cronogramaBean")
@ViewScoped
public class CronogramaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Nombre del evento. */
	private String nombre;

	/** Enlace asociado al evento. */
	private String link;

	/** Fecha del evento. */
	private LocalDate fecha;

	/** ID del evento a eliminar. */
	private Long deleteId;

	/** Lista de eventos parseados. */
	private List<FilaCronograma> filas = new ArrayList<>();

	/** Modelo para el calendario PrimeFaces. */
	private ScheduleModel scheduleprime = new DefaultScheduleModel();

	/** URL base para crear eventos. */
	private static final String BASE_CREATE = "http://localhost:8081/cronograma/crear";

	/** URL base del servicio. */
	private static final String BASE_ROOT = "http://localhost:8081";

	/** Formato de fecha ISO. */
	private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_DATE;

	/** Patrón para extraer el ID de una línea de texto. */
	private static final Pattern ID_P = Pattern.compile("\\b(id|ID)\\s*[:=]\\s*(\\d+)\\b");

	/** Patrón para extraer fechas en formato yyyy-MM-dd. */
	private static final Pattern DATE_P = Pattern.compile("\\b(\\d{4}-\\d{2}-\\d{2})\\b");

	/** Patrón para extraer URLs. */
	private static final Pattern URL_P = Pattern.compile("\\bhttps?://\\S+\\b", Pattern.CASE_INSENSITIVE);

	/** Patrón para extraer el nombre o título. */
	private static final Pattern NOMBRE_P = Pattern.compile("(?i)\\b(nombre|titulo)\\s*[:=]\\s*([^,;|}]+)");

	/** Patrón para extraer enlaces. */
	private static final Pattern LINK_P = Pattern.compile("(?i)\\b(link|url)\\s*[:=]\\s*(https?://\\S+)");

	/** Patrón para extraer fechas. */
	private static final Pattern FECHA_P = Pattern.compile("(?i)\\b(fecha|date)\\s*[:=]\\s*(\\d{4}-\\d{2}-\\d{2})");

	/**
	 * Inicializa el bean y carga la lista de eventos.
	 */
	@PostConstruct
	public void init() {
		try {
			listar();
		} catch (Exception ignore) {
		}
	}

	/**
	 * Crea un nuevo evento en el cronograma.
	 */
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
				listar();
			}
		} catch (Exception e) {
			e.printStackTrace();
			show("500", "Error al crear cronograma: " + e.getMessage());
		}
	}

	/**
	 * Lista todos los eventos desde el backend.
	 */
	public void listar() {
		filas.clear();
		try {
			String resp = CronogramaService.mostrarTexto(BASE_ROOT);
			String[] data = resp.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String body = (data.length > 1) ? data[1] : "";
			if ("202".equals(code) || "200".equals(code)) {
				if (body != null && !body.isBlank()) {
					String[] lines = body.split("\\R");
					for (String raw : lines) {
						String line = raw.trim();
						if (line.isEmpty())
							continue;
						FilaCronograma f = parseLine(line);
						if (f != null && f.getFecha() != null) {
							filas.add(f);
						}
					}
					rebuildCalModel();
					show("201", "Se cargaron " + filas.size() + " registros.");
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

	/**
	 * Elimina un evento por su ID.
	 */
	public void eliminarById() {
		if (deleteId == null || deleteId <= 0) {
			show("406", "Ingresa un ID válido para eliminar.");
			return;
		}
		eliminar(deleteId);
		deleteId = null;
	}

	/**
	 * Elimina un evento por su ID.
	 * 
	 * @param id ID del evento a eliminar.
	 */
	public void eliminar(Long id) {
		if (id == null) {
			show("406", "ID inválido para eliminar.");
			return;
		}
		try {
			String resp = CronogramaService.eliminar(BASE_ROOT, id);
			String[] data = resp.split("\n", 2);
			String code = (data.length > 0) ? data[0] : "500";
			String body = (data.length > 1) ? data[1] : "";
			if ("200".equals(code)) {
				show("200", body.isBlank() ? "Evento eliminado con éxito" : body);
				listar();
			} else {
				show(code, body.isBlank() ? "No se pudo eliminar." : body);
			}
		} catch (Exception e) {
			e.printStackTrace();
			show("500", "Error al eliminar: " + e.getMessage());
		}
	}

	/**
	 * Reconstruye el modelo del calendario con los eventos actuales.
	 */
	private void rebuildCalModel() {
		DefaultScheduleModel model = new DefaultScheduleModel();
		for (FilaCronograma f : filas) {
			LocalDate fecha = f.getFecha();
			if (fecha == null)
				continue;
			String title = isBlank(f.getNombre()) ? "Evento" : sanitizeTitle(f.getNombre());
			LocalDateTime start = fecha.atTime(9, 0);
			LocalDateTime end = fecha.atTime(10, 0);
			DefaultScheduleEvent<?> evt = DefaultScheduleEvent.builder().title(title).startDate(start).endDate(end)
					.overlapAllowed(true).build();
			if (!isBlank(f.getLink())) {
				evt.setUrl(f.getLink());
			}
			model.addEvent(evt);
		}
		this.scheduleprime = model;
	}

	/**
	 * Parsea una línea de texto en un objeto {@link FilaCronograma}.
	 * 
	 * @param line Línea de texto a parsear.
	 * @return Objeto {@link FilaCronograma} o {@code null} si no es válido.
	 */
	private FilaCronograma parseLine(String line) {
		if (isBlank(line))
			return null;
		Long id = longOrNull(findGroup(ID_P, line, 2));
		String nombre = trimClean(findGroup(NOMBRE_P, line, 2));
		String link = trimClean(findGroup(LINK_P, line, 2));
		LocalDate fecha = null;
		Matcher mFecha = FECHA_P.matcher(line);
		if (mFecha.find()) {
			try {
				fecha = LocalDate.parse(mFecha.group(2), ISO);
			} catch (Exception ignore) {
			}
		}
		if (isBlank(link)) {
			Matcher mUrl = URL_P.matcher(line);
			if (mUrl.find())
				link = trimClean(mUrl.group());
		}
		if (fecha == null) {
			Matcher mDate = DATE_P.matcher(line);
			if (mDate.find()) {
				try {
					fecha = LocalDate.parse(mDate.group(1), ISO);
				} catch (Exception ignore) {
				}
			}
		}
		if (isBlank(nombre)) {
			String name = line.replaceAll("(?i)\\b(id|nombre|titulo|link|url|fecha|date)\\s*[:=]", " ")
					.replaceAll("\\{\\}|\\[\\]|[{}\\[\\]]", " ").replaceAll(URL_P.pattern(), " ")
					.replaceAll(DATE_P.pattern(), " ").replaceAll("[,;|]", " ").trim();
			nombre = sanitizeTitle(name);
		}
		return new FilaCronograma(id, nombre, link, fecha);
	}

	/**
	 * Limpia y sanitiza un título para el calendario.
	 * 
	 * @param s Título original.
	 * @return Título sanitizado.
	 */
	private String sanitizeTitle(String s) {
		if (isBlank(s))
			return "Evento";
		String t = s.replaceAll("\\s+", " ").trim();
		if (t.length() > 80)
			t = t.substring(0, 77) + "...";
		return t;
	}

	/**
	 * Busca un grupo en un patrón de expresión regular.
	 * 
	 * @param p     Patrón.
	 * @param s     Cadena de texto.
	 * @param group Grupo a extraer.
	 * @return Valor del grupo o {@code null} si no se encuentra.
	 */
	private static String findGroup(Pattern p, String s, int group) {
		Matcher m = p.matcher(s);
		return m.find() ? m.group(group) : null;
	}

	/**
	 * Limpia y recorta una cadena de texto.
	 * 
	 * @param s Cadena a limpiar.
	 * @return Cadena limpia o {@code null} si es nula.
	 */
	private String trimClean(String s) {
		return s == null ? null : s.replaceAll("[\"']", "").trim();
	}

	/**
	 * Verifica si una cadena es nula o vacía.
	 * 
	 * @param s Cadena a verificar.
	 * @return {@code true} si es nula o vacía.
	 */
	private boolean isBlank(String s) {
		return s == null || s.isBlank();
	}

	/**
	 * Verifica si una cadena es una URL válida.
	 * 
	 * @param s Cadena a verificar.
	 * @return {@code true} si es una URL válida.
	 */
	private boolean isUrl(String s) {
		if (s == null)
			return false;
		String lower = s.toLowerCase();
		return lower.startsWith("http://") || lower.startsWith("https://");
	}

	/**
	 * Convierte una cadena a {@link Long} o devuelve {@code null}.
	 * 
	 * @param s Cadena a convertir.
	 * @return {@link Long} o {@code null} si no es válido.
	 */
	private static Long longOrNull(String s) {
		try {
			return s == null ? null : Long.valueOf(s.trim());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Muestra un mensaje en la interfaz según el código de respuesta.
	 * 
	 * @param code    Código de respuesta.
	 * @param content Contenido del mensaje.
	 */
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

	// Getters y Setters
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

	public Long getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(Long deleteId) {
		this.deleteId = deleteId;
	}

	public List<FilaCronograma> getFilas() {
		return filas;
	}

	public void setFilas(List<FilaCronograma> filas) {
		this.filas = filas != null ? filas : new ArrayList<>();
	}

	public ScheduleModel getCalModel() {
		return scheduleprime;
	}

	/**
	 * Clase interna que representa una fila del cronograma.
	 */
	public static class FilaCronograma implements Serializable {
		private Long id;
		private String nombre;
		private String link;
		private LocalDate fecha;

		public FilaCronograma() {
		}

		public FilaCronograma(Long id, String nombre, String link, LocalDate fecha) {
			this.id = id;
			this.nombre = nombre;
			this.link = link;
			this.fecha = fecha;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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
	}
}
